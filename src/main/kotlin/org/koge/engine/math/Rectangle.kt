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

package org.koge.engine.math

/**
 * This class represents an bounding box.
 * @author Moncef YABI
 */

class Rectangle(var x: Float, var y: Float, var width: Float, var height: Float) {

    init {
        setBounds(x, y, width, height)
    }

    /**
     * Set the bounding box values
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    /**
     * Get the bounding box values
     *
     * @return Rectangle
     */
    fun getBounds(): Rectangle? {
        return Rectangle(x, y, width, height)
    }

    /**
     * Checks if this Rectangle intersects another Rectangle
     *
     * @param otherRecangle
     * @return Boolean
     */
    fun intersects(otherRecangle: Rectangle): Boolean {
        var tw = width
        var th = height
        var rw = otherRecangle.width
        var rh = otherRecangle.height
        if (checkIfDimensionAreNegative(rw, rh, tw, th)) {
            return false
        }
        val tx = x
        val ty = y
        val rx = otherRecangle.x
        val ry = otherRecangle.y
        rw += rx
        rh += ry
        tw += tx
        th += ty
        return ((rw < rx || rw > tx)
                && (rh < ry || rh > ty)
                && (tw < tx || tw > rx)
                && (th < ty || th > ry))
    }

    private fun checkIfDimensionAreNegative(
        rw: Float,
        rh: Float,
        tw: Float,
        th: Float
    ) = rw <= 0 || rh <= 0 || tw <= 0 || th <= 0
}