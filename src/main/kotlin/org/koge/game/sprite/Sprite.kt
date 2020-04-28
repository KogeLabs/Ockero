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
import org.koge.engine.GameDSLMarker
import org.koge.engine.graphics.Model
import org.koge.engine.math.Rectangle


/**
 * This class represents an game Sprite.
 *
 * @property texturePath
 *
 * @author Moncef YABI
 */
@GameDSLMarker
class Sprite(private val texturePath: String, var name:String,xPos:Float=0f, yPos:Float=0f): ISprite {

    override var position= Vector2f(xPos, yPos)
    override var angleOfRotation= 0f
    override var scale = Vector3f(1f, 1f,1f)
    override var model: Model = Model()

    /**
     * Init the Sprite and create a texture from the loaded image.
     *
     */
    override fun init()
    {
        model.createFromImageFile(texturePath)
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
        model.destroy()
        //Prevent collision detection for destroyed objects
        model.width = -1f
        model.height = -1f
    }

    /**
     * Checks if this Sprite intersects another Sprite
     *
     * @param oderSprite
     * @return Boolean
     */
    override fun collide(oderSprite: Sprite): Boolean {
        val me= Rectangle(position.x, position.y, model.width , model.height)
        val him= Rectangle(oderSprite.position.x, oderSprite.position.y, oderSprite.model.width, oderSprite.model.height)
        return me.intersects(him)
    }

    /**
     *
     * @return Sprite with
     */
    override fun getWith():Float= model.width

    /**
     *
     * @return Sprite height
     */
    override fun getHeight():Float= model.height

}