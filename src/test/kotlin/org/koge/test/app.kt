package org.koge.test


import org.koge.engine.event.key.Keys
import org.koge.engine.game
import org.koge.game.animation.Frame
import org.koge.game.scene.Scene
import org.koge.game.sprite.Sprite
import org.koge.game.scene.scene
import org.koge.game.sprite.FSprite
import org.koge.game.sprite.fsprite
import org.koge.game.sprite.sprite

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
    texturePath="/textures/lock_man.png"
    xPos=200f
    yPos=400f
    delay=5

    frame {
        name="build"
        count=15
        loop=true
    }
    frame {
        name="build2"
        count=16
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

    game( 800,600, "Hallo") {

        +scene1
        +scene2

        whenInit {
            debugMode=true
            font.init(java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, 16), true)
            setActiveScene("Scene1")
        }
        render {
            g.drawText("Mouse: ${mouse.xPos} ; ${mouse.yPos}", 10f,52f)

        }

        whenMouseButtonPressed {
            setActiveScene("Scene1")
        }

        whenMouseScrolled {
            setActiveScene("Scene2")
        }
        whenDestroy {
        }

    }.start()
}