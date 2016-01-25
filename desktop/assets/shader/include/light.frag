//-------------------------------------
// Light, shadows
//-------------------------------------
void pointLight(inout vec3 lightColor, inout vec3 lightSpecular, Light light, Intersect sceneObject) {
    vec3 lightDir=normalize(light.posistion - sceneObject.pos);
    float lightPower=dot(sceneObject.normal, lightDir);
    lightColor=clamp(lightColor+light.ambientColor + light.color * lightPower, 0.0, 1.0);
    
    if (sceneObject.mat.shininess > 0.0){
        vec3 reflect = reflect(lightDir, sceneObject.normal);
        float specural=pow(max(0.,dot(lightDir,-reflect)), sceneObject.mat.shininess);
        float specural2=pow(lightPower, sceneObject.mat.shininess);
        float specural3=pow(max(0.,dot(lightDir,-reflect)) * 10., sceneObject.mat.shininess);
        lightSpecular=clamp(lightSpecular+light.color * specural, 0.0, 1.0);
    }
}

float pointShadow(in vec3 ro, in vec3 rd) {
    float t=SHADOW_MIN_DISTANCE;
    for(int i=0; i < SHADOW_ITERATION; i++) {
        float h=map(ro + rd * t).x;
        if (h < SHADOW_EPSILON){
            return 0.0;
        }
        t += h * SHADOW_BOOST;
        if (t > SHADOW_MAX_DISTANCE){
            break;
        }
    }
    return 1.0;
}

float pointSoftShadow(in vec3 ro, in vec3 rd, float k) {
    float res=1.0;
    float t=SHADOW_MIN_DISTANCE;
    for(int i=0; i < SHADOW_ITERATION; i++) {
        float h=map(ro + rd * t).x;
        res=min(res, k * h / t);
        t += SHADOW_DELTA + h * SHADOW_BOOST;
        if (h < SHADOW_EPSILON || t > SHADOW_MAX_DISTANCE){
            break;
        }
    }
    return clamp(res, 0.0, 1.0);
}

//
// Ambient occlusion
//
float pointAO(in vec3 pos, in vec3 nor) {
    float totao=0.0;
    float sca=AO_BLUR_START;
    for(int aoi=0; aoi < AO_ITERATION; aoi++) {
        float hr=AO_START + AO_STEP * float(aoi);
        vec3 aoPos=nor * hr + pos;
        float distance=map(aoPos).x;
        totao += -(distance - hr) * sca;
        sca *= AO_BLUR;
    }
    return clamp(1.0 - 4.0 * totao, 0.0, 1.0);
}


vec3 getSkyColor(vec3 outColor, Intersect sceneObject, vec3 ray, vec3 rayDir){

    vec3 skyColor=vec3(0.0);
    #if ENABLE_SKY==1
        float horizontHeight = pow(1.0-max(rayDir.y,0.0), sky.horizontal);
        skyColor=mix(sky.spaceColor, sky.horizontalColor, horizontHeight);
    #endif
    #if ENABLE_SUN==1
        float sunAmount = max(dot(rayDir, normalize(sunLight.posistion)), 0.0);
        // Sun Halo
        skyColor = skyColor + sunLight.color * pow(sunAmount, sunLight.haloSize) * sunLight.haloPower;
        // Sun Disc
        skyColor = skyColor + sunLight.color * min(pow(sunAmount, sunLight.discSize) * sunLight.discPower, sunLight.discArea);
    #endif

    return clamp(skyColor, 0.0, 1.0);
}

vec3 getColor(vec3 outColor, Intersect sceneObject, vec3 ray, vec3 rayDir){
        #if RENDER_MODE==0
            outColor=sceneObject.mat.diffuse;
        #elif RENDER_MODE==1
            outColor=sceneObject.mat.diffuse * sceneObject.grey;
        #elif RENDER_MODE==2
            outColor=vec3(sceneObject.grey);
        #elif RENDER_MODE==3
            outColor=sceneObject.normal;
        #elif RENDER_MODE==4
            outColor=sceneObject.pos;
        #elif RENDER_MODE==5
            outColor=vec3(sceneObject.distance);
        #elif RENDER_MODE==6
            outColor=vec3(sceneObject.step);
        #endif
        #if ENABLE_TEXTURE
            if (sceneObject.mat.texture == 1){
                outColor=outColor * checkers(sceneObject.pos, sceneObject.normal, 2.0);
            } else if (sceneObject.mat.texture == 2){
                outColor=outColor * distancecolor(sceneObject.pos, sceneObject.normal);
            } else if (sceneObject.mat.texture == 3){
                outColor=outColor * plasma(sceneObject.pos, sceneObject.normal);
            }
        #endif

        #if ENABLE_LIGHT
            vec3 lightColor=vec3(0.0);
            vec3 lightSpecular=vec3(0.0);

            for (int i=0; i<NUM_OF_LIGHTS; i++){
                pointLight(lightColor, lightSpecular, lights[i], sceneObject);
            }
        #else
            vec3 lightColor=vec3(1.0);
            vec3 lightSpecular=vec3(0.0);
        #endif

        float baseShadow=1.0;
        #if ENABLE_AO
            float ao=pointAO(sceneObject.pos, sceneObject.normal);
            baseShadow*=baseShadow * ao;
        #endif
        #if SHADOW_MODE==1
            for (int i=0; i<NUM_OF_LIGHTS; i++){
                baseShadow *= pointShadow(sceneObject.pos + SHADOW_POINT * sceneObject.normal, lights[i].posistion);
            }
        #endif
        #if SHADOW_MODE==2
            for (int i=0; i<NUM_OF_LIGHTS; i++){
                baseShadow *= pointSoftShadow(sceneObject.pos + SHADOW_POINT * sceneObject.normal, lights[i].posistion, SHADOW_BLUR);
            }
        #endif

        baseShadow=clamp(baseShadow,SHADOW_OPACITY, 1.0);
        outColor=sceneObject.mat.ambient + outColor * lightColor * baseShadow + sceneObject.mat.specular * lightSpecular;
        #if ENABLE_FOG
            vec3 fogColor=getSkyColor(outColor, sceneObject, ray, rayDir);
            outColor=outColor*sceneObject.grey+fogColor*sceneObject.distance*FOG_POWER;
		#endif

    return outColor;
}

vec3 getReflectColor(vec3 outColor, Intersect sceneObject, vec3 ray, vec3 rayDir){

       vec3 startColor=vec3(0.0);
        vec3 sumColor=vec3(0.0);
        vec3 divColor=vec3(1.0);
        Intersect startObject;
        startColor=outColor;
        startObject=sceneObject;

        for (int reflectStep=0; reflectStep < REFLECT_STEPS; reflectStep++){


			ray=sceneObject.pos + EPSILON * sceneObject.normal;
			rayDir = reflect(rayDir, sceneObject.normal);

            sceneObject=rayMarching(ray, rayDir);

            if (sceneObject.hit){
                outColor=getColor(outColor, sceneObject, ray, rayDir);
            } else {
                outColor=getSkyColor(outColor, sceneObject, ray, rayDir);
            }

			sumColor=sumColor + outColor * REFLECT_MULTIPLER;

            if (sceneObject.hit){
                outColor=getColor(outColor, sceneObject, ray, rayDir);
            } else {
                outColor=getSkyColor(outColor, sceneObject, ray, rayDir);
            }

			if (sceneObject.hit==false){
				break;
			}
        }
        outColor=startColor * startObject.mat.absorption * REFLECT_GLOBAL_ABSORPTION + sumColor * startObject.mat.reflection * REFLECT_GLOBAL;

    return outColor;
}
