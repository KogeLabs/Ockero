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
package org.koge.game.tilemap
/**
 * The is the TileMap class.
 *
 * @author Moncef YABI
 */
data class TileMap(
    val compressionlevel: Int,
    val height: Int,
    val infinite: Boolean,
    val layers: List<Layer>,
    val nextlayerid: Int,
    val nextobjectid: Int,
    val orientation: String,
    val renderorder: String,
    val tiledversion: String,
    val tileheight: Int,
    val tilesets: List<TileSet>,
    val tilewidth: Int,
    val type: String,
    val version: Double,
    val width: Int
)