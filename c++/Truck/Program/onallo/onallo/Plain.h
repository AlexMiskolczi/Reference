#pragma once
#include "mesh.h"
class Plain {
private:
	Mesh* mesh;
public:
	Plain();
	void Create();
	Mesh* GetMesh();
};