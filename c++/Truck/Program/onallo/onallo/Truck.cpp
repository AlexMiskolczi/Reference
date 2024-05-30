#include "Truck.h"

Truck::Truck(Camera* cam) {
	Truckrotation = 0.f;
	frontTiresAngle = 0.f;
	tireRotAngle = 0.f;
	speed = 0.f;
	this->cam = cam;
	centerPoint = vec3(0.f, 0.f, 0.f);
	Shader* shader = new PhongShader();
	body = new MeshCollection(cam,shader);
	frontTriresLeft = new MeshCollection(cam, shader);
	frontTriresRight = new MeshCollection(cam, shader);
	BackTiresLeft = new MeshCollection(cam,shader);
	BackTiresRight = new MeshCollection(cam, shader);
	Create();
}

void Truck::Create() {
	ObjectLoader loader;
	loader.LoadFile("truckbody.obj", "truckbody.mtl", "",1);
	for each (Mesh * mesh in *loader.GetMeshes())
	{
		mesh->SetRotAxis(vec3(0.f, 1.f, 0.f));
		body->AddMesh(mesh);
	}
	loader.Clear();

	std::vector<Transforms> fronttireBaseTransforms;

	fronttireBaseTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(3.7f, 0.25f, -0.75f), vec3(1.f, 1.f, 1.f), M_PI / 2.f));
	fronttireBaseTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(5.f, 0.25f, -0.75f), vec3(1.f, 1.f, 1.f), M_PI / 2.f));



	for each (Transforms transform in fronttireBaseTransforms)
	{
		loader.LoadFile("coloredFrontTire.obj", "coloredFrontTire.mtl", "",1);
		for each (Mesh * mesh in *loader.GetMeshes())
		{
			mesh->SetAngle(transform.angle);
			mesh->SetRotAxis(transform.rotA);
			mesh->SetScale(transform.scale);
			mesh->SetTransform(transform.trans);
			frontTriresLeft->AddMesh(mesh);
		}
		loader.Clear();
	}

	fronttireBaseTransforms.clear();
	fronttireBaseTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(3.7f, 0.25f, 0.75f), vec3(1.f, 1.f, 1.f), -M_PI / 2.f));
	fronttireBaseTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(5.f, 0.25f, 0.75f), vec3(1.f, 1.f, 1.f), -M_PI / 2.f));

	for each (Transforms transform in fronttireBaseTransforms)
	{
		loader.LoadFile("coloredFrontTire.obj", "coloredFrontTire.mtl", "",1);
		for each (Mesh * mesh in *loader.GetMeshes())
		{
			mesh->SetAngle(transform.angle);
			mesh->SetRotAxis(transform.rotA);
			mesh->SetScale(transform.scale);
			mesh->SetTransform(transform.trans);
			frontTriresRight->AddMesh(mesh);
		}
		loader.Clear();
	}



	std::vector<Transforms> backTireTransforms;

	backTireTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(0.55f, 0.25f, -0.6f), vec3(1.f, 1.f, 1.f), M_PI / 2.f));
	backTireTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(-0.4f, 0.25f, -0.6f), vec3(1.f, 1.f, 1.f), M_PI / 2.f));
	
	
	for each (Transforms transform in backTireTransforms)
	{
		loader.LoadFile("coloredRearTire.obj", "coloredRearTire.mtl", "",1);
		for each (Mesh * mesh in *loader.GetMeshes())
		{
			mesh->SetAngle(transform.angle);
			mesh->SetRotAxis(transform.rotA);
			mesh->SetScale(transform.scale);
			mesh->SetTransform(transform.trans);
			BackTiresLeft->AddMesh(mesh);
		}	
		loader.Clear();
	}

	backTireTransforms.clear();
	backTireTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(0.55f, 0.25f, 0.6f), vec3(1.f, 1.f, 1.f), -M_PI / 2.f));
	backTireTransforms.push_back(Transforms(vec3(0.f, 1.f, 0.f), vec3(-0.4f, 0.25f, 0.6f), vec3(1.f, 1.f, 1.f), -M_PI / 2.f));
	for each (Transforms transform in backTireTransforms)
	{
		loader.LoadFile("coloredRearTire.obj", "coloredRearTire.mtl", "",1);
		for each (Mesh * mesh in *loader.GetMeshes())
		{
			mesh->SetAngle(transform.angle);
			mesh->SetRotAxis(transform.rotA);
			mesh->SetScale(transform.scale);
			mesh->SetTransform(transform.trans);
			BackTiresRight->AddMesh(mesh);
		}
		loader.Clear();
	}

}

