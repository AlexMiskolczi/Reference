#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <vector>
#include <string>

#if defined(__APPLE__)
#include <GLUT/GLUT.h>
#include <OpenGL/gl3.h>
#else
#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__)
#include <windows.h>
#endif
#include <GL/glew.h>	
#include <GL/freeglut.h>
#endif

#pragma once

const unsigned int windowWidth = 600, windowHeight = 600;
