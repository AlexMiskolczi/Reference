#pragma once
#include "RenderState.h"
#include "GPUProgram.h"
#include "Light.h"

class Shader:public GPUProgram {
public:
	virtual void Bind(RenderState& state) = 0;
protected:
	void setUniformMaterial(const Material& material, const std::string& name);
	void setUniformLight(const Light& light, const std::string& name);
};