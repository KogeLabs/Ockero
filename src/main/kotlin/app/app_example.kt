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

package app

import org.koge.engine.Game
import org.koge.engine.event.key.Key
import org.koge.engine.event.key.KeyDownEvent
import org.koge.engine.event.mouse.*
import org.koge.engine.graphics.Graphics
import org.koge.game.sprite.Sprite

/**
 *
 * @author Moncef YABI
 */
class MyGame(width: Int, height: Int, title: String) : Game(width, height, title) {

    private var sprite = Sprite("/textures/boss.gif")
    var xMouse=0f
    var yMouse=0f
    override fun init() {
        font.init(java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 16), true)
        sprite.init()
        debugMode=true
    }

    override fun run(fps: Int) {
        //sprite.moveX(0.5f)
        //sprite.moveY(sin(sprite.position.x)*10)
    }

    override fun render(g: Graphics) {
        g.draw(sprite)
        g.drawText("Mouse: $xMouse , $yMouse",10f,54f)
    }

    override fun destroy() {
        sprite.destroy()
    }

    override fun keyDown(e: KeyDownEvent) {
        when(e.key){
            Key.KEY_UP -> sprite.moveY(-5f)
            Key.KEY_DOWN -> sprite.moveY(5f)
            Key.KEY_LEFT -> sprite.moveX(-5f)
            Key.KEY_RIGHT -> sprite.moveX(5f)
        }
    }

    override fun mouseButtonPressed(e: MousePressedEvent) {
        println(e.button)
    }

    override fun mouseButtonReleased(e: MouseReleasedEvent) {
        println(e.button)
    }

    override fun mouseCursorEnter(e: MouseCursorEnterEvent) {
        println(e.entered)
    }

    override fun mouseMoved(e: MouseMovedEvent) {
        xMouse = e.xpos.toFloat()
        yMouse = e.ypos.toFloat()
    }

    override fun mouseScrolled(e: MouseScrollEvent) {
        println("${e.xoffset} ${e.yoffset}")
    }

}

fun main() {
    MyGame(800, 600, "Koge Game").apply {
        start()
    }
}