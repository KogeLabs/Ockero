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

package org.koge.engine.graphics

import kotlin.math.min

/**
 *  The  Color class groups the values of colors values in RGBA format.
 *
 * @author Moncef YABI
 */

class Color {

    var r: Float
    var g: Float
    var b: Float
    var a: Float

    constructor(r: Float, g: Float, b: Float, a: Float) {
        this.r = min(r,1.0f)
        this.g = min(g, 1.0f)
        this.b = min(b, 1.0f)
        this.a = min(a, 1.0f)
    }

    constructor(r: Int, g:Int, b:Int, a:Int=1)
            :this(r/255.0f, g/255.0f, b/255.0f, a/255.0f)

    constructor(r: Float, g: Float, b: Float)
            : this(r, g, b, 1.0f)

    constructor(color: Color)
            : this(color.r, color.g, color.b, color.a)
    constructor(nm: String)
            :this(Integer.decode(nm).toInt())

    constructor(value: Int) {
        val r: Int = (value and 0x00FF0000 shr 16)
        val g: Int = (value and 0x0000FF00 shr 8)
        val b: Int = value and 0x000000FF
        var a: Int = value and -0x1000000 shr 24

        if(a<0) a+=256
        if(a==0) a=255
        this.r = r / 255.0f
        this.g = g / 255.0f
        this.b = b / 255.0f
        this.a = a / 255.0f
    }

    companion object{
        val white = Color(255, 255, 255)
        val WHITE = white
        val yellow = Color(255, 255, 0)
        val YELLOW = yellow
        val red = Color(255, 0, 0)
        val RED = red
        val light_red = Color(1f, 0.5f, 0.5f)
        val LIGHT_RED = light_red
        val dark_red = Color(139, 0, 0)
        val DARK_RED = dark_red
        val blue = Color(0, 0, 255)
        val BLUE = blue
        val light_blue = Color(173, 216, 230)
        val LIGHT_BLUE = light_blue
        val dark_blue = Color(0, 0, 139)
        val DARK_BLUE = dark_blue
        val dark_green = Color(0, 100, 0)
        val green = Color(0, 255, 0)
        val light_green = Color(144, 238, 144)
        val DARK_GREEN = dark_green
        val GREEN = green
        val LIGHT_GREEN = light_green
        val black = Color(0, 0, 0)
        val BLACK = black
        val gray = Color(128, 128, 128)
        val GRAY = gray
        val cyan = Color(0, 255, 255)
        val CYAN = cyan
        val dark_Gray = Color(64, 64, 64)
        val DARK_GRAY = dark_Gray
        val light_Gray = Color(192, 192, 192)
        val LIGHT_GRAY = light_Gray
        val light_Gray_glass = Color(192, 192, 192, 150)
        val LIGHT_GRAY_GLASS = light_Gray_glass
        val pink = Color(255, 175, 175)
        val PINK = pink
        val orange = Color(255, 200, 0)
        val ORANGE = orange
        val magenta = Color(255, 0, 255)
        val MAGENTA = magenta

        fun decode(nm: String): Color {
            return Color(Integer.decode(nm).toInt())
        }
    }

}