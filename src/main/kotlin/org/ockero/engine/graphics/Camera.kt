package org.ockero.engine.graphics

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

/**
 * This class is representing the camera class in Koge
 *
 * @constructor
 *
 * @param screenWidth
 * @param screenHeight
 */
class Camera(private var screenWidth:Int, private var screenHeight:Int) {
    var position: Vector2f = Vector2f(screenWidth/2f, screenHeight/2f)

    private var orthogonalMatrix: Matrix4f = Matrix4f().ortho2D(0f, screenWidth.toFloat(), screenHeight.toFloat(), 0f)

    var projection: Matrix4f = Matrix4f()

    private var needsUpdate = false

    /**
     * Set the camera position
     *
     * @param dx
     * @param dy
     */
    fun setPosition(dx: Float, dy: Float) {
        position.x = dx
        position.y = dy
        needsUpdate = true
    }

    /**
     * Update the camera's projection matrix
     */
    fun update() {
        val x= if (position.x==0f) 0f else -position.x + screenHeight/2f
        val y= if (position.y==0f) 0f else -position.y + screenHeight/2f
        if (needsUpdate) {
            val viewMatrix = Matrix4f()
                .identity()
                .translate(Vector3f(x,y, 0f))
            projection = Matrix4f().identity().mul(orthogonalMatrix).mul(viewMatrix)

        }
    }
}