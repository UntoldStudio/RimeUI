#version 150 core
in vec2 aPos;
in vec2 aTexCoord;
in vec4 aColor;
out vec2 TexCoord;
out vec4 VertexColor;
uniform mat4 uProjection;
void main() {
    gl_Position = uProjection * vec4(aPos, 0.0, 1.0);
    TexCoord = aTexCoord;
    VertexColor = aColor;
}