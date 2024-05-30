# include "MeshCollection.h"

MeshCollection::MeshCollection(Camera* cam,Shader* shader) {
	camera = cam;
	this->trans = vec3(0.f, 0.f, 0.f);
	this->scale = vec3(1.f, 1.f, 1.f);
	this->rotAxis = vec3(0.f, 1.f, 0.f);
	this->rotangle = 0.f;
	lights.push_back(new Light(vec3(0.5f, 0.5f, 0.5f), vec4(-1.f,-1.f, -1.f,0.f), vec3(0.1f, 0.1f, 0.1f)));
	lights.push_back(new Light(vec3(0.5f, 0.5f, 0.5f), vec4(1.f, 1.f, 1.f, 0.f), vec3(0.1f, 0.1f, 0.1f)));
	Meshes = new std::vector<Mesh*>;
	this->shader = shader;
}

void MeshCollection::AddMesh(Mesh* mesh) {
	Meshes->push_back(mesh);
}

void MeshCollection::Draw() {
	RenderState state;
	for each (Mesh* mesh in *Meshes)
	{
		if (parentSet) {
			state.M =M() * ScaleMatrix(mesh->GetScale()) * RotationMatrix(mesh->GetAngle(), mesh->GetRotAxis()) * TranslateMatrix(mesh->GetTransform())* MFromParent;
			state.Minv =Minv() * ScaleMatrix(mesh->GetScale() / 1.f) * RotationMatrix(-mesh->GetAngle(), mesh->GetRotAxis()) * TranslateMatrix(-mesh->GetTransform()) * MinvFromParent;
		}
		else {
			state.M =M()*ScaleMatrix(mesh->GetScale()) * RotationMatrix(mesh->GetAngle(), mesh->GetRotAxis()) * TranslateMatrix(mesh->GetTransform());
			state.Minv=Minv()*ScaleMatrix(mesh->GetScale()/1.f) * RotationMatrix(-mesh->GetAngle(),mesh->GetRotAxis())*TranslateMatrix(-mesh->GetTransform());
		}
		
		state.wEye = camera->wEye;
		state.MVP = state.M * camera->V() * camera->P();
		state.material = mesh->GetMaterial();
		state.texture = mesh->GetTexture();
		state.lights = lights;
		shader->Bind(state);
		mesh->Draw();
	}

}

void MeshCollection::SetParent(mat4 M, mat4 Minv) {
	this->MFromParent = M;
	this->MinvFromParent = Minv;
	parentSet = true;
}

void MeshCollection::SetTransform(vec3 trans) {
	this->trans = trans;
}

void MeshCollection::SetScale(vec3 scale) {
	this->scale = scale;
}

void MeshCollection::SetRotAxis(vec3 axis) {
	this->rotAxis = axis;
}

void MeshCollection::SetAngle(float angle) {
	this->rotangle = angle;
}

mat4 MeshCollection::M() {
	return ScaleMatrix(scale) * RotationMatrix(rotangle, rotAxis) * TranslateMatrix(trans);
}

mat4 MeshCollection::Minv() {
	return ScaleMatrix(-scale) * RotationMatrix(-rotangle, rotAxis) * TranslateMatrix(-trans);
}

std::vector<Mesh*>* MeshCollection::GetMeshes() {
	return Meshes;
}