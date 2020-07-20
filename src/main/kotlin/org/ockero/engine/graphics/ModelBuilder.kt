package org.ockero.engine.graphics

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

import org.ockero.engine.graphics.texture.Texture
import org.ockero.engine.graphics.texture.TextureLoader

/**
 * This object groups the model creations methods
 * @author Moncef YABI
 */

object ModelBuilder {

    /**
     * Create a model form a local image file
     *
     * @param path
     * @param c
     * @return Model
     */
    fun createModelFromSpriteImageFile(path: String, c: Color = Color.WHITE):Model {
        val texture = TextureLoader.create(path)
        val model= Model(texture).apply {
            width = texture.width.toFloat()
            height = texture.height.toFloat()
        }
        return createFromTexture(model,c)
    }

    /**
     * Create a set of models form a local sprite sheet image file
     *
     * @param c
     * @return Model
     */
    fun createModelFromSubImage(texture: Texture, subImage: SubImage, c: Color= Color.WHITE): Model{
        val model= Model(texture)
        /* Texture coordinates */
        val s1 = subImage.x / texture.width.toFloat()
        val t1: Float = subImage.y / texture.height.toFloat()
        val s2 = (subImage.x + subImage.width) / texture.width.toFloat()
        val t2: Float = (subImage.y + subImage.height) / texture.height.toFloat()

        model.initVertices(subImage.width.toFloat(),subImage.height.toFloat(), s1, s2, t1,t2,c)
        model.width= subImage.width.toFloat()
        model.height=subImage.height.toFloat()
        model.pushToOpenGL()
        return model
    }

    private fun createFromTexture(model:Model, c: Color = Color.WHITE): Model {
        model.initVertices(model.width, model.height, 0.0f, 1.0f,0.0f, 1.0f,c)
        model.pushToOpenGL()
        return model
    }

}