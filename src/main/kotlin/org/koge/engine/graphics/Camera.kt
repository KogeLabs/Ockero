package org.koge.engine.graphics

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f





/**
 * This class is representing the camera class in Koge
 *
 * @constructor
 *
 * @param width
 * @param height
 */
class Camera(var screenWidth:Int, var screenHeight:Int) {
    var position: Vector2f = Vector2f(screenWidth/2f, screenHeight/2f)

    private var viewMatrix: Matrix4f = Matrix4f()

    private var orthoMatrix: Matrix4f = Matrix4f().ortho(0f, screenWidth.toFloat(), screenHeight.toFloat(), 0f, 1f,-1f)

    var projection: Matrix4f = Matrix4f()

    private var needsUpdate = false



    fun setPosition(dx: Float, dy: Float) {
        position.x = dx
        position.y = dy
        needsUpdate = true
    }

    fun update() {
        if (needsUpdate) {
            var viewMatrix = Matrix4f()
                .identity()
                //.translate(Vector3f(-position.x, -position.y, 0f))
                .translate(Vector3f(-position.x+screenWidth/2, if (position.y==0f) 0f else -position.y + screenHeight/2f, 0f))
            projection = Matrix4f().identity().mul(orthoMatrix).mul(viewMatrix)
            //cameraMatrix = orthoMatrix
        }
    }
}