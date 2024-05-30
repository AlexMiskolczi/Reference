#include "ObjectLoader.h"

void ObjectLoader::LoadFile(std::string path, std::string mtlPath, std::string TextPath,int loadCount) {
	meshes = new std::vector<Mesh*>;
	std::ifstream file(path);
	std::string line;
	bool canRead = false;
	if (file.is_open()) {
		while (std::getline(file, line)) {
			lines.push_back(line);
		}
	}
	else
	{
		printf("Fájl megnyitása sikertelen!\n");
	}
	std::ifstream mtlfile(mtlPath);
	std::string mtlline;
	canRead = false;
	if (mtlfile.is_open()) {
		while (std::getline(mtlfile, mtlline)) {
			mtllines.push_back(mtlline);
		}
	}
	else
	{
		printf("Fájl megnyitása sikertelen!\n");
	}
	if (!TextPath.empty()) {
		text = new Texture(TextPath, false);
	}
	for (int i = 0; i < loadCount; i++) {
		ExtractInfo();
	}
	
}

void ObjectLoader::ExtractInfo() {
	for each (std::string s in lines) {
		if (s.find("v ") != std::string::npos) {
			loadedVerticies.push_back(Extractvec3(Split(ErasePrefix("v ", s),' ')));
		}
		else if (s.find("vn ") != std::string::npos) {
			loadedNormals.push_back(Extractvec3(Split(ErasePrefix("v ", s),' ')));
		}
		else if (s.find("vt ") != std::string::npos) {
			loadedUV.push_back(Extractvec2(Split(ErasePrefix("v ", s),' ')));
		}
		else if (s.find("usemtl ") != std::string::npos) {
			if (meshes->size() == 0 || faceInfo.size()==0) {
				CreateMaterial(ErasePrefix("usemtl ",s));
			}
			if (faceInfo.size() > 0) {
				CreateObjectInfoToCreateMesh();
				meshes->push_back(new Mesh(VertsInOrder, NormalsInOrder, UVsInOrder, mat,text));
				VertsInOrder.clear();
				NormalsInOrder.clear();
				UVsInOrder.clear();
				CreateMaterial(ErasePrefix("usemtl ", s));
			}
		}
		else if (s.find("f ") != std::string::npos) {
			std::vector<std::string> split = Split(ErasePrefix("f ", s),' ');
			ExtractFaceInfo(split);
		}
		else if (s.find("o ") != std::string::npos) {
			if (loadedVerticies.size() > 0) {
				CreateObjectInfoToCreateMesh();
				meshes->push_back(new Mesh(VertsInOrder, NormalsInOrder, UVsInOrder, mat,text));
				faceInfo.clear();
				VertsInOrder.clear();
				NormalsInOrder.clear();
				UVsInOrder.clear();
			}
		}
		
	}
	CreateObjectInfoToCreateMesh();
	meshes->push_back(new Mesh(VertsInOrder, NormalsInOrder, UVsInOrder, mat, text));
}

vec3 ObjectLoader::Extractvec3(std::vector<std::string> splitLine) {
	vec3 ret;
	ret.x = std::stof(splitLine[0]);
	ret.y= std::stof(splitLine[1]);
	ret.z = std::stof(splitLine[2]);
	return ret;
}

vec2 ObjectLoader::Extractvec2(std::vector<std::string> splitLine) {
	vec2 ret;
	ret.x = std::stof(splitLine[0]);
	ret.y = std::stof(splitLine[1]);
	return ret;
}


void ObjectLoader::ExtractFaceInfo(std::vector<std::string> splitLine) {
	std::vector<std::string> splitSplit;
	std::vector<int> pushback;
	for each (std::string s in splitLine)
	{
		splitSplit = Split(s, '/');
		for each (std::string ss in splitSplit)
		{
			pushback.push_back(std::stoi(ss));
		}
		faceInfo.push_back(pushback);
		pushback.clear();
	}
}

std::string ObjectLoader::ErasePrefix(std::string prefix, std::string line) {
	size_t first=0, last=0;
	if (line.find(prefix[0]) != std::string::npos) {
		first = line.find(prefix[0]);
	}
	if (line.find(prefix[prefix.size() - 1],first) != std::string::npos) {
		last = line.find(prefix[prefix.size() - 1], first);
	}
	return line.erase(first, last - first);
}

std::vector<std::string> ObjectLoader::Split(std::string line, char splitChar) {
	std::vector<std::string> split;
	std::string s;
	for each (char c in line)
	{
		if (c == splitChar) {
			if ((s.find_first_not_of(splitChar) != std::string::npos)) {
				split.push_back(s);
			}
			s = std::string();
		}
		if (c != splitChar) {
			s += c;
		}
	}
	if ((s.find_first_not_of(splitChar) != std::string::npos) && s.size() > 0) {
		split.push_back(s);
	}
	return split;
}


void ObjectLoader::CreateObjectInfoToCreateMesh(){
	for each (std::vector<int> vertinfo in faceInfo)
	{

		VertsInOrder.push_back(loadedVerticies.at(vertinfo.at(0)-1));
		NormalsInOrder.push_back(loadedNormals.at(vertinfo.at(2) - 1));
		UVsInOrder.push_back(loadedUV.at(vertinfo.at(1) - 1));
	}
}


void ObjectLoader::CreateMaterial(std::string name) {
	std::string Ka, Kd, Ks, s;
	for (int i = 0; i < mtllines.size(); i++) {
		if (mtllines.at(i).find(name) != std::string::npos) {
			Ka = mtllines.at(i + 2);
			Kd = mtllines.at(i + 3);
			Ks = mtllines.at(i + 4);
			s = mtllines.at(i + 1);
			break;
		}
	}
	Ka = ErasePrefix("Ka ", Ka);
	Kd = ErasePrefix("Kd ", Kd);
	Ks = ErasePrefix("Ks ", Ks);
	s = ErasePrefix("Ns ", s);
	mat = new Material(Extractvec3(Split(Kd, ' ')), Extractvec3(Split(Ks, ' ')), Extractvec3(Split(Ka, ' ')), std::stof(s));
}


std::vector<Mesh*>* ObjectLoader::GetMeshes() {
	return meshes;
}

void ObjectLoader::Clear() {
	loadedNormals.clear();
	loadedVerticies.clear();
	loadedUV.clear();
	lines.clear();
	mtllines.clear();
	faceInfo.clear();
	VertsInOrder.clear();
	NormalsInOrder.clear();
	UVsInOrder.clear();
	meshes->clear();
};