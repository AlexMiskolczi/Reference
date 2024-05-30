//Felhasznált modellek, átalakítva vagy eredeti formában:
//https://free3d.com/3d-model/low_poly_tree-816203.html
//https://free3d.com/3d-model/4axle-box-truck-152181.html
//https://free3d.com/3d-model/-rectangular-grass-patch--205749.html


#include "ObjectLoader.h"
#include "Camera.h"
#include "Truck.h"
#include "Terrain.h"

Truck* truck;
Terrain* terrain;
Camera* camera;
bool TurnLeft = false;
bool TurnRight = false;
bool Accelerate = false;
bool Decelerate = false;
long lastUpdate;


void ArrowHandler(int key, int x, int y) {
	switch (key)
	{
	case GLUT_KEY_LEFT: {
		camera->rotateHorizontal(-15.f*M_PI/180.f);
		break;
	}
	case GLUT_KEY_RIGHT: {
		camera->rotateHorizontal(15.f * M_PI / 180.f);
		break;
	}
	}
}

void onInitialization() {
	lastUpdate = glutGet(GLUT_ELAPSED_TIME);
	glutSpecialFunc(ArrowHandler);
	glViewport(0, 0, 600, 600);
	glEnable(GL_DEPTH_TEST);
	glDisable(GL_CULL_FACE);
	camera = new Camera(vec3(-12.f, 8.f, 0.f), vec3(2.f, 3.f, 0.f), vec3(0.f, 1.f, 0.f), 45.f, 1.f, 1.f, 100.f);
	truck = new Truck(camera);
	terrain = new Terrain(camera);
}

void onDisplay() {
	glClearColor(0.42, 0.812, 1, 0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	truck->Draw();
	terrain->Draw();
	glutSwapBuffers();
}


void onKeyboard(unsigned char key, int pX, int pY) {
	switch (key) {
	case 'a': {
		TurnLeft = true;
		break;
	}
	case 'd': {
		TurnRight = true;
		break;
	}
	case 'w': {
		Accelerate = true;
		break;
	}
	case 's': {
		Decelerate = true;
		break;
	}
	}
}
void onKeyboardUp(unsigned char key, int pX, int pY) {
	switch (key) {
	case 'a': {
		TurnLeft = false;
		break;
	}
	case 'd': {
		TurnRight = false;
		break;
	}
	case 'w': {
		Accelerate = false;
		break;
	}
	case 's': {
		Decelerate = false;
		break;
	}
	}
}
void onMouseMotion(int pX, int pY) {}
void onMouse(int button, int state, int pX, int pY) {}
void onIdle() {
	long current = glutGet(GLUT_ELAPSED_TIME);
	long delta = current - lastUpdate;
	if (TurnLeft) {
		truck->IncreaseFrontTiresAngle(delta);
	}
	if (TurnRight) {
		truck->DecreaseFrontTiresAngle(delta);
	}
	if (!TurnLeft && !TurnRight) {
		truck->CenterFrontTiresAngle(delta);
	}
	if (Accelerate) {
		truck->IncreaseSpeed(delta);
	}
	if (Decelerate) {
		truck->DecreaseSpeed(delta);
	}
	if (!Accelerate && !Decelerate) {
		truck->AutoDecreaseSpeed(delta);
	}
	truck->Animate(delta);
	glutPostRedisplay();
	lastUpdate = glutGet(GLUT_ELAPSED_TIME);
}
