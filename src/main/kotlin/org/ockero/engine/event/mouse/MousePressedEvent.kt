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

package org.ockero.engine.event.mouse

import org.ockero.engine.event.Event


/**
 * The  MouseCursorEnterEvent class groups the values of a mouse button pressed event.
 *
 * @property button, values: 0 for the left button, 1 for the mid button,
 *                  2 for the right button, 4 for the back button and 5 for the forward button
 *
 * @author Moncef YABI
 */
class MousePressedEvent(val button:Int): Event()