void Truck::Draw() {
	body->Draw();
	frontTriresLeft->Draw();
	frontTriresRight->Draw();
	BackTiresLeft->Draw();
	BackTiresRight->Draw();
}

Truck::Transforms::Transforms(vec3 rotA, vec3 trans, vec3 scale, float angle) {
	this->trans = trans;
	this->rotA = rotA;
	this->scale = scale;
	this->angle = angle;
}

void Truck::IncreaseFrontTiresAngle(long deltaTime) {
	float deltaTimeInSeconds = deltaTime / 1000.f;
	float t = deltaTimeInSeconds / 2.f;
	float deltaAngle = t * 2.f * M_PI / 9.f;
	if (frontTiresAngle <= 2.f * M_PI / 9.f && frontTiresAngle+deltaAngle<= 2.f * M_PI / 9.f) {
		frontTiresAngle += deltaAngle;
	}
	else if (frontTiresAngle <= 2.f * M_PI / 9.f && frontTiresAngle + deltaAngle > 2.f * M_PI / 9.f) {
		frontTiresAngle = 2.f * M_PI / 9.f;
	}
	for each (Mesh* mesh in *frontTriresLeft->GetMeshes())
	{
		mesh->SetAngle(M_PI / 2.f + frontTiresAngle);
	}
	for each (Mesh * mesh in *frontTriresRight->GetMeshes())
	{
		mesh->SetAngle(-M_PI / 2.f + frontTiresAngle);
	}
}

void Truck::DecreaseFrontTiresAngle(long deltaTime) {
	float deltaTimeInSeconds = deltaTime / 1000.f;
	float t = deltaTimeInSeconds / 2.f;
	float deltaAngle = t * 2.f * M_PI / 9.f;
	if (frontTiresAngle >= -2.f * M_PI / 9.f && frontTiresAngle + deltaAngle >= -2.f * M_PI / 9.f) {
		frontTiresAngle -= deltaAngle;
	}
	else if (frontTiresAngle >= -2.f * M_PI / 9.f && frontTiresAngle + deltaAngle < -2.f * M_PI / 9.f) {
		frontTiresAngle = -2.f * M_PI / 9.f;
	}
	for each (Mesh * mesh in *frontTriresLeft->GetMeshes())
	{
		mesh->SetAngle(M_PI / 2.f + frontTiresAngle);
	}
	for each (Mesh * mesh in *frontTriresRight->GetMeshes())
	{
		mesh->SetAngle(-M_PI / 2.f + frontTiresAngle);
	}
}

void Truck::CenterFrontTiresAngle(long delta) {
	float deltaTimeInSeconds = delta / 1000.f;
	float t = deltaTimeInSeconds / 1.f;
	float deltaAngle = t * 2.f * M_PI / 9.f;
	if (frontTiresAngle > 0.f) {
		if (frontTiresAngle - deltaAngle >= 0.f) {
			frontTiresAngle -= deltaAngle;
		}
		else {
			frontTiresAngle = 0.f;
		}
	}
	else if (frontTiresAngle < 0.f) {
		if (frontTiresAngle + deltaAngle <= 0.f) {
			frontTiresAngle += deltaAngle;
		}
		else {
			frontTiresAngle = 0.f;
		}
	}
	for each (Mesh * mesh in *frontTriresLeft->GetMeshes())
	{
		mesh->SetAngle(M_PI / 2.f + frontTiresAngle);
	}
	for each (Mesh * mesh in *frontTriresRight->GetMeshes())
	{
		mesh->SetAngle(-M_PI / 2.f + frontTiresAngle);
	}
}


void Truck::IncreaseSpeed(long delta) {
	float deltaTimeInSeconds = delta / 1000.f;
	if (speed >= 0) {
		float t = deltaTimeInSeconds / 20.f;
		float deltaspeed = t * 10.f;
		if (speed <= 10.f && speed + deltaspeed <= 10.f) {
			speed += deltaspeed;
		}
		else if (speed <= 10.f && speed + deltaspeed > 10.f) {
			speed = 10.f;
		}
	}
	else{
		float t = deltaTimeInSeconds / 5.f;
		float deltaspeed = t * 10.f;
		if (speed < 0.f && speed + deltaspeed <= 0.f) {
			speed += deltaspeed;
		}
		else if (speed <= 0.f && speed + deltaspeed > 0.f) {
			speed = 0.f;
		}
	}
	
}




