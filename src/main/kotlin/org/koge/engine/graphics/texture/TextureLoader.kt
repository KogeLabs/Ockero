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

package org.koge.engine.graphics.texture

import org.koge.engine.graphics.SubImage
import org.koge.engine.utils.Utils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.IOException
import java.nio.ByteBuffer

/**
 * The  TextureLoader  is a utility object to load textures for Koge.
 *
 * @author Moncef YABI
 */
object TextureLoader {


    private fun setOpenGlTextureParameter(name: Int, value: Int) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, name, value)
    }


    private fun uploadDataOpenGl(width: Int, height: Int, format: Int, data: ByteBuffer?) {
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, data)
    }

    private fun createTexture(
        w: Int,
        h: Int,
        data: ByteBuffer,
        srcPixelFormat: Int = GL11.GL_RGBA
    ): Texture {
        val texture = Texture().apply {
            id = GL11.glGenTextures()
            width = w
            height = h
            bind()
        }
        //texture.bind()
        setOpenGlTextureParameter(GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER)
        setOpenGlTextureParameter(GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER)
        setOpenGlTextureParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        setOpenGlTextureParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
        uploadDataOpenGl( w, h, srcPixelFormat, data)
        return texture
    }

    /**
     * Create texture from image file
     *
     * @param relativePath
     * @return Texture
     */
    @Throws(IOException::class)
    fun create(relativePath: String): Texture {
        var image: ByteBuffer?= null
        var width = 0
        var height = 0

        val absolutePath = Utils.getAbsolutePath(relativePath)
        MemoryStack.stackPush().use { stack ->
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            val comp = stack.mallocInt(1)

            STBImage.stbi_set_flip_vertically_on_load(true)
            image = STBImage.stbi_load(absolutePath, w, h, comp, 4)
            if (image == null) {
                throw IOException(
                    "Failed to load a texture file!"
                            + System.lineSeparator() + STBImage.stbi_failure_reason()+ System.lineSeparator()+ absolutePath
                )
            }

            width = w.get()
            height = h.get()
        }
        return createTexture(width, height, image!!)
    }

    /**
     * Convert the buffered image to a bytebuffer texture
     *
     * @param bufferedImage The image to convert to a texture
     * @return A buffer containing the data
     */
    @Throws(IOException::class)
    fun createTextureFromBufferedImage(bufferedImage: BufferedImage): Texture {
        val width: Int = bufferedImage.width
        val height: Int = bufferedImage.height

        val flippedBufferedImage = flipBufferedImage(bufferedImage)

        /* Get pixel data of image */
        val pixels = IntArray(width * height)
        flippedBufferedImage.getRGB(0, 0, width, height, pixels, 0, width)

        /* Put pixel data into a ByteBuffer */
        val imageBuffer = MemoryUtil.memAlloc(width * height * 4)
        for (i in 0 until height) {
            for (j in 0 until width) {
                val pixel = pixels[i * width + j]
                imageBuffer.put((pixel shr 16 and 0xFF).toByte())
                imageBuffer.put((pixel shr 8 and 0xFF).toByte())
                imageBuffer.put((pixel and 0xFF).toByte())
                imageBuffer.put((pixel shr 24 and 0xFF).toByte())
            }
        }
        imageBuffer.flip()
        val texture= createTexture(width,height,imageBuffer)
        MemoryUtil.memFree(imageBuffer)
        return texture
    }

    private fun flipBufferedImage(image: BufferedImage): BufferedImage {
        val at = AffineTransform()
        at.concatenate(AffineTransform.getScaleInstance(1.0, -1.0))
        at.concatenate(AffineTransform.getTranslateInstance(0.0, -image.height.toDouble()))
        return transformBufferedImage(image, at)
    }

    private fun transformBufferedImage(image: BufferedImage, at: AffineTransform): BufferedImage {
        val newImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
        val g = newImage.createGraphics()
        g.transform(at)
        g.drawImage(image, 0, 0, null)
        g.dispose()
        return newImage
    }

    /**
     * Create Image frames from sprite sheet texture
     *
     * @param texture
     * @param columns
     * @param rows
     * @return
     */
    fun getImageFramesFromSpriteSheetTexture(texture: Texture, columns:Int, rows:Int):Array<Array<SubImage>>{

        val wi: Int = texture.width / columns
        val hi: Int = texture.height / rows

        var iFrames = arrayOf<Array<SubImage>>()

        for (i in 0 until rows) {
            var rowArray = arrayOf<SubImage>()
            for (j in 0 until columns) {
                rowArray+=SubImage(wi, hi, j*wi, i*hi)
            }
            iFrames+= rowArray
        }

        return iFrames
    }

}