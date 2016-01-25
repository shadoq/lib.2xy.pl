//-------------------------------------
// RayMarch primitive object 
// (procedural function)
//-------------------------------------

vec2 box(vec3 p, vec3 pos, vec3 size, float material) {
    vec3 d=abs(p - pos) - size;
    float distance=min(max(d.x, max(d.y, d.z)), 0.0) + length(max(d, 0.0));
    return vec2(distance, material);
}
vec2 sphere(vec3 p, vec3 pos, float radius, float material) {
    return vec2(length(p - pos) - radius, material);
}
vec2 plane(vec3 p, vec3 pos, vec3 normal, float material) {
    return vec2(dot(p - pos, normal), material);
}
vec2 roundBox(vec3 p, vec3 pos, vec3 size, float radius, float material) {
    return vec2(length(max(abs(p - pos) - size, 0.0)) - radius, material);
}
vec2 torus( vec3 p, vec3 pos, vec2 t, float material) {
    p=p - pos;
    vec2 q=vec2(length(p.xy) - t.x, p.z);
    return vec2(length(q) - t.y, material);
}
vec2 hexPrism(vec3 p, vec3 pos, vec2 h, float material) {
    vec3 q=abs(p - pos);
    return vec2(max(q.z - h.y, max(q.x + q.y * 0.57735, q.y * 1.1547) - h.x), material);
}
vec2 triPrism( vec3 p, vec3 pos, vec2 h, float material) {
    vec3 q=abs(p - pos);
    return vec2(max(q.z - h.y, max(q.x * 0.866025 + q.y * 0.5, -p.y) - h.x * 0.5), material);
}
vec2 capsule( vec3 p, vec3 pos, vec3 a, vec3 b, float r, float material) {
    p=p - pos;
    vec3 pa=p - a;
    vec3 ba=b - a;
    float h=clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    return vec2(length(pa - ba * h) - r, material);
}
vec2 torus82(vec3 p, vec3 pos, vec2 t, float material) {
    p=p - pos;
    vec2 q=vec2(length2(p.xz) - t.x, p.y);
    return vec2(length8(q) - t.y, material);
}
vec2 torus88(vec3 p, vec3 pos, vec2 t, float material) {
    p=p - pos;
    vec2 q=vec2(length8(p.xz) - t.x, p.y);
    return vec2(length8(q) - t.y, material);
}

vec2 cylinder6(vec3 p, vec3 pos, vec2 h, float material) {
    p=p - pos;
    return vec2(max(length6(p.xz) - h.x, abs(p.y) - h.y), material);
}

//-------------------------------------
// Mixing object
//-------------------------------------
vec2 cross3D(vec3 p, vec3 pos, float size, float thick, float material) {
    p=p - pos;
    vec2 obj1=box(p.xyz, vec3(0.0,0.0,0.0), vec3(size,thick,thick), material);
    vec2 obj2=box(p.yzx, vec3(0.0,0.0,0.0), vec3(thick,size,thick), material);
    vec2 obj3=box(p.zxy, vec3(0.0,0.0,0.0), vec3(thick,thick,size), material);
    return vec2(min(obj1.x,min(obj2.x, obj3.x)), material);
}
