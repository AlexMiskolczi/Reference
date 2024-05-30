#pragma once
#include "math.h"
class vec2 {
public:
	float x, y;
	vec2(float x0 = 0, float y0 = 0) { x = x0; y = y0; }
	vec2 operator*(float a) const { return vec2(x * a, y * a); }
	vec2 operator/(float a) const { return vec2(x / a, y / a); }
	vec2 operator+(const vec2& v) const { return vec2(x + v.x, y + v.y); }
	vec2 operator-(const vec2& v) const { return vec2(x - v.x, y - v.y); }
	vec2 operator*(const vec2& v) const { return vec2(x * v.x, y * v.y); }
	vec2 operator-() const { return vec2(-x, -y); }
};


inline float dot(const vec2 & v1, const vec2 & v2) {
	return (v1.x * v2.x + v1.y * v2.y);
}
	
inline float length(const vec2& v) { return sqrtf(dot(v, v)); }
	
inline vec2 normalize(const vec2& v) { return v * (1 / length(v)); }
	
inline vec2 operator*(float a, const vec2& v) { return vec2(v.x * a, v.y * a); }


class vec3 {
public:
	float x, y, z;
	vec3(float x0 = 0, float y0 = 0, float z0 = 0) { x = x0; y = y0; z = z0; }
	vec3(vec2 v) { x = v.x; y = v.y; z = 0; }
	vec3 operator*(float a) const { return vec3(x * a, y * a, z * a); }
	vec3 operator/(float a) const { return vec3(x / a, y / a, z / a); }
	vec3 operator+(const vec3& v) const { return vec3(x + v.x, y + v.y, z + v.z); }
	vec3 operator-(const vec3& v) const { return vec3(x - v.x, y - v.y, z - v.z); }
	vec3 operator*(const vec3& v) const { return vec3(x * v.x, y * v.y, z * v.z); }
	vec3 operator-()  const { return vec3(-x, -y, -z); }
};

inline float dot(const vec3& v1, const vec3& v2) { return (v1.x * v2.x + v1.y * v2.y + v1.z * v2.z); }
inline float length(const vec3& v) { return sqrtf(dot(v, v)); }

inline vec3 normalize(const vec3& v) { return v * (1 / length(v)); }

inline vec3 cross(const vec3& v1, const vec3& v2) {
	return vec3(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
}

inline vec3 operator*(float a, const vec3& v) { return vec3(v.x * a, v.y * a, v.z * a); }


class vec4 {
public:
	float x, y, z, w;
	vec4(float x0 = 0, float y0 = 0, float z0 = 0, float w0 = 0) { x = x0; y = y0; z = z0; w = w0; }
	float& operator[](int j) { return *(&x + j); }
	float operator[](int j) const { return *(&x + j); }
	vec4 operator*(float a) const { return vec4(x * a, y * a, z * a, w * a); }
	vec4 operator/(float d) const { return vec4(x / d, y / d, z / d, w / d); }
	vec4 operator+(const vec4& v) const { return vec4(x + v.x, y + v.y, z + v.z, w + v.w); }
	vec4 operator-(const vec4& v)  const { return vec4(x - v.x, y - v.y, z - v.z, w - v.w); }
	vec4 operator*(const vec4& v) const { return vec4(x * v.x, y * v.y, z * v.z, w * v.w); }
	void operator+=(const vec4 right) { x += right.x; y += right.y; z += right.z; w += right.w; }
};

inline float dot(const vec4& v1, const vec4& v2) {
	return (v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w);
}
inline vec4 operator*(float a, const vec4& v) {
	return vec4(v.x * a, v.y * a, v.z * a, v.w * a);
}