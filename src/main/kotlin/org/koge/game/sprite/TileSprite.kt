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

package org.koge.game.sprite

import org.koge.engine.exception.TextureNotSetException
import org.koge.engine.graphics.Model
import org.koge.engine.graphics.texture.Texture
import org.koge.engine.graphics.texture.TextureLoader


/**
 * This class represents an tile Sprite.
 *
 *
 * @author Moncef YABI
 */
class TileSprite(private var texturePath: String, private val imageHeight:Int?, private val tileWidth:Int?, private val tileHeight:Int?, private val columns:Int?)  {

    private var models= arrayOf<Model>()
    lateinit var sheetTexture: Texture
    var size=0f

    /**
     * Init the animation
     *
     */
    fun init() {

        if (texturePath == "") throw TextureNotSetException("Texture path was not set!!")
        sheetTexture = TextureLoader.create(texturePath)
        models = SpriteSheetUtils.getModelsFromSpriteSheet(sheetTexture,imageHeight, tileWidth, tileHeight, columns)
        size= (models[0].texture.width / columns!!).toFloat()

    }

    fun getSpriteAt(index:Int, x:Float, y:Float ):Sprite{
        if(index>=models.size) throw ArrayIndexOutOfBoundsException("Index $index is out of range")
        return Sprite().apply {
            xPos=x
            yPos=y
            init(models[index])
        }
    }

    fun destroy(){
        models.forEach { model->
            model.destroy()
        }
        sheetTexture.destroy()
    }

}