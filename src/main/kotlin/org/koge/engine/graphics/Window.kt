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
package org.koge.engine.graphics


import org.koge.engine.exception.KogeRuntimeException
import org.koge.engine.input.HUI
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_VERSION_UNAVAILABLE
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback
import org.lwjgl.glfw.GLFW.glfwSetErrorCallback
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil


/**
 * This class is representing a GLFW Window
 * @author Moncef YABI
 */
object Window {
    private var window: Long = 0
    //val isWindows = System.getProperty("os.name").contains("Windows")
    //val isLinux = System.getProperty("os.name").contains("Linux")
    private val isMacOsX = System.getProperty("os.name").contains("Mac")

    /**
     * Create the GLFW Window
     *
     * @param width
     * @param height
     * @param title
     */
    fun createWindow(width: Int, height: Int, title: String) {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        check(glfwInit()) { "Unable to initialize GLFW" }

        // Configure GLFW
        glfwDefaultWindowHints() // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        if(isMacOsX){
            // Context creation for Mac OS X for GL 3.2+
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE) // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw KogeRuntimeException("Failed to create the GLFW window")

        setupCallbacks()

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            // Center the window
            glfwSetWindowPos(
                window, (vidmode!!.width() - pWidth[0]) / 2, (vidmode.height() - pHeight[0]) / 2
            )
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)
        // Enable v-sync
        glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(window)
    }

    private fun setupCallbacks() {

        glfwSetErrorCallback(object : GLFWErrorCallback() {
            private val delegate = createPrint(System.err)
            override fun invoke(error: Int, description: Long) {
                if (error == GLFW_VERSION_UNAVAILABLE) System.err.println("Ockero requires OpenGL 3.2 or higher.")
                delegate.invoke(error, description)
            }

            override fun free() {
                delegate.free()
            }
        })
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window) { _: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                closeWindow()
            if (action == GLFW_RELEASE) HUI.fireKeyReleasedEvent(key, glfwGetKeyName(key, 0)?.get(0))
            if (action == GLFW_PRESS) HUI.fireKeyPressedEvent(key, glfwGetKeyName(key, 0)?.get(0))
            HUI.fireKeyDownEvent(key, glfwGetKeyName(key, 0)?.get(0))
        }

        // Setup mouse callbacks.
        glfwSetMouseButtonCallback(window) { _: Long, button: Int, action: Int, _: Int ->
            if (action == GLFW_RELEASE) HUI.fireMouseButtonReleasedEvent(button)
            if (action == GLFW_PRESS) HUI.fireMouseButtonPressedEvent(button)
        }

        glfwSetCursorPosCallback(window) { _: Long, xpos: Double, ypos: Double ->
            HUI.fireMouseMovedEvent(xpos.toFloat(), ypos.toFloat())
        }

        glfwSetScrollCallback(window) { _: Long, xOffset: Double, yOffset: Double ->
            HUI.fireMouseScrollEvent(xOffset.toFloat(), yOffset.toFloat())
        }

        glfwSetCursorEnterCallback(window) { _: Long, entered: Boolean ->
            HUI.fireMouseCursorEnterEvent(entered)
        }
    }

    /**
     * Destroy the GLFW Window
     *
     */
    fun destroy() {
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }

    /**
     * Check in the Window needs to be closed
     *
     * @return Boolean
     */
    fun windowShouldClose(): Boolean = glfwWindowShouldClose(window)

    /**
     * Update the GLFW Wíndow
     *
     */
    fun update() {
        glfwSwapBuffers(window) // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents()
    }

    /**
     * Close the GLFW window
     *
     */
    fun closeWindow(){
        glfwSetWindowShouldClose(window, true) // We will detect this in the rendering loop
    }

    /**
     * Check if a key was pressed
     *
     * @param keyCode
     * @return
     */
    fun isKeyPressed(keyCode: Int): Boolean {
        return glfwGetKey(window, keyCode) == GLFW_PRESS
    }

}