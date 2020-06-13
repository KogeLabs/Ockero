#version 330

//in vec3 passColor;
in vec2 passTextureCoord;
out vec4 outColor;

uniform sampler2D tex;

void main() {
	outColor = texture(tex, passTextureCoord);
	//outColor = vec4(passColor, 1.0);
}