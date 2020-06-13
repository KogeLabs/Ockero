#version 330

layout(location = 0) in vec3 position;
layout(location = 2) in vec2 textureCoord;

out vec2 passTextureCoord;

uniform mat4 model;
uniform mat4 projection;

void main() {

	gl_Position = projection  * model * vec4(position, 1.0) ;
	passTextureCoord = textureCoord;

}