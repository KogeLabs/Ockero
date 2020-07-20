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
package org.ockero.game.scene

import org.jbox2d.dynamics.World
import org.ockero.engine.graphics.Camera
import org.ockero.engine.kernel.GameDSLMarker
import org.ockero.engine.kernel.GameDSLWrapper
import org.ockero.engine.graphics.Graphics
import org.ockero.game.sprite.ISprite
import org.ockero.game.sprite.Sprite

/**
 * This class groups the sprites used in one Koge session
 *
 * @author Moncef YABI
 */
@GameDSLMarker
class Scene(var name:String) {

    private val sceneElements = mutableListOf<ISprite>()
    private var onUpdates: (() -> Unit)? = null
    private var onRender: (() -> Unit)? = null
    private var onMouseButtonPressed: (() -> Unit)? = null
    private var onMouseMoved: (() -> Unit)? = null
    private var onKeyPressed: (() -> Unit)? = null
    private var onKeyReleased: (() -> Unit)? = null
    private var onKeyDown: (() -> Unit)? = null
    private var onInit: (() -> Unit)? = null
    private var onDestroy: (() -> Unit)? = null
    var level:Level?=null

    lateinit var mouse: GameDSLWrapper.Mouse
    lateinit var key: GameDSLWrapper.Key
    lateinit var g:Graphics
    lateinit var camera: Camera
    var world: World?=null


    /**
     * The scene init function.
     *
     */
    fun init(g: Graphics, mouse: GameDSLWrapper.Mouse, key: GameDSLWrapper.Key, camera: Camera,world: World? ){
        this.g=g
        this.mouse=mouse
        this.key=key
        this.camera=camera
        this.world=world
        this.level?.init()
        sceneElements.forEach { sprite ->
            sprite.init(world)
        }
        onInit?.invoke()
    }

    /**
     * DSL Wrapper for the update function
     *
     * @param block: lambda function
     */
    fun whenInit(block: () -> Unit){
        onInit=block
    }

    /**
     * The scene render function.
     */
    fun render(){
        level?.sprites?.forEach {sprite ->
            g.draw(sprite)
        }
        onRender?.invoke()

        sceneElements.forEach { sprite ->
            g.draw(sprite)
        }
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
        onDestroy?.invoke()
        level?.destroy()
    }

    /**
     * DSL Wrapper for the update function
     *
     * @param block: lambda function
     */
    fun whenDestroy(block: () -> Unit) {
        onDestroy = block
    }

    /**
     * Add an element to a scene
     *
     * @param sprite
     */
    fun addElement(sprite: ISprite){
        sceneElements.add(sprite)
    }

    /**
     * remove element from the scene
     *
     */
    fun removeElement(sprite: Sprite){
        sceneElements.remove(sprite)
        sprite.destroy()
    }


    /**
     * Update the scene
     *
     */
    fun update(){
        onUpdates?.invoke()
        sceneElements.forEach { sprite ->
            sprite.update()
        }
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
     * This function will be internally triggered
     */
    fun keyReleased() {
        onKeyReleased?.invoke()
    }

    /**
     * DSL Wrapper for the keyPressed function
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

    /**
     * Add a sprite to the game using the extended the unary plus operator
     *
     */
    operator fun ISprite.unaryPlus(){
        this@Scene.sceneElements.add(this)
    }

    /**
     * DSL Wrapper for the level function
     *
     * @param block: lambda function
     */
    fun level(block: LevelBuilder.()->Unit){
        level = LevelBuilder().apply(block).build()
    }

}


/**
 * DSL Wrapper for the scene function
 *
 * @param block: lambda function
 */
fun scene(name:String,block: Scene.()->Unit): Scene = Scene(name).apply(block)
