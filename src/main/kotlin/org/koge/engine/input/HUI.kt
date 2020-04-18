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
package org.koge.engine.input

import org.koge.engine.event.IComponentListener
import org.koge.engine.event.key.IKeyboardListener
import org.koge.engine.event.key.KeyDownEvent
import org.koge.engine.event.key.KeyPressedEvent
import org.koge.engine.event.key.KeyReleasedEvent
import org.koge.engine.event.mouse.*

/**
 *
 * @author Moncef YABI
 */
object HUI {

    private val mouseListener: ArrayList<IComponentListener?> = ArrayList()
    private val keyListener: ArrayList<IComponentListener?> = ArrayList()

    fun addMouseEventListener(listener: IComponentListener?) {
        mouseListener.add(listener)
    }

    fun removeMouseEventListener(listener: IComponentListener?) {
        mouseListener.remove(listener)
    }

    fun addKeyEventListener(listener: IComponentListener?) {
        keyListener.add(listener)
    }

    fun removeKeyEventListener(listener: IComponentListener?) {
        keyListener.remove(listener)
    }

    fun fireKeyPressedEvent(key: Int, c: Char?) {
        val e = KeyPressedEvent(key, c)
        keyListener.forEach {
            val l: IKeyboardListener = it as IKeyboardListener
            l.keyPressed(e)
        }
    }

    fun fireKeyReleasedEvent(key: Int, c: Char?) {
        val e = KeyReleasedEvent(key, c)
        keyListener.forEach{
            val l: IKeyboardListener = it as IKeyboardListener
            l.keyReleased(e)
        }
    }

    fun fireKeyDownEvent(key: Int, c: Char?) {
        val e = KeyDownEvent(key, c)
        keyListener.forEach{
            val l: IKeyboardListener = it as IKeyboardListener
            l.keyDown(e)
        }
    }

    fun fireMouseButtonPressedEvent(button: Int) {
        val e = MousePressedEvent(button)
        mouseListener.forEach {
            val l: IMouseListener = it as IMouseListener
            l.mouseButtonPressed(e)
        }
    }

    fun fireMouseButtonReleasedEvent(button: Int) {
        val e = MouseReleasedEvent(button)
        mouseListener.forEach {
            val l: IMouseListener = it as IMouseListener
            l.mouseButtonReleased(e)
        }
    }

    fun fireMouseMovedEvent(xpos: Double, ypos: Double) {
        val e = MouseMovedEvent(xpos, ypos)
        mouseListener.forEach {
            val l: IMouseListener = it as IMouseListener
            l.mouseMoved(e)
        }
    }

    fun fireMouseScrollEvent(xoffset: Double, yoffset: Double) {
        val e = MouseScrollEvent(xoffset, yoffset)
        mouseListener.forEach {
            val l: IMouseListener = it as IMouseListener
            l.mouseScrolled(e)
        }
    }

    fun fireMouseCursorEnterEvent(entered: Boolean) {
        val e = MouseCursorEnterEvent(entered)
        mouseListener.forEach {
            val l: IMouseListener = it as IMouseListener
            l.mouseCursorEnter(e)
        }
    }


}