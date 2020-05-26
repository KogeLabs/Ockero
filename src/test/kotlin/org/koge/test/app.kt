package org.koge.test


import org.jbox2d.callbacks.ContactImpulse
import org.jbox2d.callbacks.ContactListener
import org.jbox2d.collision.Manifold
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.*
import org.jbox2d.dynamics.contacts.Contact

import org.koge.engine.audio.AudioPlayer
import org.koge.engine.audio.Source
import org.koge.engine.event.key.Keys
import org.koge.engine.kernel.game
import org.koge.engine.utils.PPM
import org.koge.game.scene.scene
import org.koge.game.sprite.animatedsprite
import org.koge.game.tilemap.ObjectLayer
import java.awt.Font



//Screen width and height
const val WIDTH = 500
const val HEIGHT = 256

val worldG = World(Vec2(0f, 10f))


val bodies= mutableListOf<Body>()
fun addLevelBodies(width: Float, height:Float, xPos: Float, yPos:Float):Body{

    val fd = FixtureDef().apply {
        shape = PolygonShape().apply {
            setAsBox(width, height)
        }
    }

    val bd = BodyDef().apply {
        position.set(xPos, yPos)
        type=BodyType.STATIC
    }

    return worldG.createBody(bd).apply {
        createFixture(fd)
    }
}

const val timeStep = 1.0f / 60f
const val velocityIterations = 6
const val positionIterations = 2


val mario= animatedsprite(8,8){
    texturePath="/textures/mario.png"
    xPos=150f
    yPos=230f
    delay=5
    setupPhysicsBody(
        BodyDef().apply {
            position.set(xPos/ PPM, yPos/PPM)
            type = BodyType.DYNAMIC
        },
        FixtureDef().apply {
            shape = PolygonShape().apply {
                setAsBox(16/2.5f/PPM, 16/2.2f/PPM)
            }
            density = 0.9f
            friction = 0.0f
            restitution = 0.0f
        }
    )
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
    activeAnimationName="stop_right"
}

val enemy= animatedsprite(8,8){
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

val scene1 = scene("Level 1") {

    +mario
    +enemy
    var direction =-1f
    var inTheAir=false

    level {
        path="/levels/mario_level.json"
    }

    whenInit {
        world?.setContactListener(object: ContactListener{
            override fun endContact(contact: Contact?) {

            }

            override fun beginContact(contact: Contact?) {
                if(inTheAir){
                    mario.physicsBody?.linearVelocity= Vec2(0f,0f)
                    mario.physicsBody?.angularVelocity = 0f
                    mario.setActiveAnimation("stop_right")
                    mario.stopAnimation()
                }
                inTheAir=false

            }

            override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

            }

            override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

            }

        })
        var objectLayer = level?.tileMap?.layers?.get(1) as ObjectLayer
        objectLayer.objects.forEach {lo->
            bodies.add(addLevelBodies(lo.width.toFloat()/2/PPM,
                lo.height.toFloat()/2/PPM,
                (lo.x.toFloat()+lo.width.toFloat()/2)/PPM,
                (lo.y.toFloat()+lo.height.toFloat()/2)/ PPM )
            )
        }
        objectLayer = level?.tileMap?.layers?.get(2) as ObjectLayer
        objectLayer.objects.forEach {lo->
            bodies.add(addLevelBodies(lo.width.toFloat()/2/PPM,
                lo.height.toFloat()/2/PPM,
                (lo.x.toFloat()+lo.width.toFloat()/2)/PPM,
                (lo.y.toFloat()+lo.height.toFloat()/2)/ PPM )
            )
        }
        objectLayer = level?.tileMap?.layers?.get(3) as ObjectLayer
        objectLayer.objects.forEach {lo->
            bodies.add(addLevelBodies(lo.width.toFloat()/2/PPM,
                lo.height.toFloat()/2/PPM,
                (lo.x.toFloat()+lo.width.toFloat()/2)/PPM,
                (lo.y.toFloat()+lo.height.toFloat()/2)/ PPM )
            )
        }

    }

    whenKeyReleased {

        if(!inTheAir){
            mario.setActiveAnimation("stop_right")
            mario.stopAnimation()
            mario.physicsBody?.linearVelocity= Vec2(0f,0f)
            mario.physicsBody?.angularVelocity = 0f

        }
    }
    whenKeyPressed {

        if (key.id==Keys.KEY_UP ){
            if(!inTheAir){

                mario.applyLinearImpulse(0f,-0.07f)
                mario.setActiveAnimation("jump_right")
                mario.startAnimation()
                inTheAir=true
            }
        }
        if (key.id==Keys.KEY_RIGHT && mario.physicsBody?.linearVelocity?.x!! <=1){
            if(!inTheAir){
                mario.setActiveAnimation("run_right")
                mario.startAnimation()
            }


            mario.applyLinearImpulse(0.05f,0f)
        }
        if(key.id==Keys.KEY_LEFT && mario.physicsBody?.linearVelocity?.x!! >=-1){
            if(!inTheAir){
                mario.setActiveAnimation("run_left")
                mario.startAnimation()
            }
            mario.applyLinearImpulse(-0.05f,0f)
        }
    }


    whenUpdate {

        if(enemy.position.x<=20f) direction =1f

        else if(enemy.position.x>= 700) direction =-1f
        enemy.moveX(direction*5f)

        camera.setPosition(mario.position.x,0f)
        camera.update()

    }

    render {

        g.drawText(this.name, 150f, 10f)
        //g.drawText("${mario.position.x}; ${mario.position.y}", 150f, 100f)

        /*var width: Float
        var height:Float
        var xPos: Float
        var yPos:Float
        bodies.forEach { body->

            width=(body.fixtureList.shape as PolygonShape).vertices[2].x
            height=(body.fixtureList.shape as PolygonShape).vertices[2].y
            g.drawRect(PPM*(body.position.x-width), PPM*(body.position.y-height), width*2*PPM,height*2*PPM)
        }*/

    }

    whenDestroy {

    }

}

val scene2 = scene("Level 2") {

    whenUpdate {

    }

    whenKeyDown {

    }
    whenMouseMoved {

    }

    render {
        g.drawText(this.name, 150f, 10f)
    }

}

fun main() {


    game(WIDTH, HEIGHT, "Koge") {

        val source= Source()

        +scene1
        +scene2

        whenInit {
            font.init(Font("Comic Sans MS", Font.PLAIN, 16), true)
            setActiveScene("Level 1")

            AudioPlayer.init()
            AudioPlayer.setListenerData(0f, 0f, 0f)

            source.init(AudioPlayer.loadSound("/audio/bg.wav"))
            source.setLooping(true)
            source.play()
            world = worldG
        }
        render {
            //g.drawText("Mouse: ${mouse.xPos} ; ${mouse.yPos}", 10f, 52f)

            g.drawText("FPS: ${timer.getFPS()}", 10f, 10f)
            g.drawText("UPS: ${timer.getUPS()}", 10f, 32f)
        }

        whenUpdate {
            world?.step(timeStep, velocityIterations, positionIterations)
        }

        whenMouseButtonPressed {
            setActiveScene("Level 1")
        }

        whenMouseScrolled {
            setActiveScene("Level 2")
        }
        whenDestroy {
            source.delete()
            AudioPlayer.cleanUp()
        }

    }.start()


}

