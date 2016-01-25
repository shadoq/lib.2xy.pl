//-------------------------------------
// Operation CGI
//-------------------------------------
vec2 smothJoin(vec2 a, vec2 b, float k) {
    float h=clamp(0.5 + 0.5 * (b.x - a.x) / k, 0.0, 1.0);
    float distance=mix(b.x, a.x, h) - k * h * (1.0 - h);
    a.x=distance;
    if (h < 0.5){
        a.y=b.y;
    }
    return a;
}

vec2 join(vec2 a, vec2 b) {
    if (a.x > b.x){
        return b;
    }
    return a;
}
vec2 join(vec2 a, vec2 b, vec2 c) {
    if (a.x > b.x){
        if (b.x > c.x){
            return c;
        }
        return b;
    }
    if (a.x > c.x){
        return c;
    }
    return a;
}

vec2 intersect(vec2 a, vec2 b) {
    if (a.x > b.x){
        return a;
    }
    return b;
}
vec2 intersect(vec2 a, vec2 b, vec2 c) {
    if (a.x > b.x){
        if (c.x > a.x){
            return c;
        }
        return a;
    }
    if (c.x > b.x){
        return c;
    }
    return b;
}
vec2 diff(vec2 a, vec2 b) {
    b.x=-b.x;
    if (b.x > a.x){
        return b;
    }
    return a;
}

vec2 diff(vec2 a, vec2 b, vec2 c) {
    b.x=-b.x;
    if (b.x > a.x){
        c.x=-c.x;
        if (c.x > c.x){
            return c;
        }
        return b;
    }
    if (b.x > c.x){
        a.x=-a.x;
        if (a.x > c.x){
            return a;
        }
        return c;
    }
    c.x=-c.x;
    if (c.x > a.x){
        return c;
    }
    return a;
}

//-----------------------------------
// Position operator
//-----------------------------------
vec3 repetition(vec3 pos, vec3 c) {
    return mod(pos, c) - 0.5 * c;
}

vec3 rotateX(vec3 p, float angle) {
    float c=cos(angle);
    float s=sin(angle);
    return vec3(p.x, c * p.y - s * p.z, s * p.y + c * p.z);
}
vec3 rotateY(vec3 p, float angle) {
    float c=cos(angle);
    float s=sin(angle);
    return vec3(c * p.x - s * p.z, p.y, s * p.x + c * p.z);
}
vec3 rotateZ(vec3 p, float angle) {
    float c=cos(angle);
    float s=sin(angle);
    return vec3(c * p.x - s * p.y, s * p.x + c * p.y, p.z);
}

vec3 rotateX(vec3 p, float sinus, float cosinus) {
    return vec3(p.x, cosinus * p.y - sinus * p.z, sinus * p.y + cosinus * p.z);
}
vec3 rotateY(vec3 p, float sinus, float cosinus) {
    return vec3(cosinus * p.x - sinus * p.z, p.y, sinus * p.x + cosinus * p.z);
}
vec3 rotateZ(vec3 p, float sinus, float cosinus) {
    return vec3(cosinus * p.x - sinus * p.y, sinus * p.x + cosinus * p.y, p.z);
}

//-----------------------------------
// Deform operation
//-----------------------------------
vec3 twist(vec3 p, float multipler, float time) {
    float c=cos(multipler * p.y + time);
    float s=sin(multipler * p.y + time);
    return vec3(c * p.x - s * p.z, p.y, s * p.x + c * p.z);
}
vec3 bend(vec3 p, float multipler, float time ) {
    float c=-1. * cos(multipler * p.y + time);
    float s=1. * sin(multipler * p.y + time);
    mat2 m=mat2(c, -s, s, c);
    return vec3(m * p.xy, p.z);
}

vec3 twistY(vec3 p, float multipler, float time) {
    float c=cos(multipler * p.y + time);
    float s=sin(multipler * p.y + time);
    return vec3(c * p.x - s * p.z, p.y, s * p.x + c * p.z);
}

