#version 150 core
in vec2 vTexCoord;
out vec4 FragColor;
uniform sampler2D uTexture;
uniform vec4 uColor;
void main() {
    vec2 flipped = vec2(vTexCoord.x, 1.0 - vTexCoord.y);
    float alpha = texture(uTexture, flipped).r;
    FragColor = vec4(uColor.rgb, uColor.a * alpha);
}