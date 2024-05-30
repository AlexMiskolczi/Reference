#include "Plain.h"

Plain::Plain() {
	Create();
}

void Plain::Create() {
	Texture* text = new Texture("grass.bmp", false);
	std::vector<vec3> pos;
	pos.push_back(vec3(200.f, -0.4f, -200.f));
	pos.push_back(vec3(-200.f, -0.4f, -200.f));
	pos.push_back(vec3(-200.f, -0.4f, 200.f));
	pos.push_back(vec3(200.f, -0.4f, -200.f));
	pos.push_back(vec3(200.f, -0.4f, 200.f));
	pos.push_back(vec3(-200.f, -0.4f, 200.f));

	std::vector<vec3> norm;
	norm.push_back(vec3(0.f, 1.f, 0.f));
	norm.push_back(vec3(0.f, 1.f, 0.f));
	norm.push_back(vec3(0.f, 1.f, 0.f));
	norm.push_back(vec3(0.f, 1.f, 0.f));
	norm.push_back(vec3(0.f, 1.f, 0.f));
	norm.push_back(vec3(0.f, 1.f, 0.f));

	std::vector<vec2> UV;
	UV.push_back(vec2(0.f, 200.f));
	UV.push_back(vec2(0.f, 0.f));
	UV.push_back(vec2(200.f, 0.f));
	UV.push_back(vec2(0.f, 200.f));
	UV.push_back(vec2(200.f, 200.f));
	UV.push_back(vec2(200.f, 0.f));
	
	Material* mat = new Material(vec3(1.f,1.f,1.f), vec3(0.f, 0.f, 0.f), vec3(1.f, 1.f, 1.f),100);

	mesh = new Mesh(pos, norm, UV, mat, text);
}

Mesh* Plain::GetMesh() {
	return mesh;
}