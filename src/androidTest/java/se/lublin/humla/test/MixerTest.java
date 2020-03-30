package se.lublin.humla.test;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import se.lublin.humla.audio.BasicClippingShortMixer;
import se.lublin.humla.audio.IAudioMixer;
import se.lublin.humla.audio.IAudioMixerSource;

/**
 * Created by andrew on 16/07/15.
 */
public class MixerTest extends TestCase {
    /**
     * Tests that mixing order should not affect the output.
     */
    public void testMixerCommutativity(IAudioMixer<float[], short[]> mixer) {
        BasicSource<float[]> pcmA = new BasicSource<>(new float[] { 0.2f, 0.5f, 0.7f }, 3);
        BasicSource<float[]> pcmB = new BasicSource<>(new float[] { 0.3f, 0.5f, 0.5f }, 3);
        BasicSource<float[]> pcmC = new BasicSource<>(new float[] { 0.0f, 0.0f, -0.5f }, 3);
        final short[] outputABC = new short[3];
        final short[] outputCBA = new short[3];

        List<IAudioMixerSource<float[]>> sourcesABC = new ArrayList<>();
        sourcesABC.add(pcmA);
        sourcesABC.add(pcmB);
        sourcesABC.add(pcmC);
        List<IAudioMixerSource<float[]>> sourcesCBA = new ArrayList<>();
        sourcesCBA.add(pcmC);
        sourcesCBA.add(pcmB);
        sourcesCBA.add(pcmA);

        mixer.mix(sourcesABC, outputABC, 0, 3);
        mixer.mix(sourcesCBA, outputCBA, 0, 3);

        for (int i = 0; i < 3; i++) {
            assertEquals("Mixing should be commutative.", outputABC[i], outputCBA[i]);
        }
    }

    public void testBasicClippingShortMixer() {
        testMixerCommutativity(new BasicClippingShortMixer());
    }

    private static class BasicSource<T> implements IAudioMixerSource<T> {
        private T mSamples;
        private int mLength;

        public BasicSource(T samples, int length) {
            mSamples = samples;
            mLength = length;
        }

        @Override
        public T getSamples() {
            return mSamples;
        }

        @Override
        public int getNumSamples() {
            return mLength;
        }
    }
}
