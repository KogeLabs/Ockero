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
 *
 * @author Moncef YABI
 */
class Rectangle() {
    var x = 0f
    var y = 0f
    var width = 0f
    var height = 0f


    constructor(x: Float, y: Float, width: Float, height: Float) : this() {
        setBounds(x, y, width, height)
    }

    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    fun getBounds(): Rectangle? {
        return Rectangle(x, y, width, height)
    }

    fun intersects(r: Rectangle): Boolean {
        var tw = width
        var th = height
        var rw = r.width
        var rh = r.height
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false
        }
        val tx = x
        val ty = y
        val rx = r.x
        val ry = r.y
        rw += rx
        rh += ry
        tw += tx
        th += ty
        return ((rw < rx || rw > tx)
                && (rh < ry || rh > ty)
                && (tw < tx || tw > rx)
                && (th < ty || th > ry))
    }
}