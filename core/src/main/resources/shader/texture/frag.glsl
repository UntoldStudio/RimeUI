#version 150 core
in vec2 TexCoord;
in vec4 VertexColor;
out vec4 FragColor;
uniform sampler2D uTexture;
void main() {
    FragColor = texture(uTexture, TexCoord) * VertexColor;
}