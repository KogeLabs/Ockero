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

import org.koge.engine.GameDSLMarker
import org.koge.engine.GameDSLWrapper
import org.koge.engine.graphics.Graphics

/**
 * This class groups the sprites used in one Koge session
 *
 * @author Moncef YABI
 */
@GameDSLMarker
class Scene {

    private val sceneElements = mutableListOf<ISprite>()
    private var onUpdates: (() -> Unit)? = null
    private var onRender: (() -> Unit)? = null
    private var onMouseButtonPressed: (() -> Unit)? = null
    private var onMouseMoved: (() -> Unit)? = null
    private var onKeyPressed: (() -> Unit)? = null
    private var onKeyDown: (() -> Unit)? = null

    lateinit var mouse:GameDSLWrapper.Mouse
    lateinit var key:GameDSLWrapper.Key
    lateinit var g:Graphics
    /**
     * The scene init function.
     *
     */
    fun init(g: Graphics, mouse: GameDSLWrapper.Mouse, key:GameDSLWrapper.Key){
        this.g=g
        this.mouse=mouse
        this.key=key
        sceneElements.forEach { sprite ->
            sprite.init()
        }
    }

    /**
     * The scene init function.
     */
    fun render(){
        sceneElements.forEach { sprite ->
            g.draw(sprite)
        }
        onRender?.invoke()
    }

    /**
     * remove all elements from the scene
     *
     */
    fun destroy(){
        sceneElements.forEach { sprite ->
            sprite.destroy()
        }
        sceneElements.clear()
    }

    /**
     * Add an element to a scene
     *
     * @param sprite
     */
    fun addElement(sprite:ISprite){
        sceneElements.add(sprite)
    }

    /**
     * remove element from the scene
     *
     */
    fun removeElement(sprite:Sprite){
        sceneElements.remove(sprite)
        sprite.destroy()
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

}