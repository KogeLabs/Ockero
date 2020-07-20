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
package org.ockero.game.scene

import com.beust.klaxon.Klaxon
import org.ockero.engine.utils.Utils
import org.ockero.game.sprite.Sprite
import org.ockero.game.sprite.TileSprite
import org.ockero.game.tilemap.TileLayer
import org.ockero.game.tilemap.TileMap


/**
 * This is the game levels class
 *
 * @author Moncef YABI
 */
class Level(levelFilePath:String) {

    val sprites= mutableListOf<Sprite>()
    val tileMap = Klaxon().parse<TileMap>(Utils.readContentFromFile(levelFilePath).toString())
    private val tileset=tileMap?.tilesets?.get(0)
    private val tileSprite  = TileSprite("/textures/${tileset?.image}", tileset?.tileheight, tileset?.columns)

    /**
     * Init the Level
     *
     */
    fun init(){

        tileSprite.init()
        internalInit()
    }

    @Throws(Exception::class)
    private fun internalInit() {

        tileMap?.layers?.forEach { layer ->
            when(layer.type){
                "tilelayer" -> {
                    var x=0
                    var y=0
                    (layer as TileLayer).data.forEach {index ->
                        if(index!=0)sprites.add(tileSprite.getSpriteAt(index-1,x * tileSprite.size,( y) * tileSprite.size))
                        x++
                        if(x== layer.width){
                            y++
                            x=0
                        }
                    }
                }
            }
        }

    }

    /**
     * Destroy the level and free the resources
     *
     */
    fun destroy()
    {
        tileSprite.destroy()
    }
}