#include "Camera.h"
#include "iostream"

Camera::Camera(vec3 eye, vec3 lookat, vec3 vup, float fov, float asp, float fp, float bp) {
	wEye = eye;
	wLookat = lookat;
	wVup = vup;
	this->fov = fov * M_PI / 180.f;
	this->asp = asp;
	this->fp = fp;
	this->bp = bp;
	YAngle = 0.f;
}

mat4 Camera::V() {
	vec3 w = normalize(wEye - wLookat);
	vec3 u = normalize(cross(wVup, w));
	vec3 v = cross(w, u);
	return TranslateMatrix(wEye * (-1)) * mat4(u.x, v.x, w.x, 0,
		u.y, v.y, w.y, 0,
		u.z, v.z, w.z, 0,
		0, 0, 0, 1);
}

mat4 Camera::P() {
	return mat4(1 / (tan(fov / 2) * asp), 0, 0, 0,
		0, 1 / tan(fov / 2), 0, 0,
		0, 0, -(fp + bp) / (bp - fp), -1,
		0, 0, -2 * fp * bp / (bp - fp), 0);
}

void Camera::rotateHorizontal(float angle) {
	YAngle += angle;
	if (YAngle > 2.f * M_PI) {
		YAngle -= 2.f * M_PI;
	}
}

void Camera::CalcRotation() {
	vec4 camEye = vec4(wEye.x, wEye.y, wEye.z, 1.f) * TranslateMatrix(-wLookat) * RotationMatrix(YAngle, vec3(0.f, 1.f, 0.f)) * TranslateMatrix(wLookat);
	wEye = vec3(camEye.x, camEye.y, camEye.z);
}


