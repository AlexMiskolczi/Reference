#pragma once
#include "vector"
#include "Vectors.h"
#include "Material.h"
#include "Texture.h"

#if defined(__APPLE__)
#include <GLUT/GLUT.h>
#include <OpenGL/gl3.h>
#else
#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__)
#include <windows.h>
#endif
#include <GL/glew.h>	
#include <GL/freeglut.h>
#endif

class Mesh {
private:
	struct VertexData {
		vec3 pos;
		vec3 norm;
		vec2 UV;
	public:
		VertexData(vec3 pos, vec3 norm, vec2 UV) {
			this->pos = pos;
			this->norm = norm;
			this->UV = UV;
		}
	};
	unsigned int vao, vbo;
	std::vector<VertexData> data;
	Material* mat;
	Texture* text;
	vec3 trans, rotAxis, scale;
	float rotangle;
public:
	Mesh(std::vector<vec3> verts, std::vector<vec3> norms, std::vector<vec2> Uv, Material* mat, Texture* text);
	void SetModel(vec3 t, vec3 rotAxis, vec3 s, float rotAngle);
	vec3 GetTransform();
	vec3 GetScale();
	vec3 GetRotAxis();
	void SetTransform(vec3 trans);
	void SetScale(vec3 scale);
	void SetRotAxis(vec3 axis);
	void SetAngle(float angle);
	float GetAngle();
	void LoadToGPU();
	void Draw();
	Material* GetMaterial();
	Texture* GetTexture();
	~Mesh();
};