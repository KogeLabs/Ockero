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

import org.joml.Vector2f
import org.joml.Vector3f
import org.koge.engine.kernel.GameDSLMarker
import org.koge.engine.exception.TextureNotSetException
import org.koge.engine.graphics.Model
import org.koge.engine.graphics.ModelBuilder
import org.koge.engine.math.Rectangle


/**
 * This class represents an game Sprite.
 *
 * @property texturePath
 *
 * @author Moncef YABI
 */
@GameDSLMarker
open class Sprite: ISprite {

    var texturePath: String=""
    var xPos:Float=0f
    var yPos:Float=0f
    override lateinit var position:Vector2f
    override var angleOfRotation= 0f
    override var scale = Vector3f(1f, 1f,1f)
    override lateinit var mainModel: Model

    /**
     * Init the Sprite and create a texture from the loaded image.
     *
     */
    override fun init()
    {
        if (texturePath == "") throw TextureNotSetException("Texture path was not set!!")
        position= Vector2f(xPos, yPos)
        mainModel= ModelBuilder.createModelFromSpriteImageFile(texturePath)
    }

    /**
     * Init the Sprite and set the main model.
     *
     */
    fun init(model: Model)
    {
        position= Vector2f(xPos, yPos)
        mainModel=model
    }

    /**
     * Move the sprite in the x direction at speedX
     *
     * @param speedX
     */
    override fun moveX(speedX: Float){
        position.x+=speedX
    }

    /**
     * Move the sprite in the x direction at speedY
     *
     * @param speedY
     */
    override fun moveY(speedY: Float){
        position.y+=speedY
    }

    /**
     * Change the Sprite 2d coordinate
     *
     * @param x
     * @param y
     */
    override fun setPosition(x:Float, y:Float) {
        position.set(x,y)
    }

    /**
     * Destroy this sprite and free all resources.
     *
     */
    override fun destroy(){
        mainModel.destroy()
        //Prevent collision detection for destroyed objects
        mainModel.width = -1f
        mainModel.height = -1f
    }

    /**
     * Checks if this Sprite intersects another Sprite
     *
     * @param oderSprite
     * @return Boolean
     */
    override fun collide(oderSprite: Sprite): Boolean {
        val me= Rectangle(position.x, position.y, mainModel.width , mainModel.height)
        val him= Rectangle(oderSprite.position.x, oderSprite.position.y, oderSprite.mainModel.width, oderSprite.mainModel.height)
        return me.intersects(him)
    }

    /**
     *
     * @return Sprite with
     */
    override fun getWith():Float= mainModel.width

    /**
     *
     * @return Sprite height
     */
    override fun getHeight():Float= mainModel.height

    /**
     *
     * @return Sprite main model
     */
    override fun getModel(): Model = mainModel
}

/**
 * DSL Wrapper for the scene function
 *
 * @param block: lambda function
 */
fun sprite(block: Sprite.()->Unit): Sprite = Sprite().apply(block)