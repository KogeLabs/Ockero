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
package org.ockero.engine.audio

import org.lwjgl.BufferUtils
import org.lwjgl.openal.AL10
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException


/**
 * This is the Audio class
 *
 * @author Moncef YABI
 */
class AudioData() {

    var format = 0
    var samplerate = 0
    var totalBytes = 0
    var bytesPerFrame = 0
    var data: ByteBuffer? = null

    private var audioStream: AudioInputStream? = null
    private lateinit var dataArray: ByteArray

    constructor(stream: AudioInputStream?):this() {
        audioStream = stream
        val audioFormat = stream!!.format
        format = getOpenAlFormat(audioFormat.channels, audioFormat.sampleSizeInBits)
        samplerate = audioFormat.sampleRate.toInt()
        bytesPerFrame = audioFormat.frameSize
        totalBytes = (stream.frameLength * bytesPerFrame).toInt()
        data = BufferUtils.createByteBuffer(totalBytes)
        dataArray = ByteArray(totalBytes)
        loadData()
    }

    /**
     * Destroy the sound buffers
     *
     */
    fun dispose() {
        try {
            audioStream!!.close()
            data?.clear()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadData(): ByteBuffer? {
        try {
            val bytesRead = audioStream!!.read(dataArray, 0, totalBytes)
            data?.clear()
            data?.put(dataArray, 0, bytesRead)
            data?.flip()
        } catch (e: IOException) {
            e.printStackTrace()
            System.err.println("Couldn't read bytes from audio stream!")
        }
        return data
    }


    companion object{
        /**
         * Create Audio data
         *
         * @param file
         * @return
         */
        fun create(file: String): AudioData? {
            val stream =File(file).inputStream()
            val bufferedInput: InputStream = BufferedInputStream(stream)
            var audioStream: AudioInputStream? = null
            try {
                audioStream = AudioSystem.getAudioInputStream(bufferedInput)
            } catch (e: UnsupportedAudioFileException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return AudioData(audioStream)
        }
    }

    private fun getOpenAlFormat(channels: Int, bitsPerSample: Int): Int {
        return if (channels == 1) {
            if (bitsPerSample == 8) AL10.AL_FORMAT_MONO8 else AL10.AL_FORMAT_MONO16
        } else {
            if (bitsPerSample == 8) AL10.AL_FORMAT_STEREO8 else AL10.AL_FORMAT_STEREO16
        }
    }
}