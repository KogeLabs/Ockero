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
package org.koge.game.animation

import org.koge.engine.graphics.Model
import org.koge.engine.graphics.ModelBuilder
import org.koge.engine.graphics.texture.Texture
import org.koge.engine.graphics.texture.TextureLoader


/**
 * This class is the Animation class
 *
 * @author Moncef YABI
 */
class Animation(var texture: Texture, delay: Int, columns: Int, rows: Int, loop: Boolean) {

    var models= mutableListOf<Model>()
    var counter = 0
    private var delay = 0
    private var frames = 0
    private var loop = false
    var start = true
    var stoped = false
    var stopAt:Int=-1


    init {
        setUp(delay, loop, columns, rows)
    }

    private fun setUp(delay: Int, loop: Boolean, columns:Int, rows:Int) {
        this.delay = delay
        this.loop = loop
        val iSubs = TextureLoader.getImageFramesFromSpriteSheetTexture(texture,columns,rows)
        var modelsSheet= arrayOf<Array<Model>>()
        iSubs.forEach{ iSubsRow ->
            var modelRow=arrayOf<Model>()
            iSubsRow.forEach { element->
                modelRow+= ModelBuilder.createModelFromSubImage(texture, element)
            }
            modelsSheet+=modelRow
        }
        modelsSheet.reverse()
        modelsSheet.forEach { row ->
            row.forEach {model ->
                models.add(model)
            }
        }
    }
    

    fun stopAnimation(index: Int) {
        if (index > models.size - 1 || index < 0) {
            return
        }
        counter = index
        start = false
    }
    

    fun startAnimation(index: Int) {
        if (index > models.size - 1 || index < 0) {
            return
        }
        counter = index
        start = true
    }

    fun update() {
        performCount()
        frames++
        if (frames >= delay && start) {
            if (loop) {
                counter = (counter + 1) % models.size
                if (counter == 0) {
                    stoped = true
                }
            } else {
                counter++
            }
            frames = 0
        }
    }

    private fun performCount() {
        if (counter > models.size - 1) {
            counter = models.size - 1
            stoped = true
        } else if (counter < 0) {
            counter = 0
        }
    }

    fun setFrame(index: Int) {
        if (index > models.size - 1 || index < 0) {
            return
        }
        counter = index
    }

    fun nextFrame() {
        if (counter > models.size - 1) {
            return
        }
        counter++
    }

    fun previousFrame() {
        if (counter == 0) {
            return
        }
        counter--
    }

}