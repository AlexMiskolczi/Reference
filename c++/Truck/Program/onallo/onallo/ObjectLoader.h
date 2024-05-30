#pragma once
#include "vector"
#include "Vectors.h"
#include "mesh.h"
#include "string"
#include "fstream"
#include "Material.h"
#include "Texture.h"


class ObjectLoader {
private:
	std::vector<vec3> loadedVerticies;
	std::vector<vec3> loadedNormals;
	std::vector<vec2> loadedUV;
	std::vector<std::string> lines;
	std::vector<std::string> mtllines;
	std::vector<std::vector<int>> faceInfo;
	std::vector<vec3> VertsInOrder;
	std::vector<vec3> NormalsInOrder;
	std::vector<vec2> UVsInOrder;
	Material* mat;
	Texture* text;
	std::vector<Mesh*>* meshes;

	std::string ErasePrefix(std::string prefix, std::string line);
	std::vector<std::string> Split(std::string line, char splitChar);
	void CreateMaterial(std::string name);
	void CreateObjectInfoToCreateMesh();
	vec3 Extractvec3(std::vector<std::string> splitLine);
	vec2 Extractvec2(std::vector<std::string> splitLine);
	void ExtractFaceInfo(std::vector<std::string> splitLine);
	void ExtractInfo();
public:
	void LoadFile(std::string path,std::string mtlPath, std::string TextPath,int loadCount);
	void Clear();
	std::vector<Mesh*>* GetMeshes();
};