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

import org.koge.engine.utils.Utils
import org.koge.game.sprite.Sprite
import org.koge.game.sprite.TileSprite
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException


/**
 * This is the game levels class
 *
 * @author Moncef YABI
 */
class Level(private val levelFilePath:String) {

    private var levelInfo  = arrayOf<CharArray>()
    val sprites= mutableListOf<Sprite>()
    private val tileSprite  = TileSprite("/textures/blocks.png",8,8)

    fun init(){
        val lines = mutableListOf<String>()

        var line: String?

        try {
            val fileReader = FileReader(Utils.getAbsolutePath(levelFilePath).toString())
            val bufferedReader = BufferedReader(fileReader)

            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let { lines.add(it) }
            }

             bufferedReader.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        for (i in 0 until lines.size ) {
            val charArray: CharArray = lines[i].toCharArray()
            levelInfo+= CharArray(charArray.size)
            levelInfo+= charArray
        }

        tileSprite.init()
        internalInit()
    }

    @Throws(Exception::class)
    fun internalInit() {
        for (y in levelInfo.indices) {
            for (x in levelInfo[y].indices) {
                when (levelInfo[y][x]) {
                    '#' -> {
                        print('#')
                        //val tile = TileSprite(Vector2f(x * Tile.SIZE, (levelInfo.size - 1 - y) * Tile.SIZE), 24)
                        //sprites.add(tileSprite.getSpriteAt(1,x * tileSprite.size,(levelInfo.size - 1 - y) * tileSprite.size))
                    }
                    '.' ->  {
                        print('.')
                    }
                    'W' -> {
                        print('W')
                        sprites.add(tileSprite.getSpriteAt(2,x * tileSprite.size,( y) * tileSprite.size))
                    }
                    'G' -> {
                        print('G')
                        sprites.add(tileSprite.getSpriteAt(5,x * tileSprite.size,( y) * tileSprite.size))
                    }
                    'B' -> {
                        print('B')

                    }
                    'A' -> {
                        print('A')

                    }
                    '-' -> {
                        print('-')
                    }
                    'P' -> {
                        print('P')

                    }
                    '|' -> {
                        print('|')

                    }
                    'C' -> {
                    }
                    else -> {
                    }
                }
            }
            println()
        }
    }

    fun destroy()
    {
        tileSprite.destroy()
    }
}