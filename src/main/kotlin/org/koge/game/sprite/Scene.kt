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

import org.koge.engine.graphics.Graphics

/**
 * This class groups the sprites used in one Koge session
 *
 * @author Moncef YABI
 */
class Scene {

    val sceneElements = mutableMapOf<String, ISprite>()
    private var onUpdates: (() -> Unit)? = null
    private var onRender: (() -> Unit)? = null
    private var onMouseButtonPressed: (() -> Unit)? = null
    private var onMouseMoved: (() -> Unit)? = null
    private var onKeyPressed: (() -> Unit)? = null
    private var onKeyDown: (() -> Unit)? = null

    /**
     * The scene init function.
     *
     */
    fun init(){
        sceneElements.forEach { (name, element) ->
            val sprite: ISprite = element
            sprite.init()
        }
    }

    /**
     * The scene init function.
     * @param g, the Graphics renderer object
     */

    fun render(g: Graphics){
        sceneElements.forEach { (name, element) ->
            val sprite: ISprite = element
            g.draw(sprite)
        }
        onRender?.invoke()
    }

    /**
     * remove all elements from the scene
     *
     */
    fun destroy(){
        sceneElements.forEach { (name, element) ->
            val sprite: ISprite = element
            sprite.destroy()
        }
        sceneElements.clear()
    }

    /**
     * remove element from the scene
     *
     */
    fun removeElement(name:String){
        sceneElements.remove(name)
    }


    /**
     * Update the scene
     *
     */
    fun update(){
        onUpdates?.invoke()
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
     * This function will be called form the GameDSlWrapper
     *
     */
    fun mouseButtonPressed() {
        onMouseButtonPressed?.invoke()
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
     * This function will be called form the GameDSlWrapper
     *
     */
    fun mouseMoved() {
        onMouseMoved?.invoke()
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
     * This function will be internally triggered
     */
    fun keyPressed() {
        onKeyPressed?.invoke()
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
     * This function will be internally triggered
     *
     */
    fun keyDown() {
        onKeyDown?.invoke()
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
     * DSL Wrapper for the render function
     *
     * @param block: lambda function
     */
    fun render(block: () -> Unit) {
        onRender = block
    }

    fun sprite(name:String, path:String, xPos:Float=0f, yPos:Float=0f,block:ISprite.()->Unit) {
        sceneElements[name] = Sprite(path, xPos, yPos).apply(block)
    }
}