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
package org.ockero.engine.exception

/**
 * Texture not set exception will be thrown when passing an empty
 * path to the TextureBuilder class
 *
 * @constructor
 *
 * @author Moncef YABI
 */
class TextureNotSetException(message:String): Exception()

/**
 * Koge runtime exception
 * @constructor
 *
 * @param message
 */
class KogeRuntimeException(message:String):RuntimeException()