void Truck::DecreaseSpeed(long delta) {
	float deltaTimeInSeconds = delta / 1000.f;
	if (speed > 0) {
		float t = deltaTimeInSeconds / 5.f;
		float deltaspeed = t * 10.f;
		if (speed >= 0.f && speed - deltaspeed >= 0.f) {
			speed -= deltaspeed;
		}
		else if (speed >= 0.f && speed - deltaspeed < 0.f) {
			speed = 0.f;
		}
	}
	else {
		float t = deltaTimeInSeconds / 20.f;
		float deltaspeed = t * 10.f;
		if (speed >= -10.f && speed - deltaspeed >= -10.f) {
			speed -= deltaspeed;
		}
		else if (speed >= -10.f && speed - deltaspeed < -10.f) {
			speed = -10.f;
		}
	}
	
}


void Truck::AutoDecreaseSpeed(long delta) {
	float deltaTimeInSeconds = delta / 1000.f;
	float t = deltaTimeInSeconds / 40.f;
	float deltaspeed = t * 10.f;
	if (speed > 0.f) {
		if (speed >= 0.f && speed - deltaspeed >= 0.f) {
			speed -= deltaspeed;
		}
		else if (speed > 0.f && speed - deltaspeed < 0.f) {
			speed = 0.f;
		}
	}
	else if(speed<0.f) {
		if (speed <= 0.f && speed + deltaspeed <= 0.f) {
			speed += deltaspeed;
		}
		else if (speed <= 0.f && speed + deltaspeed > 0.f) {
			speed = 0.f;
		}
	}
}

void Truck::Animate(long delta) {
	float deltaTimeInSeconds = delta / 1000.f;
	float omega = speed / 0.4f;
	float angle = omega * deltaTimeInSeconds;
	tireRotAngle += angle;
	if (tireRotAngle > 2.0f * M_PI) {
		tireRotAngle -= 2.0f * M_PI;
	}

	BackTiresLeft->SetRotAxis(vec3(1.f, 0.f, 0.f));
	BackTiresLeft->SetAngle(tireRotAngle);
	BackTiresRight->SetRotAxis(vec3(1.f, 0.f, 0.f));
	BackTiresRight->SetAngle(-tireRotAngle);

	frontTriresLeft->SetAngle(tireRotAngle);
	frontTriresLeft->SetRotAxis(vec3(1.f, 0.f, 0.f));
	frontTriresRight->SetAngle(-tireRotAngle);
	frontTriresRight->SetRotAxis(vec3(1.f, 0.f, 0.f));

	body->SetRotAxis(vec3(0.f, 1.f, 0.f));
	Truckrotation += frontTiresAngle / 1.5f * speed * deltaTimeInSeconds;
	dir = vec3(cosf(Truckrotation), 0, -sinf(Truckrotation));
	if (Truckrotation > 2.0f * M_PI) {
		Truckrotation -= 2.0f * M_PI;
	}
	if (Truckrotation < -2.0f * M_PI) {
		Truckrotation += 2.0f * M_PI;
	}
	body->SetAngle(Truckrotation);
	centerPoint = centerPoint + dir * speed*deltaTimeInSeconds;
	body->SetTransform(centerPoint);

	cam->wLookat = vec3(centerPoint.x, 3.f, centerPoint.z);
	vec4 camEye = vec4(-12.f, 8.f, 0.f, 1.f) * RotationMatrix(Truckrotation, vec3(0.f, 1.f, 0.f)) * TranslateMatrix(centerPoint);
	cam->wEye = vec3(camEye.x, camEye.y, camEye.z);
	cam->CalcRotation();
	

	frontTriresLeft->SetParent(body->M(), body->Minv());
	frontTriresRight->SetParent(body->M(), body->Minv());
	BackTiresLeft->SetParent(body->M(), body->Minv());
	BackTiresRight->SetParent(body->M(), body->Minv());

}