vec3 twistX(vec3 p, float multipler, float time) {
    float c=cos(multipler * p.x + time);
    float s=sin(multipler * p.x + time);
    return vec3(p.x, c * p.y - s * p.z, s * p.y + c * p.z);
}

vec3 twistZ(vec3 p, float multipler, float time) {
    float c=cos(multipler * p.z + time);
    float s=sin(multipler * p.z + time);
    return vec3(c * p.x - s * p.y, s * p.x + c * p.y, p.z);
}

//-----------------------------------
// Displace map
//-----------------------------------
vec2 sinDisplace(vec3 p, vec2 primitive, float steps, float scale) {
    primitive.x+=(sin(steps * p.x) * sin(steps * p.y) * sin(steps * p.z)) * scale;
    return primitive;
}
vec2 sinDisplace(vec3 p, vec2 primitive, float steps, float scale, float time) {
    primitive.x+=(sin(steps * p.x + time) * sin(steps * p.y + time) * sin(steps * p.z + time)) * scale;
    return primitive;
}

vec2 sDisplace(vec3 p, vec2 primitive, float steps, float scale) {
    primitive.x+=(sin(steps * p.x) * cos(steps * p.y) * sin(steps * p.z)) * scale;
    return primitive;
}

vec2 sDisplace(vec3 p, vec2 primitive, float steps, float scale, float time) {
    primitive.x+=(sin(steps * p.x + time) * cos(steps * p.y + time) * sin(steps * p.z + time)) * scale;
    return primitive;
}

vec3 pDisplace(vec3 p, float steps, float scale) {
    p+=vec3(
        sin(steps * p.x) * scale,
        cos(steps * p.y) * scale,
        sin(steps * p.z) * scale
    ) ;
    return p;
}

vec3 pDisplace(vec3 p, float steps, float scale, float time) {
    scale=scale/2.0;
    p+=vec3(
        scale + sin(steps * p.x + time) * scale,
        scale + cos(steps * p.y + time) * scale,
        scale + sin(steps * p.z + time) * scale
    ) ;
    return p;
}

//-------------------------------------
// Ray Marching
//-------------------------------------
vec3 pointNormal(vec3 pos) {
    vec3 eps=vec3(NORMAL_EPSILON, 0.0, 0.0);
    vec3 nor=vec3(
            map(pos + eps.xyy).x - map(pos - eps.xyy).x,
            map(pos + eps.yxy).x - map(pos - eps.yxy).x,
            map(pos + eps.yyx).x - map(pos - eps.yyx).x
    );
    return normalize(nor);
}


Intersect rayMarching(vec3 ray, vec3 rayDir) {

    float distance=1.0;
    Intersect sceneObject;
    vec2 mapOut;
    sceneObject.hit=false;
    sceneObject.stepCount=0;

    for(int i=0; i<MAX_STEPS; ++i) {

        mapOut=map(ray);
        ray = ray + rayDir * mapOut.x;
        distance = distance + mapOut.x;
        sceneObject.stepCount++;

        if (mapOut.x < EPSILON){
            sceneObject.hit=true;
            break;
        }
        if (distance > MAX_DISTANCE){
            sceneObject.hit=false;
            break;
        }
    }

    if (sceneObject.hit){

        sceneObject.step = STEPS_DELTA * sceneObject.stepCount;
        sceneObject.distance=distance/MAX_DISTANCE;
        sceneObject.pos=ray;
        sceneObject.grey=1.0 - sceneObject.distance;
        sceneObject.normal=pointNormal(ray);

        //
        // Assign material to object
        //
        #if ENABLE_MATERIAL==1
            sceneObject.mat=materials[int(mapOut.y)];
        #else
            sceneObject.mat.ambient=vec3(0.1);
            sceneObject.mat.diffuse=vec3(0.5);
            sceneObject.mat.specular=vec3(1.0);
            sceneObject.mat.shininess=8.0;
            sceneObject.mat.reflection=1.0;
            sceneObject.mat.absorption=1.0;
        #endif
    }
    return sceneObject;
}

