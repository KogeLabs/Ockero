package org.koge.test


import org.jbox2d.callbacks.ContactImpulse
import org.jbox2d.callbacks.ContactListener
import org.jbox2d.collision.Manifold
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.*
import org.jbox2d.dynamics.contacts.Contact

import org.koge.engine.audio.AudioPlayer
import org.koge.engine.audio.Source
import org.koge.engine.event.key.Keys
import org.koge.engine.kernel.game
import org.koge.game.scene.Level
import org.koge.game.scene.scene
import org.koge.game.sprite.animationsprite
import org.koge.game.sprite.sprite
import org.koge.game.tilemap.ObjectLayer
import java.awt.Font



//Screen width and height
const val WIDTH = 320
const val HEIGHT = 320
const val PPM = 100


val world = World(Vec2(0f, 9.8f))

val bd = BodyDef().apply {
    position.set(100f/PPM, 100f/PPM)
    type = BodyType.DYNAMIC
}


//define fixture of the body.
val fd = FixtureDef().apply {
    shape = CircleShape().apply {
        radius=8f/PPM
    }
    density = 0.9f
    friction = 0.0f
    restitution = 0.0f
}


//create the body and add fixture to it

//create the body and add fixture to it
val body: Body = world.createBody(bd).apply {
    createFixture(fd)
}
lateinit var ground:Body
fun addGround(width: Float=160f/PPM, height: Float=160f/PPM){
    val ps = PolygonShape()
    ps.setAsBox(width, height)
    val fd = FixtureDef()
    fd.shape = ps
    val bd = BodyDef()
    bd.position.set(WIDTH/2f/PPM, 730f/PPM)
    ground= world.createBody(bd)
    ground.createFixture(fd)
}
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

    return world.createBody(bd).apply {
        createFixture(fd)
    }
}

const val timeStep = 1.0f / 60f
const val velocityIterations = 6
const val positionIterations = 2


val boss21 = sprite{
    texturePath= "/textures/enemy.png"
    xPos=10f
    yPos=100f
}

val boss22 = sprite{
    texturePath= "/textures/boss.gif"
    xPos=400f
    yPos=690f
}
val boss23 = sprite{
    texturePath= "/textures/boss.gif"
    xPos=530f
    yPos=690f
}
val mario= animationsprite(8,8){
    texturePath="/textures/mario.png"
    xPos=100f
    yPos=100f
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
    addGround()
    val level1= Level("/levels/level1.json")
    world.setContactListener(object: ContactListener{
        override fun endContact(contact: Contact?) {
        }

        override fun beginContact(contact: Contact?) {
            if(inTheAir){
                body.linearVelocity= Vec2(0f,0f)
                body.angularVelocity = 0f
                mario.setActiveAnimation("stop_right_big")
                mario.stopAnimation()
            }
            inTheAir=false

        }

        override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

        }

        override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

        }

    })

    whenInit {
        level1.init()

        var objectLayer = level1.tileMap?.layers?.get(2) as ObjectLayer
        objectLayer.objects.forEach {lo->
            bodies.add(addLevelBodies(lo.width.toFloat()/2/PPM,
                lo.height.toFloat()/2/PPM,
                (lo.x.toFloat()+lo.width.toFloat()/2)/PPM,
                (lo.y.toFloat()+lo.height.toFloat()/2)/ PPM )
            )
        }
        objectLayer = level1.tileMap?.layers?.get(3) as ObjectLayer
        objectLayer.objects.forEach {lo->
            bodies.add(addLevelBodies(lo.width.toFloat()/2/PPM,
                lo.height.toFloat()/2/PPM,
                (lo.x.toFloat()+lo.width.toFloat()/2)/PPM,
                (lo.y.toFloat()+lo.height.toFloat()/2)/ PPM )
            )
        }
        val i=0
    }

    whenKeyReleased {

        if(!inTheAir){
            mario.setActiveAnimation("stop_right_big")
            mario.stopAnimation()
            body.linearVelocity= Vec2(0f,0f)
            body.angularVelocity = 0f

        }
    }
    whenKeyPressed {

        if (key.id==Keys.KEY_UP ){
            if(!inTheAir){
                val force = Vec2(0f, -.07f)
                val point = body.getWorldPoint(body.worldCenter)
                body.applyLinearImpulse(force, point)
                mario.setActiveAnimation("jump_right_big")
                mario.startAnimation()
                inTheAir=true
            }
        }
        if (key.id==Keys.KEY_RIGHT&& body.linearVelocity.x<=3){
            if(!inTheAir){
                mario.setActiveAnimation("run_right_big")
                mario.startAnimation()
            }

            val force = Vec2(.05f, 0f)
            val point = body.getWorldPoint(body.worldCenter)
            body.applyLinearImpulse(force, point)
        }
        if(key.id==Keys.KEY_LEFT&&body.linearVelocity.x>=-3){
            if(!inTheAir){
                mario.setActiveAnimation("run_left_big")
                mario.startAnimation()
            }
            val force = Vec2(-.05f, 0f)
            val point = body.getWorldPoint(body.worldCenter)
            body.applyLinearImpulse(force, point)
        }
    }


    whenUpdate {

        world.step(timeStep, velocityIterations, positionIterations)
        mario.position.x= body.position.x*PPM -16
        mario.position.y= body.position.y*PPM -22
        if(enemy.position.x<=20f) direction =1f

        else if(enemy.position.x>= 700) direction =-1f
        enemy.moveX(direction*5f)

    }

    render {
        level1.sprites.forEach { sprite ->
            g.draw(sprite)
        }
        g.drawText("Level #1", 150f, 10f)

        var width: Float
        var height:Float
        var xPos: Float
        var yPos:Float
        /*bodies.forEach { body->

            width=(body.fixtureList.shape as PolygonShape).vertices[2].x
            height=(body.fixtureList.shape as PolygonShape).vertices[2].y
            g.drawRect(PPM*(body.position.x-width), PPM*(body.position.y-height), width*2*PPM,height*2*PPM)
        }*/
    }

    whenDestroy {
        level1.destroy()
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

fun main() {




    lateinit var source: Source


    game(WIDTH, HEIGHT, "Koge") {


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

        }
        render {
            //g.drawText("Mouse: ${mouse.xPos} ; ${mouse.yPos}", 10f, 52f)

            //g.drawText("FPS: ${timer.getFPS()}", 10f, 10f)
            //g.drawText("UPS: ${timer.getUPS()}", 10f, 32f)
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

