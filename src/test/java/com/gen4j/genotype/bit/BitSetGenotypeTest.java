package com.gen4j.genotype.bit;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;

import java.math.RoundingMode;
import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.math.IntMath;

@RunWith(PowerMockRunner.class)
public class BitSetGenotypeTest {

    private static final int BITS_LENGTH = 8;

    private static final String BIT_SIZE = "01010";

    @Mock
    private BitSet bits;

    private BitSetGenotype subject;

    @Before
    public void setup() {
        reset(bits);
    }

    @Test
    public void testValue() {
        prepareBitsExpectations();
        replay(bits);
        createSubject();

        assertEquals(bits, subject.value());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        expect(bits.size()).andReturn(IntMath.log2(BIT_SIZE.length(), RoundingMode.CEILING) - 1).times(1);
        replay(bits);
        createSubject();
    }

    @Test
    public void testCopyConstructor() {
        final BitSet bits = new BitSet(4);
        bits.set(0);
        bits.set(2);
        subject = new BitSetGenotype(bits, bits.size());

        assertEquals(new BitSetGenotype(subject), subject);
    }

    @Test
    public void testToString() {

        prepareBitsExpectations();

        final int genotypeLength = BIT_SIZE.length();
        expect(bits.length()).andReturn(genotypeLength);
        for (int i = genotypeLength; i > 0; i--) {
            expect(bits.get(eq(genotypeLength - i))).andReturn(BIT_SIZE.charAt(i - 1) == '1');
        }

        replay(bits);
        createSubject();

        assertEquals(BIT_SIZE, subject.toString());
    }

    public void createSubject() {
        subject = new BitSetGenotype(bits, BIT_SIZE.length());
    }

    private void prepareBitsExpectations() {
        expect(bits.size()).andReturn(BITS_LENGTH).times(1);
    }

}
