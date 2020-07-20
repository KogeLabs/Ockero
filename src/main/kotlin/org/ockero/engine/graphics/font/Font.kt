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
package org.ockero.engine.graphics.font

import org.ockero.engine.graphics.SubImage
import org.ockero.engine.graphics.texture.Texture
import org.ockero.engine.graphics.texture.TextureLoader
import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.awt.Font as JFont


/**
 * The  Font class represents fonts, which are used to render text in Koge.
 * @author Moncef YABI
 */
class Font{

    var width = 0f
    var height = 0
    private var size = 0
    private lateinit var fontMetrics: FontMetrics
    private lateinit var font: JFont

    /**
     * Texture holding the Font atlas
     */
    lateinit var texture: Texture

    /**
     * Map holding the char value and its representative  Glyph
     */
    val glyphMap = mutableMapOf<Char, SubImage>()

    /**
     * Initialise and create an OpenGL Texture from the  java.awt.Font
     *
     * @param font
     * @param antiAlias
     */
    fun init(font: JFont, antiAlias: Boolean) {
        val image= createFont(font, antiAlias)
        texture= TextureLoader.createTextureFromBufferedImage(image)
    }


    private fun createFont(font: JFont, antiAlias: Boolean):BufferedImage {

        val chMap = mutableMapOf<Char, BufferedImage>()
        var imageWidth = 0
        var imageHeight = 0
        this.font = font
        /* Generate chars images and skip the ASCII control codes */
        for (i in 32..255) {
            if (i == 127) continue
            val c = i.toChar()
            val ch: BufferedImage = getFontImage(c, antiAlias)
            chMap[c] = ch
            imageWidth += ch.width
            imageHeight = imageHeight.coerceAtLeast(ch.height)
        }

        val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
        val g = image.createGraphics()
        this.height = image.height
        this.size= font.size
        var x = 0
        chMap.forEach{ (c, charImage) ->
            val charWidth = charImage.width
            val charHeight = charImage.height
            glyphMap[c]= SubImage(charWidth, charHeight, x, image.height-charHeight)
            g.drawImage(charImage, x, 0, null)
            x += charWidth
        }

        return image
    }

    private fun getFontImage(ch: Char, antiAlias: Boolean): BufferedImage {
        // Create a temporary image to extract the character's size
        val tempFontImage = BufferedImage(
            1, 1,
            BufferedImage.TYPE_INT_ARGB
        )
        val g = tempFontImage.graphics as Graphics2D
        if (antiAlias) {
            g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            )
        }
        g.font = font
        fontMetrics = g.fontMetrics
        var charWidth = fontMetrics.charWidth(ch)
        if (charWidth <= 0) {
            charWidth = 1
        }
        var charHeight = fontMetrics.height
        if (charHeight <= 0) {
            charHeight = size
        }


        // Create another image holding the character we are creating
        val fontImage: BufferedImage
        fontImage = BufferedImage(
            charWidth, charHeight,
            BufferedImage.TYPE_INT_ARGB
        )
        val gt = fontImage.graphics as Graphics2D
        if (antiAlias) {
            gt.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            )
        }
        gt.font = font
        gt.color = Color.WHITE

        gt.drawString(
            ch.toString(), 0 ,fontMetrics.ascent
        )
        gt.drawString(ch.toString(), 0, 0 + fontMetrics.ascent)
        return fontImage
    }

    /**
     * Get the text width in pixels of an CharSequence
     *
     * @param text
     * @return Int
     */
    fun getWidth(text: CharSequence): Int {
        var width = 0
        var lineWidth = 0

        text.forEach {
            when(it){
                '\n' ->{
                    width = width.coerceAtLeast(lineWidth)
                    lineWidth = 0
                }
                '\r'-> {}
                else -> {
                    val g = glyphMap[it]!!
                    lineWidth += g.width
                }
            }
        }

        width = width.coerceAtLeast(lineWidth)
        return width
    }


    /**
     * Get the text height in pixels of an CharSequence
     *
     * @param text
     * @return Int
     */
    fun getTextHeight(text: CharSequence): Int {
        var height = 0
        var lineHeight = 0

        text.forEach {
            when(it){
                '\n' ->{
                    height += lineHeight
                    lineHeight = 0
                }
                '\r'-> {}
                else -> {
                    val g = glyphMap[it]!!
                    lineHeight = g.height
                    lineHeight = lineHeight.coerceAtLeast(height)
                }
            }
        }
        height += lineHeight
        return height
    }

}