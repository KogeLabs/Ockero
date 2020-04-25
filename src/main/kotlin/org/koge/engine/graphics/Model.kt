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

import org.joml.Vector2f
import org.joml.Vector3f
import org.koge.engine.graphics.font.Glyph
import org.koge.engine.graphics.texture.Texture
import org.koge.engine.graphics.texture.TextureLoader
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryStack.*
import java.nio.FloatBuffer


/**
 * This class represent a Game model. A model is the basic entity holding the Vertex information
 * @author Moncef YABI
 */
class Model {

    private var vao: Int = 0
    private var pbo: Int = 0
    private var ibo: Int = 0
    private var cbo: Int = 0
    private var tbo: Int = 0

    var width = 0f
    var height = 0f
    private lateinit var vertices: Array<Vertex>
    lateinit var indices: IntArray
    lateinit var texture: Texture

    /**
     * Create a model form a local image file
     *
     * @param path
     * @param c
     * @return Model
     */
    fun createFromImageFile(path: String, c: Color = Color.WHITE):Model {
        texture = TextureLoader.create(path)
        return createFromTexture(texture, c)
    }
    
    private fun createFromTexture(texture: Texture, c: Color = Color.WHITE): Model {

        width = texture.width.toFloat()
        height = texture.height.toFloat()
        initVertices(width, height, 0.0f, 1.0f,c)
        pushToOpenGL()
        return this
    }

    /**
     * Create a model form a glyph texture
     *
     * @param texture
     * @param glyph
     * @param c
     * @return Model
     */
    fun createFromGlyphTexture(texture: Texture, glyph:Glyph, c: Color = Color.WHITE): Model {
        this.texture=texture
        /* Texture coordinates */
        val s1 = glyph.x / texture.width.toFloat()
        val s2 = (glyph.x + glyph.width) / texture.width.toFloat()

        initVertices(glyph.width.toFloat(),glyph.height.toFloat(), s1, s2, c)
        pushToOpenGL()
        return this
    }

    private fun initVertices(
        width: Float, height: Float,
        s1: Float, s2: Float, c: Color
    ) {
        vertices = arrayOf(
            Vertex(Vector3f(0f, height, 0.0f), c, Vector2f(s1, 0.0f)),
            Vertex(Vector3f(0f, 0f, 0.0f), c, Vector2f(s1, 1.0f)),
            Vertex(Vector3f(width, 0f, 0.0f), c, Vector2f(s2, 1.0f)),
            Vertex(Vector3f(width, height, 0.0f), c, Vector2f(s2, 0.0f))
        )
    }

    private fun pushToOpenGL() {
        vao = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(vao)

        indices = intArrayOf(
            0, 1, 3,
            3, 1, 2
        )

        stackPush().use {
            val positionBuffer = stackMallocFloat(vertices.size * 3)
            val positionData = FloatArray(vertices.size * 3)
            for (i in vertices.indices) {
                positionData[i * 3] = vertices[i].position.x
                positionData[i * 3 + 1] = vertices[i].position.y
                positionData[i * 3 + 2] = vertices[i].position.z
            }
            positionBuffer.put(positionData).flip()

            pbo = storeData(positionBuffer, 0, 3)

            val colorBuffer = stackMallocFloat(vertices.size * 3)
            val colorData = FloatArray(vertices.size * 3)
            for (i in vertices.indices) {
                colorData[i * 3] = vertices[i].color.r
                colorData[i * 3 + 1] = vertices[i].color.g
                colorData[i * 3 + 2] = vertices[i].color.b
            }
            colorBuffer.put(colorData).flip()

            cbo = storeData(colorBuffer, 1, 3)
            val textureBuffer = stackMallocFloat(vertices.size * 2)
            val textureData = FloatArray(vertices.size * 2)
            for (i in vertices.indices) {
                textureData[i * 2] = vertices[i].textureCoord.x
                textureData[i * 2 + 1] = vertices[i].textureCoord.y
            }
            textureBuffer.put(textureData).flip()

            tbo = storeData(textureBuffer, 2, 2)
            val indicesBuffer = stackMallocInt(indices.size)
            indicesBuffer.put(indices).flip()

            ibo = GL15.glGenBuffers()
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW)
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
        }
    }

    /**
     * Get the VAO object id
     *
     * @return Int
     */
    fun getVAO(): Int {
        return vao
    }

    /**
     * Get the PBO object id
     *
     * @return Int
     */
    fun getPBO(): Int {
        return pbo
    }

    /**
     * Get the IBO object id
     *
     * @return Int
     */
    fun getIBO(): Int {
        return ibo
    }


    private fun storeData(buffer: FloatBuffer, index: Int, size: Int): Int {
        val bufferID = GL15.glGenBuffers()
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        return bufferID
    }

    /**
     * Clear the OpenGL Buffers
     *
     */
    fun clearBuffers(){
        GL15.glDeleteBuffers(pbo)
        GL15.glDeleteBuffers(cbo)
        GL15.glDeleteBuffers(ibo)
        GL15.glDeleteBuffers(tbo)
        GL30.glDeleteVertexArrays(vao)
    }

    /**
     * Destroy the Texture buffers to remove them from memory. Needs to be called after the Koge session was closed.
     *
     */
    fun destroy() {
        clearBuffers()
        texture.destroy()
    }
}