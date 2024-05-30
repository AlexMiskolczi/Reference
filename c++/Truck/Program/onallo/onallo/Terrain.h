#pragma once
#include "MeshCollection.h"
#include "TexturedShader.h"
#include "PhongShader.h"
#include "ObjectLoader.h"
#include "Plain.h"
#include "cstdlib"


class Terrain {
private:
	MeshCollection* roads;
	MeshCollection* trees;
	MeshCollection* plain;
	Shader* TextureShader;
	Shader* Phongshader;
	void Create();
public:
	Terrain(Camera* cam);
	void Draw();
};