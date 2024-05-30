#pragma once
#include "Vectors.h"

class Light {
public:
	vec3 La, Le;
	vec4 pos;
	Light(vec3 Le, vec4 Pos, vec3 ambient) {
		this->Le = Le;
		this->pos = Pos;
		this->La = ambient;
	};
};