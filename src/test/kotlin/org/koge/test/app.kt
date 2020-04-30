package org.koge.test


import org.koge.engine.event.key.Keys
import org.koge.engine.game
import org.koge.engine.graphics.texture.TextureLoader
import org.koge.game.animation.Animation
import org.koge.game.sprite.Sprite


fun main(){

    game( 800,600, "Hallo") {

        var animation: Animation?=null

        whenInit {
            debugMode=true
            font.init(java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, 16), true)

            animation= Animation(TextureLoader.create("/textures/power-plant-sheet.png"), 10, 29,1,true)
            animation?.start=true

        }
        render {
            g.drawText("Mouse: ${mouse.xPos} ; ${mouse.yPos}", 10f,52f)
            animation?.update()
            g.draw(animation!!.models[animation!!.counter], 300f,350f)
        }

        scene("Scene1"){
            val boss11= Sprite("/textures/enemy.png", 10f,100f)
            addElement(boss11)
            val boss12= Sprite("/textures/boss.gif", 10f,200f)
            addElement(boss12)
            whenKeyDown {
                when(key.id){
                    Keys.KEY_UP -> boss11.moveY(-5f)
                    Keys.KEY_DOWN -> boss11.moveY(5f)
                    Keys.KEY_LEFT -> boss11.moveX(-5f)
                    Keys.KEY_RIGHT -> boss11.moveX(5f)
                }
            }

            whenMouseMoved {
                boss12.setPosition(mouse.xPos-boss12.getWith()/2, mouse.yPos-boss12.getHeight()/2)
            }
            render{
                g.drawText("Scene #1", 150f,10f)
            }

        }
        scene("Scene2"){

            val boss21= Sprite("/textures/enemy.png",  10f,100f)
            addElement(boss21)
            val boss22= Sprite("/textures/enemy.png", 10f,200f)
            addElement(boss22)

            whenUpdate {
                if(boss21.collide(boss22)){
                    g.drawText("Collision detected!", 250f,10f)
                    removeElement(boss21)
                }
            }

            whenKeyDown {
                when(key.id){
                    Keys.KEY_UP -> boss21.moveY(-5f)
                    Keys.KEY_DOWN -> boss21.moveY(5f)
                    Keys.KEY_LEFT -> boss21.moveX(-5f)
                    Keys.KEY_RIGHT -> boss21.moveX(5f)
                }
            }
            whenMouseMoved {
                boss22.setPosition(mouse.xPos-boss22.getWith()/2, mouse.yPos-boss22.getHeight()/2)
            }

            render{
                g.drawText("Scene #2", 150f,10f)
            }

        }

        whenMouseButtonPressed {
            setActiveScene("Scene1")
        }

        whenMouseScrolled {
            setActiveScene("Scene2")
        }


    }.start()
}