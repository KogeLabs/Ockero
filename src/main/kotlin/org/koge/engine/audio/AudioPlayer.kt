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
package org.koge.engine.audio

import org.koge.engine.utils.Utils
import org.lwjgl.openal.AL
import org.lwjgl.openal.AL10
import org.lwjgl.openal.ALC
import org.lwjgl.openal.ALC10.alcCloseDevice
import org.lwjgl.openal.ALC10.alcDestroyContext
import org.lwjgl.openal.ALC10.alcMakeContextCurrent
import java.nio.IntBuffer
import org.lwjgl.system.MemoryUtil.NULL

import org.lwjgl.openal.ALC10.*
import java.nio.ByteBuffer


/**
 * This is the Audio source class
 *
 * @author Moncef YABI
 */
object AudioPlayer {

    private var device: Long = NULL
    private var context: Long = NULL

    private val collections: MutableList<Int> = ArrayList()

    /**
     * Init the Audio player
     *
     */
    fun init() {
        device = alcOpenDevice(null as ByteBuffer?)
        check(device != NULL) { "Failed to open the default device." }
        val deviceCaps = ALC.createCapabilities(device)
        context = alcCreateContext(device, null as IntBuffer?)
        alcMakeContextCurrent(context)
        AL.createCapabilities(deviceCaps)
    }

    /**
     * Load sound file to OpenAL
     *
     * @param filePath
     * @return
     */
    fun loadSound(filePath: String): Int {
        val bufferID = AL10.alGenBuffers()
        collections.add(bufferID)
        val waveFile: AudioData? = AudioData.create(Utils.getAbsolutePath(filePath).toString())
        AL10.alBufferData(bufferID, waveFile!!.format, waveFile.data, waveFile.samplerate)
        waveFile.dispose()
        return bufferID
    }

    /**
     * Remove Audio data from OpenAL
     *
     */
    fun cleanUp() {
        for (buffer in collections) {
            AL10.alDeleteBuffers(buffer)
        }
        alcCloseDevice(device)
        alcDestroyContext(context)
    }

    /**
     * Set OpenAL Listener
     *
     * @param x
     * @param y
     * @param z
     */
    fun setListenerData(x: Float, y: Float, z: Float) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z) // position
        AL10.alListener3f(AL10.AL_VELOCITY, 0f, 0f, 0f) // velocity
    }

}