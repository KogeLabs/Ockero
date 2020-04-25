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


import org.koge.engine.input.HUI
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

/**
 * This class is representing a GLFW Window
 * @author Moncef YABI
 */
class Window {
    private var window: Long = 0

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
        check(GLFW.glfwInit()) { "Unable to initialize GLFW" }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints() // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE) // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE) // the window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        setupCallbacks()

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            // Center the window
            GLFW.glfwSetWindowPos(
                window, (vidmode!!.width() - pWidth[0]) / 2, (vidmode.height() - pHeight[0]) / 2
            )
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window)
        // Enable v-sync
        GLFW.glfwSwapInterval(1)

        // Make the window visible
        GLFW.glfwShowWindow(window)
    }

    private fun setupCallbacks() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        GLFW.glfwSetKeyCallback(window) { _: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
                closeWindow()
            if (action == GLFW.GLFW_RELEASE) HUI.fireKeyReleasedEvent(key, GLFW.glfwGetKeyName(key, 0)?.get(0))
            if (action == GLFW.GLFW_PRESS) HUI.fireKeyPressedEvent(key, GLFW.glfwGetKeyName(key, 0)?.get(0))
            HUI.fireKeyDownEvent(key, GLFW.glfwGetKeyName(key, 0)?.get(0))
        }

        // Setup mouse callbacks.
        GLFW.glfwSetMouseButtonCallback(window) { _: Long, button: Int, action: Int, _: Int ->
            if (action == GLFW.GLFW_RELEASE) HUI.fireMouseButtonReleasedEvent(button)
            if (action == GLFW.GLFW_PRESS) HUI.fireMouseButtonPressedEvent(button)
        }

        GLFW.glfwSetCursorPosCallback(window) { _: Long, xpos: Double, ypos: Double ->
            HUI.fireMouseMovedEvent(xpos.toFloat(), ypos.toFloat())
        }

        GLFW.glfwSetScrollCallback(window) { _: Long, xOffset: Double, yOffset: Double ->
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
        GLFW.glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

    /**
     * Check in the Window needs to be closed
     *
     * @return Boolean
     */
    fun windowShouldClose(): Boolean = GLFW.glfwWindowShouldClose(window)

    /**
     * Update the GLFW Wíndow
     *
     */
    fun update() {
        GLFW.glfwSwapBuffers(window) // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        GLFW.glfwPollEvents()
    }

    /**
     * Close the GLFW window
     *
     */
    fun closeWindow(){
        GLFW.glfwSetWindowShouldClose(window, true) // We will detect this in the rendering loop
    }
}