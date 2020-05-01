package org.koge.test


import org.koge.engine.audio.AudioPlayer
import org.koge.engine.audio.Source
import org.koge.engine.event.key.Keys
import org.koge.engine.kernel.game
import org.koge.engine.utils.Utils
import org.koge.game.scene.scene
import org.koge.game.sprite.fsprite
import org.koge.game.sprite.sprite
import java.awt.Font

val boss11 = sprite{
    texturePath= "/textures/enemy.png"
    xPos=10f
    yPos=100f
}

val boss12 = sprite{
    texturePath= "/textures/boss.gif"
    xPos=10f
    yPos=200f
}

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

val animation= fsprite(8,8){
    texturePath="/textures/mario.png"
    xPos=200f
    yPos=400f
    delay=5

    frame {
        name="build"
        count=18
        loop=true
    }
    frame {
        name="build2"
        count=18
        loop=true
    }
    activeAnimationName="build2"
}

val scene1 = scene("Scene1") {
    +boss11
    +boss12
    whenKeyDown {
        when (key.id) {
            Keys.KEY_UP -> boss11.moveY(-5f)
            Keys.KEY_DOWN -> boss11.moveY(5f)
            Keys.KEY_LEFT -> boss11.moveX(-5f)
            Keys.KEY_RIGHT -> boss11.moveX(5f)
        }
    }

    whenMouseMoved {
        boss12.setPosition(mouse.xPos - boss12.getWith() / 2, mouse.yPos - boss12.getHeight() / 2)
    }
    render {
        g.drawText("Scene #1", 150f, 10f)
    }

}

val scene2 = scene("Scene2") {
    +boss21
    +boss22
    +animation
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


    game(800, 600, "Hallo") {

        +scene1
        +scene2

        whenInit {
            debugMode = true
            font.init(Font("Comic Sans MS", Font.PLAIN, 16), true)
            setActiveScene("Scene1")

            AudioPlayer.init()
            AudioPlayer.setListenerData(0f, 0f, 0f)

            val buffer: Int = AudioPlayer.loadSound(Utils.getAbsolutePath("/audio/bg.wav").toString())
            source = Source()
            source.play(buffer)

        }
        render {
            g.drawText("Mouse: ${mouse.xPos} ; ${mouse.yPos}", 10f, 52f)

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
        }

    }.start()





}