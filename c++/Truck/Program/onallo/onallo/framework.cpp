#include "framework.h"


void onInitialization();
void onDisplay();
void onKeyboard(unsigned char key, int pX, int pY);
void onKeyboardUp(unsigned char key, int pX, int pY);
void onMouseMotion(int pX, int pY);
void onMouse(int button, int state, int pX, int pY);
void onIdle();


int main(int argc, char * argv[]) {
	glutInit(&argc, argv);
	int majorVersion = 3, minorVersion = 3;
#if !defined(__APPLE__)
	glutInitContextVersion(majorVersion, minorVersion);
#endif
	glutInitWindowSize(600, 600);				
	glutInitWindowPosition(100, 100);						
#if defined(__APPLE__)
	glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH | GLUT_3_2_CORE_PROFILE);  
#else
	glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH);
#endif
	glutCreateWindow(argv[0]);

#if !defined(__APPLE__)
	glewExperimental = true;	
	glewInit();
#endif
	printf("GL Vendor    : %s\n", glGetString(GL_VENDOR));
	printf("GL Renderer  : %s\n", glGetString(GL_RENDERER));
	printf("GL Version (string)  : %s\n", glGetString(GL_VERSION));
	glGetIntegerv(GL_MAJOR_VERSION, &majorVersion);
	glGetIntegerv(GL_MINOR_VERSION, &minorVersion);
	printf("GL Version (integer) : %d.%d\n", majorVersion, minorVersion);
	printf("GLSL Version : %s\n", glGetString(GL_SHADING_LANGUAGE_VERSION));
	onInitialization();
	glutDisplayFunc(onDisplay);                
	glutMouseFunc(onMouse);
	glutIdleFunc(onIdle);
	glutKeyboardFunc(onKeyboard);
	glutKeyboardUpFunc(onKeyboardUp);
	glutMotionFunc(onMouseMotion);
	glutMainLoop();
	return 1;
}
