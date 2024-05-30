#pragma once
#define _USE_MATH_DEFINES
#include "Vectors.h"
#include "Mat.h"
#include "math.h"

class Camera {
public:
	vec3 wEye, wLookat, wVup;
	float fov, asp, fp, bp, YAngle;
public:
	Camera(vec3 eye, vec3 lookat, vec3 vup, float fov, float asp, float fp, float bp);
	mat4 V();
	mat4 P();
	void rotateHorizontal(float angle);
	void CalcRotation();
};