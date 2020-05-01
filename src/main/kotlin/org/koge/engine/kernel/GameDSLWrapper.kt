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

import org.koge.engine.event.key.KeyDownEvent
import org.koge.engine.event.key.KeyPressedEvent
import org.koge.engine.event.key.KeyReleasedEvent
import org.koge.engine.event.mouse.*
import org.koge.engine.graphics.Graphics
import org.koge.game.scene.Scene

/**
 * This class is a dsl wrapper for the the abstract class Game
 *
 * @constructor
 *
 * @param width
 * @param height
 * @param title
 *
 * @author Moncef YABI
 */
@GameDSLMarker
class GameDSLWrapper(width: Int, height: Int, title: String) : Game(width, height, title){

    private var onInit: (() -> Unit)? = null
    private var onUpdates: (() -> Unit)? = null
    private var onDestroy: (() -> Unit)? = null
    private var onRender: (() -> Unit)? = null

    private var onKeyReleased: (() -> Unit)? = null
    private var onKeyPressed: (() -> Unit)? = null
    private var onKeyDown: (() -> Unit)? = null

    private var onMouseButtonPressed: (() -> Unit)? = null
    private var onMouseButtonReleased: (() -> Unit)? = null
    private var onMouseMoved: (() -> Unit)? = null
    private var onMouseScrolled: (() -> Unit)? = null
    private var onMouseCursorEnter: (() -> Unit)? = null

    val mouse= Mouse()
    val key = Key()

    private val scenes = mutableMapOf<String, Scene>()
    private var activeScene: Scene?=null


    /**
     * Set the current active scene
     *
     * @param name
     */
    fun setActiveScene(name: String){
        activeScene= scenes[name]
    }
    /**
     * remove scene from the game session
     *
     */
    fun removeElement(name:String){
        scenes.remove(name)
    }
    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun keyReleased(e: KeyReleasedEvent) {
        key.id= e.key
        key.c = e.c?:' '
        onKeyReleased?.invoke()
    }

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun keyPressed(e: KeyPressedEvent) {
        key.id= e.key
        key.c = e.c?:' '
        onKeyPressed?.invoke()
        activeScene?.keyPressed()
    }

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun keyDown(e: KeyDownEvent) {
        key.id= e.key
        key.c = e.c?:' '
        onKeyDown?.invoke()
        activeScene?.keyDown()
    }

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun mouseButtonPressed(e: MousePressedEvent) {
        mouse.button= e.button
        onMouseButtonPressed?.invoke()
        activeScene?.mouseButtonPressed()
    }

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun mouseButtonReleased(e: MouseReleasedEvent) {
        mouse.button= e.button
        onMouseButtonReleased?.invoke()
    }

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun mouseMoved(e: MouseMovedEvent) {
        mouse.xPos=e.xPos
        mouse.yPos=e.yPos
        onMouseMoved?.invoke()

        activeScene?.mouseMoved()
    }

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun mouseScrolled(e: MouseScrollEvent) {
        mouse.xOffset=e.xOffset
        mouse.yOffset= e.yOffset
        onMouseScrolled?.invoke()
    }

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    override fun mouseCursorEnter(e: MouseCursorEnterEvent) {
        mouse.entered=e.entered
        onMouseCursorEnter?.invoke()
    }

    /**
     * DSL Wrapper for the render function
     *
     * @param block: lambda function
     */
    fun render(block: () -> Unit) {
        onRender = block
    }

    /**
     * DSL Wrapper for the keyReleased function
     *
     * @param block: lambda function
     */
    fun whenKeyReleased(block: () -> Unit) {
        onKeyReleased = block
    }

    /**
     * DSL Wrapper for the keyPressed function
     *
     * @param block: lambda function
     */
    fun whenKeyPressed(block: () -> Unit) {
        onKeyPressed = block
    }

    /**
     * DSL Wrapper for the keyDown function
     *
     * @param block: lambda function
     */
    fun whenKeyDown(block: () -> Unit) {
        onKeyDown = block
    }

    /**
     * DSL Wrapper for the mouseButtonPressed function
     *
     * @param block: lambda function
     */
    fun whenMouseButtonPressed(block: () -> Unit) {
        onMouseButtonPressed = block
    }

    /**
     * DSL Wrapper for the mouseButtonReleased function
     *
     * @param block: lambda function
     */
    fun whenMouseButtonReleased(block: () -> Unit) {
        onMouseButtonReleased = block
    }

    /**
     * DSL Wrapper for the mouseButtonMoved function
     *
     * @param block: lambda function
     */
    fun whenMouseMoved(block: () -> Unit) {
        onMouseMoved = block
    }

    /**
     * DSL Wrapper for the mouseButtonScrolled function
     *
     * @param block: lambda function
     */
    fun whenMouseScrolled(block: () -> Unit) {
        onMouseScrolled = block
    }

    /**
     * DSL Wrapper for the mouseButtonEnter function
     *
     * @param block: lambda function
     */
    fun whenMouseCursorEnter(block: () -> Unit) {
        onMouseCursorEnter = block
    }

    /**
     * The Game init function. This function is called form   internalInit()
     *
     */
    override fun init() {
        onInit?.invoke()
        scenes.forEach { (_,element) ->
            element.init(g, mouse, key)
        }
    }

    /**
     * The game run function. This function is called form  gameLoop()
     *
     * @param fps
     */
    override fun run(fps: Int) {
        onUpdates?.invoke()
        activeScene?.update()
    }

    /**
     * The game render function. This function is called form  gameLoop()
     *
     * @param g
     */
    override fun render(g: Graphics) {
        onRender?.invoke()
        activeScene?.render()
    }

    /**
     * Destroy the game session and free all resources. This function is called form  destroyGameSession()
     *
     */
    override fun destroy() {
        onDestroy?.invoke()
        scenes.forEach { (_, element) ->
            element.destroy()
        }
        scenes.clear()
    }

    /**
     * DSL Wrapper for the init function
     *
     * @param block: lambda function
     */
    fun whenInit(block: () -> Unit) {
        onInit = block
    }

    /**
     * DSL Wrapper for the update function
     *
     * @param block: lambda function
     */
    fun whenUpdate(block: () -> Unit) {
        onUpdates = block
    }

    /**
     * DSL Wrapper for the destroy function
     *
     * @param block: lambda function
     */
    fun whenDestroy(block: () -> Unit) {
        onDestroy = block
    }

    /**
     * Add a scene to the game using the extended the unary plus operator
     *
     */
    operator fun Scene.unaryPlus(){
        this@GameDSLWrapper.scenes[this.name]= this
    }

    /**
     * Data class to group Mouse parameters
     *
     * @property xPos, cursor x position
     * @property yPos, cursor y position
     * @property button, mouse button id
     * @property xOffset, scroll offset in the x direction
     * @property yOffset, scroll offset in the y direction
     * @property entered, cursor is on the screen
     */
    data class Mouse(var xPos: Float=0f, var yPos:Float=0f, var button:Int=-1, var xOffset: Float=0f, var yOffset: Float=0f,var entered: Boolean=false)

    /**
     * Data class to group Key parameters
     *
     * @property id, key id
     * @property c, key char value
     */
    data class Key(var id:Int=0, var c:Char=' ')
}

/**
 * Main entry point for Koge dsl
 *
 * @param width
 * @param height
 * @param title
 * @param block
 * @return
 */
fun game(width: Int, height: Int,  title: String,block: GameDSLWrapper.()->Unit): GameDSLWrapper = GameDSLWrapper(
    width,
    height,
    title
).apply(block)


/**
 * Marker class for DSL
 *
 */
@DslMarker
annotation class GameDSLMarker

