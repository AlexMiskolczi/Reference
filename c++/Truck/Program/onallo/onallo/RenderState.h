#pragma once
#include "Material.h"
#include "Light.h"
#include "vector"
#include "Mat.h"
#include "Texture.h"

class RenderState {
public:
	mat4 MVP, M, Minv, V, P;
	Material* material;
	std::vector<Light*> lights;
	vec3 wEye;
	Texture* texture;
};