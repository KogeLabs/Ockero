/*
 * Copyright (C) 2014 Moncef YABI
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
package org.ockero.game.tilemap

import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import kotlin.reflect.KClass

/**
 * The is the Layer class.
 *
 * @author Moncef YABI
 */
@TypeFor(field = "type", adapter = LayerTypeAdapter::class)
open class Layer(val type: String)
/**
 * The is the Tile Layer class.
 *
 * @author Moncef YABI
 */
data class TileLayer(
    val `data`: List<Int>,
    val height: Int,
    val id: Int,
    val name: String,
    val opacity: Int,
    val visible: Boolean,
    val width: Int,
    val x: Int,
    val y: Int
): Layer( type="tilelayer")
/**
 * The is the Tile ObjectLayer class.
 *
 * @author Moncef YABI
 */
data class ObjectLayer(
    val draworder: String,
    val id: Int,
    val name: String,
    val objects: List<LogicalObject>,
    val opacity: Int,
    val visible: Boolean,
    val x: Int,
    val y: Int
): Layer( type="objectgroup")

/**
 * The is the Tile ObjectLayer class.
 *
 * @author Moncef YABI
 */
data class LogicalObject(
    val height: Double,
    val id: Int,
    val name: String,
    val rotation: Int,
    val type: String,
    val visible: Boolean,
    val width: Double,
    val x: Double,
    val y: Double
)



class LayerTypeAdapter: TypeAdapter<Layer> {
    override fun classFor(type: Any): KClass<out Layer> = when(type as String) {
        "tilelayer" -> TileLayer::class
        "objectgroup" -> ObjectLayer::class
        else -> throw IllegalArgumentException("Unknown type: $type")
    }
}