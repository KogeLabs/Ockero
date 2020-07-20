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

import org.lwjgl.openal.AL10

/**
 * This is the Audio source class
 *
 * @author Moncef YABI
 */
class Source {

    var sourceID = 0
    var buffer:Int=-1

    fun init(buffer:Int){
        sourceID = AL10.alGenSources()

        // set property of the source
        AL10.alSourcef(sourceID, AL10.AL_GAIN, 1f) // volume
        AL10.alSourcef(sourceID, AL10.AL_PITCH, 1f) // pitch
        AL10.alSource3f(sourceID, AL10.AL_POSITION, 0f, 0f, 0f) // position
        this.buffer=buffer
    }

    /**
     * Play a sound
     */
    fun play() {
        if (buffer==-1) return
        stop()
        AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer)
        continuePlaying()
    }

    /**
     * Delete the sound
     *
     */
    fun delete() {
        stop()
        AL10.alDeleteSources(sourceID)
    }

    /**
     * Pause the source
     *
     */
    fun pause() {
        AL10.alSourcePause(sourceID)
    }

    /**
     * Continue playing the source
     *
     */
    fun continuePlaying() {
        AL10.alSourcePlay(sourceID)
    }

    /**
     * Stop the source
     *
     */
    fun stop() {
        AL10.alSourceStop(sourceID)
    }

    /**
     * Loop the source
     *
     * @param loop
     */
    fun setLooping(loop: Boolean) {
        AL10.alSourcei(sourceID, AL10.AL_LOOPING, if (loop) AL10.AL_TRUE else AL10.AL_FALSE)
    }

    /**
     * Check if the source is playing
     *
     * @return
     */
    fun isPlaying(): Boolean {
        return AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING
    }

    /**
     * Set source speed
     *
     * @param x
     * @param y
     * @param z
     */
    fun setVelocity(x: Float, y: Float, z: Float) {
        AL10.alSource3f(sourceID, AL10.AL_VELOCITY, x, y, z)
    }

    /**
     * Set source volume
     *
     * @param volume
     */
    fun setVolume(volume: Float) {
        AL10.alSourcef(sourceID, AL10.AL_GAIN, volume)
    }

    /**
     * Set source pitch
     *
     * @param pitch
     */
    fun setPitch(pitch: Float) {
        AL10.alSourcef(sourceID, AL10.AL_PITCH, pitch)
    }

    /**
     * Set source position
     *
     * @param x
     * @param y
     * @param z
     */
    fun setPosition(x: Float, y: Float, z: Float) {
        AL10.alSource3f(sourceID, AL10.AL_POSITION, x, y, z)
    }
}