//-------------------------------------
// Texture
//-------------------------------------
vec3 debugSteps(float distance) {
    return vec3(
            (distance < 0.25) ? 1. - distance * 4. : ((distance > 0.24 && distance < 0.50) ? 0.0 + distance * 2. : 0.), 
            (distance < 0.25) ? 1. - distance * 4. : ((distance > 0.49 && distance < 0.75) ? distance * 1.2 : 0.), 
            (distance < 0.15) ? 1. - distance * 4. : ((distance > 0.75) ? distance : 0.)
    );
}

vec3 checkers(vec3 pos, vec3 normal, float size) {
    vec3 p=cross(pos, normal);
    float c=floor(mod((p.x / size) + floor(p.y / size) + floor(p.z / size), 2.0)) * size;
    return vec3(clamp(c, 0.3, 1.0));
}

vec3 distancecolor(vec3 pos, vec3 normal) {
    return normalize(pos);
}

vec3 plasma(vec3 pos, vec3 normal) {
    return vec3(
        pos.x * sin(pos.y * 50. + time / 0.4), 
        pos.y * cos(pos.z * 10. + time / 0.2), 
        pos.x * sin(pos.y * 15. + time / 0.10)
    );
}

