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
package org.koge.game.scene

import com.beust.klaxon.Klaxon
import org.koge.engine.utils.Utils
import org.koge.game.sprite.Sprite
import org.koge.game.sprite.TileSprite
import org.koge.game.tilemap.TileLayer
import org.koge.game.tilemap.TileMap


/**
 * This is the game levels class
 *
 * @author Moncef YABI
 */
class Level(private val levelFilePath:String) {

    private var levelInfo  = arrayOf<CharArray>()
    val sprites= mutableListOf<Sprite>()
    val tileMap = Klaxon().parse<TileMap>(Utils.readContentFromFile(levelFilePath).toString())
    val tileset=tileMap?.tilesets?.get(0)
    private val tileSprite  = TileSprite("/textures/${tileset?.image}",tileset?.imageheight, tileset?.tilewidth, tileset?.tileheight, tileset?.columns)

    fun init(){


        tileSprite.init()
        internalInit()
    }

    @Throws(Exception::class)
    fun internalInit() {
        val layer1 = tileMap?.layers?.get(0) as TileLayer
        var x=0
        var y=0
        layer1?.data?.forEach { index ->

            if(index!=0)sprites.add(tileSprite.getSpriteAt(index-1,x * tileSprite.size,( y) * tileSprite.size))
            x++
            if(x==layer1?.width){
                y++
                x=0
            }


        }

        val layer2 = tileMap?.layers?.get(1) as TileLayer
        x=0
        y=0
        layer2?.data?.forEach { index ->

            if(index!=0)sprites.add(tileSprite.getSpriteAt(index-1,x * tileSprite.size,( y) * tileSprite.size))
            x++
            if(x==layer1?.width){
                y++
                x=0
            }


        }


    }

    fun destroy()
    {
        tileSprite.destroy()
    }
}