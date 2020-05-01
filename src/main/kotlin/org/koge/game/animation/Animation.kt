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


/**
 * This is the Animation class
 *
 * @author Moncef YABI
 */
class Animation(val name:String, val models:Array<Model>, private val delay: Int, var loop: Boolean) {

    var counter = 0
    private var frames = 0
    var start = true
    var stoped = false


    /**
     * Stop animation at index
     *
     * @param index
     */
    fun stopAnimation(index: Int) {
        if (index > models.size - 1 || index < 0) {
            return
        }
        counter = index
        start = false
    }

    /**
     * Start animation at index
     *
     * @param index
     */
    fun startAnimation(index: Int) {
        if (index > models.size - 1 || index < 0) {
            return
        }
        counter = index
        start = true
    }

    /**
     * Update the animation
     *
     */
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
                if(counter>=models.size) counter=models.size-1
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

    /**
     * Select am animation frame
     *
     * @param index
     */
    fun setFrame(index: Int) {
        if (index > models.size - 1 || index < 0) {
            return
        }
        counter = index
    }

    /**
     * Select the next animation frame
     *
     */
    fun nextFrame() {
        if (counter > models.size - 1) {
            return
        }
        counter++
    }

    /**
     * Select the previous animation frame
     *
     */
    fun previousFrame() {
        if (counter == 0) {
            return
        }
        counter--
    }

}