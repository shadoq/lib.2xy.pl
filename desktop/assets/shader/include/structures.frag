
//-------------------------------------
// Data structures
//-------------------------------------
struct Camera {
    vec3 pos;
    vec3 target;
    vec3 up;
    vec3 dir;
    vec3 side;
    float focus;
};

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
    int texture;
    float reflection;
    float absorption;
};

struct Intersect {
    bool hit;
    float distance;
    float step;
    float grey;
    vec3 pos;
    vec3 normal;
    int stepCount;
    Material mat;
};

struct Light {
    vec3 posistion;
    vec3 ambientColor;
    vec3 color;
};

struct Sky {
    vec3 horizontalColor;
    vec3 spaceColor;
    float horizontal;
};

struct Sun {
    vec3 posistion;
    vec3 color;
    float haloSize;
    float haloPower;
    float discSize;
    float discPower;
    float discArea;
};

Light lights[NUM_OF_LIGHTS];
Material materials[NUM_OF_MATERIALS];
Sun sunLight;
Sky sky;
Camera iCamera;
