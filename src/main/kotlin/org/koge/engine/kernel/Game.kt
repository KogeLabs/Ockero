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
package org.koge.engine.kernel

/**
 * This is the mane game class
 *
 * @author Moncef YABI
 */

import org.jbox2d.dynamics.World
import org.koge.engine.event.HUIEventAdapter
import org.koge.engine.graphics.Camera
import org.koge.engine.graphics.Color
import org.koge.engine.graphics.Graphics
import org.koge.engine.graphics.Window
import org.koge.engine.graphics.font.Font
import org.koge.engine.input.HUI
import org.koge.engine.time.Timer
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15.*

/**
 * This is the main game class
 *
 * @constructor
 *
 * @param width
 * @param height
 * @param title
 */

abstract class Game(var width: Int, var height: Int, title: String) : HUIEventAdapter() {

    private val targetFPS = 75
    private val targetUPS = 30

    val g: Graphics
    val timer = Timer()
    val camera: Camera
    var world: World?=null
    var backgroundColor:Color= Color(89f/255,145f/255,255f/255,0.0f)

    open var debugMode= false

    /**
     * The Game init function. This function is called form   internalInit()
     *
     */
    abstract fun init()

    /**
     * The game run function. This function is called form  gameLoop()
     *
     * @param fps
     */
    abstract fun run(fps: Int)

    /**
     * The game render function. This function is called form  gameLoop()
     *
     * @param g
     */
    abstract fun render(g: Graphics)

    /**
     * Destroy the game session and free all resources. This function is called form  destroyGameSession()
     *
     */
    abstract fun destroy()



    val font:Font

    init {
        Window.createWindow(width, height, title)
        font= Font()
        camera= Camera(width,height)
        g = Graphics(width.toFloat(), height.toFloat(), font, camera)
    }

    /**
     * Start the game main loop
     *
     */
    fun start() {
        internalInit()
        gameLoop()
        destroyGameSession()
    }

    /**
     * Destroy the game session and free all resources
     *
     */
    fun stop() {
        Window.closeWindow()
    }

    private fun destroyGameSession() {
        destroy()
        g.destroy()
        Window.destroy()
    }

    private fun internalInit() {
        GL.createCapabilities()


        /* Enable blending */
        glEnable(GL_TEXTURE_2D)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glOrtho(0.0, width.toDouble(), height.toDouble(), 0.0, 1.0, -1.0)
        g.init()

        timer.init()
        HUI.addKeyEventListener(this)
        HUI.addMouseEventListener(this)
        init()
        // Set the clear color
        //glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        glClearColor(backgroundColor.r,backgroundColor.g,backgroundColor.b,0.0f)
    }

    private fun gameLoop() {
        var elapsedTime: Float
        var accumulator = 0f
        val interval = 1f / targetUPS
        Color.white
        while (!Window.windowShouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear the frame buffer
            elapsedTime = timer.getElapsedTime()
            accumulator += elapsedTime

            while (accumulator >= interval) {
                run(timer.getFPS())
                timer.updateUPS()
                accumulator -= interval
            }
            if(debugMode){
                g.drawText("FPS: ${timer.getFPS()}", 10f, 10f)
                g.drawText("UPS: ${timer.getUPS()}", 10f, 32f)
            }

            render(g)

            timer.updateFPS()
            Window.update()
            timer.update()
            sync()
        }
    }

    private fun sync() {
        val loopSlot = 1f / targetFPS
        val endTime = timer.lastLoopTime + loopSlot
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1)
            } catch (ie: InterruptedException) {
            }
        }
    }

}