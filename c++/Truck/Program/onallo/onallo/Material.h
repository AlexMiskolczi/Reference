#pragma once
#include "Vectors.h"

class Material {
public:
	vec3 ka, kd, ks;
	float shininess;
	Material(vec3 kd, vec3 ks, vec3 ka, float shine) {
		this->ka = ka;
		this->ks = ks;
		this->kd = kd;
		this->shininess = shine;
	}
};