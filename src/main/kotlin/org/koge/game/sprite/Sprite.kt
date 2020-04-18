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

/**
 *
 * @author Moncef YABI
 */

import org.joml.Vector2f
import org.joml.Vector3f
import org.koge.engine.graphics.Model
import org.koge.engine.math.Rectangle



class Sprite(private val texturePath: String) {

    private val me = Rectangle()
    private val him = Rectangle()
    var position= Vector2f(0f, 0f)
    var angleOfRotation= 0f
    var scale = Vector3f(1f, 1f,1f)
    var model: Model = Model()

    fun init()
    {
        model.createFromImageFile(texturePath)
    }

    fun moveX(speedX: Float){
        position.x+=speedX
    }

    fun moveY(speedY: Float){
        position.y+=speedY
    }

    fun setPosition(x:Float, y:Float) {
        position.set(x,y)
    }

    fun destroy(){
        model.destroy()
    }

    fun collide(oder: Sprite): Boolean {
        me.setBounds(position.x, position.y, model.width , model.height)
        him.setBounds(oder.position.x, oder.position.y, oder.model.width, oder.model.height)
        return me.intersects(him)
    }


}