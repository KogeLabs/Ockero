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

package org.ockero.engine.event.key

import org.ockero.engine.event.IComponentListener

/**
 *  Interface for key event listener
 *
 * @author Moncef YABI
 */

interface IKeyboardListener: IComponentListener {

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun keyReleased( e: KeyReleasedEvent)

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun keyPressed(e: KeyPressedEvent)

    /**
     * This function will be internally triggered
     *
     * @param e the object holding the event information
     */
    fun keyDown(e: KeyDownEvent)
}