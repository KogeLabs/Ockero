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

package org.ockero.engine.event

import org.ockero.engine.event.key.IKeyboardListener
import org.ockero.engine.event.key.KeyDownEvent
import org.ockero.engine.event.key.KeyPressedEvent
import org.ockero.engine.event.key.KeyReleasedEvent
import org.ockero.engine.event.mouse.*

/**
 *
 * @author Moncef YABI
 */
open class HUIEventAdapter: IKeyboardListener, IMouseListener {

    /**
     * {@inheritDoc}
     */
    override fun keyReleased(e: KeyReleasedEvent) {}

    /**
     * {@inheritDoc}
     */
    override fun keyPressed(e: KeyPressedEvent) {}

    /**
     * {@inheritDoc}
     */
    override fun keyDown(e: KeyDownEvent) {}

    /**
     * {@inheritDoc}
     */
    override fun mouseButtonPressed(e: MousePressedEvent) {}

    /**
     * {@inheritDoc}
     */
    override fun mouseButtonReleased(e: MouseReleasedEvent) {}

    /**
     * {@inheritDoc}
     */
    override fun mouseMoved(e: MouseMovedEvent) {}

    /**
     * {@inheritDoc}
     */
    override fun mouseScrolled(e: MouseScrollEvent) {}

    /**
     * {@inheritDoc}
     */
    override fun mouseCursorEnter(e: MouseCursorEnterEvent) {}
}