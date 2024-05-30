#pragma once
#include "MeshCollection.h"
#include "Vectors.h"
#include "ObjectLoader.h"
#include "TexturedShader.h"
#include "PhongShader.h"

class Truck {
private:
	class Transforms {	
	public:
		vec3 rotA;
		vec3 trans;
		vec3 scale;
		float angle;
		Transforms(vec3 rotA, vec3 trans, vec3 scale, float angle);
	};
	MeshCollection* body;
	MeshCollection* frontTriresLeft;
	MeshCollection* frontTriresRight;
	MeshCollection* BackTiresLeft;
	MeshCollection* BackTiresRight;
	float frontTiresAngle;
	float tireRotAngle;
	float speed;
	vec3 dir;
	vec3 centerPoint;
	float Truckrotation;
	Camera* cam;
public:
	Truck(Camera* cam);
	void IncreaseFrontTiresAngle(long deltaTime);
	void DecreaseFrontTiresAngle(long deltaTime);
	void CenterFrontTiresAngle(long deltaTime);
	void IncreaseSpeed(long deltaTime);
	void DecreaseSpeed(long deltaTime);
	void AutoDecreaseSpeed(long deltaTime);
	void Animate(long delta);
	void Create();
	void Draw();
};