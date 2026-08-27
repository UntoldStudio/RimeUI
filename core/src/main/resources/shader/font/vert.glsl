#version 150 core

in vec2 aPos;
in vec2 aTexCoord;

uniform mat4 uProjection;

out vec2 vTexCoord;

void main() {
    gl_Position = uProjection * vec4(aPos, 0.0, 1.0);
    vTexCoord = aTexCoord;
}