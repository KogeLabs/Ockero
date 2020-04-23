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

package org.koge.engine.event.mouse

import org.koge.engine.event.IComponentListener

/**
 * Interface for the mouse event listener
 *
 * @author Moncef YABI
 */
interface IMouseListener : IComponentListener {

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun mouseButtonPressed(e: MousePressedEvent)

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun mouseButtonReleased(e: MouseReleasedEvent)

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun mouseMoved(e: MouseMovedEvent)

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun mouseScrolled(e: MouseScrollEvent)

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun mouseCursorEnter(e: MouseCursorEnterEvent)
}