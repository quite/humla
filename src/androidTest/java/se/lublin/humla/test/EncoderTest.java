/*
 * Copyright (C) 2014 Andrew Comminos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.lublin.humla.test;

import android.test.AndroidTestCase;

import com.googlecode.javacpp.Loader;

import se.lublin.humla.audio.encoder.CELT7Encoder;
import se.lublin.humla.audio.encoder.IEncoder;
import se.lublin.humla.audio.encoder.OpusEncoder;
import se.lublin.humla.audio.javacpp.Opus;
import se.lublin.humla.exception.NativeAudioException;
import se.lublin.humla.net.PacketBuffer;

/**
 * This class tests the Opus and CELT encoders with blank PCM data.
 * The bitrate is set to 40000bps. TODO: add test for varying bitrates.
 * If any of these methods throw a NativeAudioException, then the test will fail.
 * Created by andrew on 13/10/13.
 */
public class EncoderTest extends AndroidTestCase {
    private static final int MAX_BUFFER_SIZE = 960;
    private static final int SAMPLE_RATE = 48000;
    private static final int BITRATE = 40000;
    private static final int FRAME_SIZE = 480;
    private static final int FRAMES_PER_PACKET = 4;

    static {
        Loader.load(Opus.class);
    }

    public void testOpusEncode() throws NativeAudioException {
        IEncoder encoder = new OpusEncoder(SAMPLE_RATE, 1, FRAME_SIZE, FRAMES_PER_PACKET, BITRATE, MAX_BUFFER_SIZE);
        testEncoder(encoder);
        encoder.destroy();
    }

    public void testCELT7Encode() throws NativeAudioException {
        CELT7Encoder encoder = new CELT7Encoder(SAMPLE_RATE, FRAME_SIZE, 1, FRAMES_PER_PACKET,
                BITRATE, MAX_BUFFER_SIZE);
        testEncoder(encoder);
        encoder.destroy();
    }

    public void testEncoder(IEncoder encoder) throws NativeAudioException {
        assertFalse(encoder.isReady());
        assertEquals(0, encoder.getBufferedFrames());

        //
        final short[] dummyFrame = new short[FRAME_SIZE];
        for (int i = 0; i < FRAMES_PER_PACKET; i++) {
            assertFalse(encoder.isReady());
            encoder.encode(dummyFrame, FRAME_SIZE);
        }
        assertTrue(encoder.isReady());
        assertEquals(FRAMES_PER_PACKET, encoder.getBufferedFrames());

        // Flushing
        PacketBuffer buffer = PacketBuffer.allocate(MAX_BUFFER_SIZE);
        encoder.getEncodedData(buffer);
        assertFalse(encoder.isReady());
        assertEquals(0, encoder.getBufferedFrames());

        // Termination (for frames per packet > 1)
        encoder.encode(dummyFrame, FRAME_SIZE);
        assertFalse(encoder.isReady());
        encoder.terminate();
        assertTrue(encoder.isReady());

    }
}
