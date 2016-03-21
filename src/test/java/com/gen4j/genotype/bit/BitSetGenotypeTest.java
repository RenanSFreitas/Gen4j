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

    private static final int BITS_CARDINALITY = 8;

    private static final String BIT_STRING = "01010";

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
        expect(bits.cardinality()).andReturn(IntMath.log2(BIT_STRING.length(), RoundingMode.CEILING) - 1).times(1);
        replay(bits);
        createSubject();
    }


    @Test
    public void testToString() {

        prepareBitsExpectations();

        final int genotypeLength = BIT_STRING.length();
        for (int i = genotypeLength; i > 0; i--) {
            expect(bits.get(eq(genotypeLength - i))).andReturn(BIT_STRING.charAt(i - 1) == '1');
        }

        replay(bits);
        createSubject();

        assertEquals(BIT_STRING, subject.toString());
    }

    private void createSubject() {
        subject = new BitSetGenotype(bits, BIT_STRING.length());
    }

    private void prepareBitsExpectations() {
        expect(bits.cardinality()).andReturn(BITS_CARDINALITY).times(1);
    }

}
