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

package org.koge.engine.utils

import java.io.File
import java.io.IOException


/**
 *
 * @author Moncef YABI
 */
object Utils {

    @Throws(IOException::class)
    fun readContentFromFile(path: String): String? = this::class.java.getResource(path).readText()

    @Throws(IOException::class)
    fun getAbsolutePath(reSourcePath: String): String? = File(this::class.java.getResource(reSourcePath).toURI()).absolutePath

}