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

package org.ockero.engine.time



import org.lwjgl.glfw.GLFW.glfwGetTime

/**
 * This class provide the necessary timer functions for Koge
 *
 *  @author Moncef YABI
 */
class Timer {

    var lastLoopTime:Double=0.0
    private var timeCount = 0f
    private var fps = 0
    private var fpsCount = 0
    private var ups = 0

    private var upsCount = 0

    /**
     * Returns the value of the GLFW timer
     *
     * @return Double
     */
    fun getTime(): Double = glfwGetTime()


    /**
     * Init the timer
     *
     */
    fun init() {
        lastLoopTime=getTime()
    }

    /**
     * Get the elapsed time during one loop
     *
     * @return Float
     */
    fun getElapsedTime(): Float {
        val time = getTime()
        val elapsedTime = (time - lastLoopTime).toFloat()
        lastLoopTime = time
        timeCount += elapsedTime
        return elapsedTime
    }


    /**
     * Increment the FPS value
     *
     */
    fun updateFPS() {
        fpsCount++
    }

    /**
     * Increment the UPS value
     *
     */
    fun updateUPS() {
        upsCount++
    }

    /**
     * Update the timer
     *
     */
    fun update() {
        if (timeCount > 1f) {
            fps = fpsCount
            fpsCount = 0
            ups = upsCount
            upsCount = 0
            timeCount -= 1f
        }
    }

    /**
     * Retrun the FPS calculated value
     *
     * @return Int
     */
    fun getFPS(): Int = if (fps > 0) fps else fpsCount

    /**
     * Retrun the UPS calculated value
     *
     * @return Int
     */
    fun getUPS(): Int = if (ups > 0) ups else upsCount

}