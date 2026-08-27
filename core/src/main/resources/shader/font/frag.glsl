#version 150 core

in vec2 vTexCoord;

out vec4 FragColor;

uniform sampler2D uTexture;
uniform vec4 uColor;

void main() {
    float gray = texture(uTexture, vec2(vTexCoord.x, 1.0 - vTexCoord.y)).r;
    FragColor = vec4(uColor.rgb * gray, uColor.a * gray);
}