#include "Terrain.h"

Terrain::Terrain(Camera* cam){
	TextureShader = new TexturedShader();
	Phongshader = new PhongShader();
	roads = new MeshCollection(cam, TextureShader);
	plain = new MeshCollection(cam, TextureShader);
	trees = new MeshCollection(cam, Phongshader);
	Create();
}

void Terrain::Create() {
	ObjectLoader loader;
	loader.LoadFile("road.obj", "road.mtl", "road.bmp",52);
	int i = -50;
	for each (Mesh* mesh in *loader.GetMeshes())
	{
		mesh->SetRotAxis(vec3(0.f, 1.f, 0.f));
		mesh->SetAngle(M_PI / 2.f);
		mesh->SetTransform(vec3(i*3.7f, -0.4f, 0.f));
		roads->AddMesh(mesh);
		i++;
	}
	loader.Clear();
	loader.LoadFile("road.obj", "road.mtl", "road.bmp", 25);
	i = 0;
	for each (Mesh * mesh in *loader.GetMeshes())
	{
		mesh->SetTransform(vec3(0.f, -0.4f, (i * 3.7f)+6.f));
		roads->AddMesh(mesh);
		i++;
	}
	loader.Clear();
	loader.Clear();
	loader.LoadFile("road.obj", "road.mtl", "road.bmp", 25);
	i = 0;
	for each (Mesh * mesh in *loader.GetMeshes())
	{
		mesh->SetTransform(vec3(0.f, -0.4f, (i * -3.7f) - 6.f));
		roads->AddMesh(mesh);
		i++;
	}
	loader.Clear();

	Plain* plainMaker = new Plain();

	plain->AddMesh(plainMaker->GetMesh());

	loader.LoadFile("coloredTree.obj", "coloredTree.mtl", "", 100);
	i = 0;
	float randX, randZ;
	for each (Mesh* mesh in *loader.GetMeshes())
	{
		randX = (std::rand() % (195 - 5 + 1) + 5)/1.f;
		randZ = (std::rand() % (195 - 5 + 1) + 5)/1.f;
		if (i < 100) {
			mesh->SetTransform(vec3(-randX, 0.f, -randZ));
		}
		else if (i < 200) {
			mesh->SetTransform(vec3(randX, 0.f, -randZ));
		}
		else if (i < 300) {
			mesh->SetTransform(vec3(randX, 0.f, randZ));
		}
		else {
			mesh->SetTransform(vec3(-randX, 0.f, randZ));
		}
		mesh->SetScale(vec3(0.1f, 0.1f, 0.1f));
		trees->AddMesh(mesh);
		i++;
	}
}

void Terrain::Draw() {
	roads->Draw();
	plain->Draw();
	trees->Draw();
}