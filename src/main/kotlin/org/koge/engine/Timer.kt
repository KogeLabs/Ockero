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

package org.koge.engine

/**
 *
 * @author Moncef YABI
 */

import org.lwjgl.glfw.GLFW.glfwGetTime


class Timer {

    var lastLoopTime:Double=0.0
    private var timeCount = 0f
    private var fps = 0
    private var fpsCount = 0
    private var ups = 0

    private var upsCount = 0

    fun getTime(): Double = glfwGetTime()

    fun init() {
        lastLoopTime=getTime()
    }
    fun getElapsedTime(): Float {
        val time = getTime()
        val elapsedTime = (time - lastLoopTime).toFloat()
        lastLoopTime = time
        timeCount += elapsedTime
        return elapsedTime
    }


    fun updateFPS() {
        fpsCount++
    }

    fun updateUPS() {
        upsCount++
    }

    fun update() {
        if (timeCount > 1f) {
            fps = fpsCount
            fpsCount = 0
            ups = upsCount
            upsCount = 0
            timeCount -= 1f
        }
    }

    fun getFPS(): Int = if (fps > 0) fps else fpsCount

    fun getUPS(): Int = if (ups > 0) ups else upsCount

}