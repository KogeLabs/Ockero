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

package org.koge.test

import org.koge.engine.Game
import org.koge.engine.event.mouse.*
import org.koge.engine.graphics.Graphics
import org.koge.game.sprite.Sprite
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.math.sin


/**
 *
 * @author Moncef YABI
 */
class GameTest1(width: Int, height: Int, title: String) : Game(width, height, title) {

    override fun init() {

    }

    override fun run(fps: Int) {

    }

    override fun render(g: Graphics) {

        stop() // stop the game session directly, to allow the testing to complete
    }

    override fun destroy() {

    }

}

class GameTest2(width: Int, height: Int, title: String) : Game(width, height, title) {

    private var sprite = Sprite("/textures/boss.gif")

    override fun init() {
        sprite.init()
    }

    override fun run(fps: Int) {
        sprite.moveX(0.5f)
        sprite.moveY(sin(sprite.position.x)*10)
    }

    override fun render(g: Graphics) {
        g.draw(sprite)
        stop() // stop the game session directly, to allow the testing to complete
    }

    override fun destroy() {
        sprite.destroy()
    }
}

class GameTest3(width: Int, height: Int, title: String) : Game(width, height, title) {

    override fun init() {
        font.init(java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 16), true)
        debugMode=true
    }

    override fun run(fps: Int) {

    }

    override fun render(g: Graphics) {
        stop() // stop the game session directly, to allow the testing to complete
    }

    override fun destroy() {

    }

}

class GameTest4(width: Int, height: Int, title: String) : Game(width, height, title) {

    var xMouse=0f
    var yMouse=0f
    override fun init() {

    }

    override fun run(fps: Int) {

    }

    override fun render(g: Graphics) {

        stop() // stop the game session directly, to allow the testing to complete
    }

    override fun destroy() {

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
        xMouse = e.xPos
        yMouse = e.yPos
    }

    override fun mouseScrolled(e: MouseScrollEvent) {
        println("${e.xOffset} ${e.yOffset}")
    }

}

object KogeTest: Spek({
    describe("Crate Game Window"){
        // Tests are only possible on a machine with a GPU
        /*GameTest1(800, 600, "Koge Game").apply {

            start()
        }*/
    }
    describe("Draw a Sprite"){
        // Tests are only possible on a machine with a GPU
        /*GameTest2(800, 600, "Koge Game").apply {

            start()
        }*/
    }
    describe("Draw text"){
        // Tests are only possible on a machine with a GPU
        /*GameTest3(800, 600, "Koge Game").apply {

            start()
        }*/
    }
    describe("Test event handling"){
        // Tests are only possible on a machine with a GPU
        /*GameTest4(800, 600, "Koge Game").apply {

            start()
        }*/
    }

})