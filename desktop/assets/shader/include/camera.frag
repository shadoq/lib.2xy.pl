
//-------------------------------------------
// Camera setup
//-------------------------------------------

Camera cameraSetup(Camera iCamera)
{
    #if CAMERA_MODE == 0
        Camera cam=iCamera;
    #else
        Camera cam=Camera(vec3(0.0), vec3(0.0), vec3(0.0), vec3(0.0), vec3(0.0), 1.0);
        cam.up=normalize(vec3(0.0, 1.0, 0.0));
        cam.focus=1.8;
    #endif

    //
    // Camera config
    //
    #if CAMERA_MODE == 1
        cam.pos=vec3(4.0, 2.0, 5.0);
        cam.target=vec3(0.0, 0.0, 0.0);
    #elif CAMERA_MODE == 2
        cam.pos=vec3(8.0, 4.0, 8.0);
        cam.target=vec3(0.0, 0.0, 0.0);
    #elif CAMERA_MODE == 3
        cam.pos=vec3(10.0, 10.0, 10.0);
        cam.target=vec3(0.0, 0.1, 0.0);
    #elif CAMERA_MODE == 4
        cam.pos=vec3(iMouse.x, iMouse.y, 5.0);
        cam.target=vec3(0.0, 0.0, 0.0);
    #elif CAMERA_MODE == 5
        cam.pos=vec3(-sin(time / 10.0) * 6.0, 3.0, cos(time / 10.0) * 6.0);
        cam.target=vec3(0.0, 0.0, 0.0);
    #elif CAMERA_MODE == 6
        cam.pos=vec3(0.0, 0.5, 0.0);
        cam.target=vec3(-sin(time / 10.0) * 5.0, cos(time / 4.0) * 3.0, cos(time / 10.0) * 5.0);
    #elif CAMERA_MODE == 7
        cam.pos=0. + vec3(cos(time * 0.2) * 10., 8.0 + sin(time * 0.1) * 2., sin(time * 0.2) * 10.);
        cam.target=vec3(0.0, 0.0, 0.0);
    #endif

    //
    // Calculate camera target
    //
    cam.dir=normalize(cam.target - cam.pos);
    cam.side=cross(cam.dir, cam.up);

    return cam;
}