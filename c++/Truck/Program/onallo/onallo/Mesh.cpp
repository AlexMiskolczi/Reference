#include "mesh.h"
#include "stdio.h"


Mesh::Mesh(std::vector<vec3> verts, std::vector<vec3> norms, std::vector<vec2> Uv, Material* mat,Texture* text) {
	this->mat = mat;
	this->text = text;
	this->trans = vec3(0.f, 0.f, 0.f);
	this->scale = vec3(1.f, 1.f, 1.f);
	this->rotAxis = vec3(0.f, 1.f, 0.f);
	this->rotangle = 0.f;
	for(int i = 0; i < verts.size(); i++){
		data.push_back(VertexData(verts.at(i),norms.at(i),Uv.at(i)));
	}
	LoadToGPU();
}

void Mesh::LoadToGPU() {
	glGenVertexArrays(1, &vao);
	glBindVertexArray(vao);
	glGenBuffers(1, &vbo);
	glBindVertexArray(vao);
	glBindBuffer(GL_ARRAY_BUFFER, vbo);
	glBufferData(GL_ARRAY_BUFFER, data.size() * sizeof(VertexData),
		&data[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(0);
	glEnableVertexAttribArray(1);
	glEnableVertexAttribArray(2);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE,
		sizeof(VertexData), (void*)offsetof(VertexData, pos));
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE,
		sizeof(VertexData), (void*)offsetof(VertexData, norm));
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE,
		sizeof(VertexData), (void*)offsetof(VertexData, UV));
}

void Mesh::Draw() {
	glBindVertexArray(vao);
	glDrawArrays(GL_TRIANGLES, 0, data.size());
}

Material* Mesh::GetMaterial() {
	return mat;
}

Texture* Mesh::GetTexture() {
	return text;
}


Mesh::~Mesh() {
	glDeleteBuffers(1, &vbo);
	glDeleteVertexArrays(1, &vao);
}


void Mesh::SetModel(vec3 t, vec3 rotAxis, vec3 s, float rotAngle) {
	trans = t;
	scale = s;
	this->rotAxis = rotAxis;
	this->rotangle = rotAngle;
};

vec3 Mesh::GetTransform() {
	return trans;
}

vec3 Mesh::GetScale() {
	return scale;
}

vec3 Mesh::GetRotAxis() {
	return rotAxis;
}

float Mesh::GetAngle() {
	return rotangle;
}

void Mesh::SetTransform(vec3 trans) {
	this->trans = trans;
}

void Mesh::SetScale(vec3 scale) {
	this->scale = scale;
}

void Mesh::SetRotAxis(vec3 axis) {
	this->rotAxis = axis;
}

void Mesh::SetAngle(float angle) {
	this->rotangle = angle;
}