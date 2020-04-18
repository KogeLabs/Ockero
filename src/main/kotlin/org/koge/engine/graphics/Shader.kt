/**
 * Copyright (C) 2020 Moncef YABI
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.koge.engine.graphics
/**
 *
 * @author Moncef YABI
 */
import org.joml.*
import org.koge.engine.utils.Utils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.system.MemoryStack


class Shader ( vertexPath: String, fragmentPath:String) {

    private var vertexID = 0
    private  var fragmentID:Int = 0
    private  var programID:Int = 0
    private var vertexFile:String? = Utils.readContentFromFile(vertexPath)
    private var fragmentFile:String? = Utils.readContentFromFile(fragmentPath)

    fun create() {
        programID = GL20.glCreateProgram()
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER)

        GL20.glShaderSource(vertexID, vertexFile)
        GL20.glCompileShader(vertexID)

        if (GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            println("Vertex Shader: " + GL20.glGetShaderInfoLog(vertexID))
            return
        }

        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER)

        GL20.glShaderSource(fragmentID, fragmentFile)
        GL20.glCompileShader(fragmentID)

        if (GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            println("Fragment Shader: " + GL20.glGetShaderInfoLog(fragmentID))
            return
        }

        GL20.glAttachShader(programID, vertexID)
        GL20.glAttachShader(programID, fragmentID)

        GL20.glLinkProgram(programID)
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            println("Program Linking: " + GL20.glGetProgramInfoLog(programID))
            return
        }

        GL20.glValidateProgram(programID)
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            println("Program Validation: " + GL20.glGetProgramInfoLog(programID))
            return
        }
    }

    fun getAttributeLocation(name: CharSequence?): Int {
        return GL20.glGetAttribLocation(programID, name)
    }


    fun enableVertexAttribute(location: Int) {
        GL20.glEnableVertexAttribArray(location)
    }

    fun disableVertexAttribute(location: Int) {
        GL20.glDisableVertexAttribArray(location)
    }

    fun pointVertexAttribute(location: Int, size: Int, strprogramIDe: Int, offset: Long) {
        GL20.glVertexAttribPointer(location, size, GL20.GL_FLOAT, false, strprogramIDe, offset)
    }


    fun getUniformLocation(name: CharSequence): Int {
        return GL20.glGetUniformLocation(programID, name)
    }

    fun setUniform(name: String, value: Float) {
        GL20.glUniform1f(getUniformLocation(name), value)
    }

    fun setUniform(name: String, value: Int) {
        GL20.glUniform1i(getUniformLocation(name), value)
    }

    fun setUniform(name: String, value: Boolean) {
        GL20.glUniform1i(getUniformLocation(name), if (value) 1 else 0)
    }

    fun setUniform(location: Int, value: Vector2f) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(2)
            value.get(buffer)
            GL20.glUniform2fv(location, buffer)
        }
    }

    fun setUniform(location: Int, value: Vector3f) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(3)
            value.get(buffer)
            GL20.glUniform3fv(location, buffer)
        }
    }

    fun setUniform(location: Int, value: Vector4f) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(4)
            value.get(buffer)
            GL20.glUniform4fv(location, buffer)
        }
    }

    fun setUniform(location: Int, value: Matrix2f) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(2 * 2)
            value.get(buffer)
            GL20.glUniformMatrix2fv(location, false, buffer)
        }
    }

    fun setUniform(location: Int, value: Matrix3f) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(3 * 3)
            value.get(buffer)
            GL20.glUniformMatrix3fv(location, false, buffer)
        }
    }

    fun setUniform(location: Int, value: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(4 * 4)
            value.get(buffer)
            GL20.glUniformMatrix4fv(location, false, buffer)
        }
    }


    fun bind() {
        GL20.glUseProgram(programID)
    }

    fun unbind() {
        GL20.glUseProgram(0)
    }

    fun destroy() {
        GL20.glDetachShader(programID, vertexID)
        GL20.glDetachShader(programID, fragmentID)
        GL20.glDeleteShader(vertexID)
        GL20.glDeleteShader(fragmentID)
        GL20.glDeleteProgram(programID)
    }

}