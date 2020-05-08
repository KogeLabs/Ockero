package org.koge.test


import org.koge.engine.audio.AudioPlayer
import org.koge.engine.audio.Source
import org.koge.engine.event.key.Keys
import org.koge.engine.graphics.Window
import org.koge.engine.kernel.game
import org.koge.game.scene.Level
import org.koge.game.scene.scene
import org.koge.game.sprite.animationsprite
import org.koge.game.sprite.sprite
import java.awt.Font


val boss21 = sprite{
    texturePath= "/textures/enemy.png"
    xPos=10f
    yPos=100f
}

val boss22 = sprite{
    texturePath= "/textures/boss.gif"
    xPos=10f
    yPos=200f
}

val mario= animationsprite(8,8){
    texturePath="/textures/mario.png"
    xPos=200f
    yPos=690f
    delay=5

    frame {
        name="stop_right"
        count=1
        loop=false
    }
    frame {
        name="run_right"
        count=4
        loop=true
    }
    frame {
        name="jump_right"
        count=2
        loop=false
    }
    frame {
        name="die_right"
        count=1
        loop=false
    }
    frame {
        name="die_left"
        count=1
        loop=false
    }
    frame {
        name="jump_left"
        count=2
        loop=false
    }
    frame {
        name="run_left"
        count=4
        loop=true
    }
    frame {
        name="stop_left"
        count=1
        loop=false
    }

    frame {
        name="stop_right_big"
        count=1
        loop=false
    }
    frame {
        name="run_right_big"
        count=5
        loop=true
    }
    frame {
        name="die_right_big"
        count=2
        loop=false
    }
    frame {
        name="jump_right_big"
        count=2
        loop=false
    }

    frame {
        name="die_left_big"
        count=2
        loop=false
    }
    frame {
        name="run_left_big"
        count=5
        loop=true
    }
    frame {
        name="stop_left_big"
        count=1
        loop=false
    }
    frame {
        name="jump_left_big"
        count=2
        loop=false
    }
    activeAnimationName="stop_right_big"
}

val enemy= animationsprite(8,8){
    texturePath="/textures/enemy.png"
    xPos=800f
    yPos=690f
    delay=5

    frame {
        name="orange"
        count=2
        loop=true
    }
    frame {
        name="orange_die"
        count=1
        loop=false
    }

    activeAnimationName="orange"

}

val scene1 = scene("Scene1") {
    +mario
    +enemy
    var direction =-1f
    var inTheAir=false
    whenUpdate {
        if(enemy.position.x<=20f) direction =1f

        else if(enemy.position.x>= 700) direction =-1f
        enemy.moveX(direction*5f)
        when {
            Window.isKeyPressed(Keys.KEY_SPACE) -> {
                mario.setActiveAnimation("jump_right_big")
                mario.startAnimation()
                if(!inTheAir){
                    mario.moveY(-60f)
                    inTheAir=true
                }
            }
            Window.isKeyPressed(Keys.KEY_LEFT) -> {
                mario.setActiveAnimation("run_left_big")
                mario.startAnimation()
                mario.moveX(-5f)
            }
            Window.isKeyPressed(Keys.KEY_RIGHT) -> {
                mario.setActiveAnimation("run_right_big")
                mario.startAnimation()
                mario.moveX(5f)
            }
            else -> {
                mario.setActiveAnimation("stop_right_big")
                mario.stopAnimation()
                if(inTheAir){
                    mario.moveY(20f)
                    if(mario.position.y>690f) {
                        inTheAir=false
                        mario.position.y=690f
                    }
                }
            }
        }
    }


    render {
        g.drawText("Scene #1", 150f, 10f)
    }

}

val scene2 = scene("Scene2") {
    +boss21
    +boss22
    whenUpdate {
        if (boss21.collide(boss22)) {
            g.drawText("Collision detected!", 250f, 10f)
            removeElement(boss21)
        }
    }

    whenKeyDown {
        when (key.id) {
            Keys.KEY_UP -> boss21.moveY(-5f)
            Keys.KEY_DOWN -> boss21.moveY(5f)
            Keys.KEY_LEFT -> boss21.moveX(-5f)
            Keys.KEY_RIGHT -> boss21.moveX(5f)
        }
    }
    whenMouseMoved {
        boss22.setPosition(mouse.xPos - boss22.getWith() / 2, mouse.yPos - boss22.getHeight() / 2)
    }

    render {
        g.drawText("Scene #2", 150f, 10f)
    }

}

fun main(){

    lateinit var source:Source


    game(900, 750, "Hallo") {

        val level1= Level("/levels/level1.txt")
        +scene1
        //+scene2
        whenInit {
            font.init(Font("Comic Sans MS", Font.PLAIN, 16), true)
            setActiveScene("Scene1")

            AudioPlayer.init()
            AudioPlayer.setListenerData(0f, 0f, 0f)

            val buffer: Int = AudioPlayer.loadSound("/audio/bg.wav")
            source = Source()
            source.play(buffer)
            level1.init()

        }
        render {
            g.drawText("Mouse: ${mouse.xPos} ; ${mouse.yPos}", 10f, 52f)
            level1.sprites.forEach { sprite ->
                g.draw(sprite)
            }
            g.drawText("FPS: ${timer.getFPS()}", 10f, 10f)
            g.drawText("UPS: ${timer.getUPS()}", 10f, 32f)
        }

        whenMouseButtonPressed {
            setActiveScene("Scene1")
        }

        whenMouseScrolled {
            setActiveScene("Scene2")
        }
        whenDestroy {
            source.delete()
            AudioPlayer.cleanUp()
            level1.destroy()
        }

    }.start()





}