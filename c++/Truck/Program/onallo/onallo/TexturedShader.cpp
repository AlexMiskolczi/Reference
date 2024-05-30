#include "TexturedShader.h"

TexturedShader::TexturedShader() {
	create(vertexSource, fragmentSource, "fragmentColor");
}

void TexturedShader::Bind(RenderState &state) {
	Use();
	setUniform(state.MVP, "MVP");
	setUniform(state.M, "M");
	setUniform(state.Minv, "Minv");
	setUniform(state.wEye, "wEye");
	setUniformMaterial(*state.material, "material");
	setUniform(*state.texture, std::string("diffuseTexture"));

	setUniform((int)state.lights.size(), "nLights");
	for (unsigned int i = 0; i < state.lights.size(); i++) {
		setUniformLight(*state.lights[i], std::string("lights[") + std::to_string(i) + std::string("]"));
	}
}