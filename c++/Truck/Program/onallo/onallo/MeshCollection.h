#pragma once
#include "vector"
#include "mesh.h"
#include "Camera.h"
#include "Shader.h"
#include "Light.h"
#include "RenderState.h"
#include "Mat.h"

class MeshCollection {
protected:
	std::vector<Mesh*>* Meshes;
	Camera* camera;
	Shader* shader;
	std::vector<Light*> lights;
	vec3 trans, rotAxis, scale;
	float rotangle;
	mat4 MFromParent;
	mat4 MinvFromParent;
	bool parentSet = false;
	
public:
	MeshCollection(Camera* cam,Shader* shader);
	mat4 M();
	mat4 Minv();
	void SetParent(mat4 M, mat4 Minv);
	std::vector<Mesh*>* GetMeshes();
	void AddMesh(Mesh* mesh);
	void Draw();
	void SetTransform(vec3 trans);
	void SetScale(vec3 scale);
	void SetRotAxis(vec3 axis);
	void SetAngle(float angle);
};