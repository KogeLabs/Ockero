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

package org.ockero.game.sprite

import org.ockero.engine.graphics.Model
import org.ockero.engine.graphics.ModelBuilder
import org.ockero.engine.graphics.SubImage
import org.ockero.engine.graphics.texture.Texture
import org.ockero.engine.graphics.texture.TextureLoader

/**
 * The  TextureLoader  is a utility object to load textures for Koge.
 *
 * @author Moncef YABI
 */
object SpriteSheetUtils {

    /**
     * Extract sprites from a sprite sheet texture
     *
     * @param texture
     * @param rows
     * @param columns
     * @return Array<Model>
     */
    fun getModelsFromSpriteSheet(texture: Texture, rows: Int, columns: Int): Array<Model> {

        val iSubs = TextureLoader.getSubImagesFromSpriteSheetTexture(texture, columns, rows)

        return geModelsFromSubImages(iSubs, texture)
    }


    private fun geModelsFromSubImages(
        iSubs: Array<Array<SubImage>>,
        texture: Texture
    ): Array<Model> {
        var models = arrayOf<Model>()
        var modelsSheet = arrayOf<Array<Model>>()
        iSubs.forEach { iSubsRow ->
            var modelRow = arrayOf<Model>()
            iSubsRow.forEach { element ->
                modelRow += ModelBuilder.createModelFromSubImage(texture, element)
            }
            modelsSheet += modelRow
        }
        modelsSheet.reverse()
        modelsSheet.forEach { row ->
            row.forEach { model ->
                models += model
            }
        }
        return models
    }
}