package com.gen4j.factory;

import static com.gen4j.utils.Strings.reverse;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.util.Arrays.asList;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;

import java.util.BitSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.genotype.bit.BitSetGenotype;
import com.gen4j.phenotype.Phenotype;
import com.google.common.math.DoubleMath;;

/**
 * Based on Michalewicz example.
 * <p>
 * MICHALEWICZ, Zbigniew. <b>Genetic algorithms + data structures= evolution
 * programs</b>. Springer Science & Business Media, 2013, p.19-21.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BitSetGenotype.class)
public class BitSetGenotypeEncoderTest {

    private static final double X3_VALUE = 1.627888;

    private static final double X2_VALUE = -0.958973;

    private static final double X1_VALUE = 0.637197;

    private static final String X3 = "x3";

    private static final String X2 = "x2";

    private static final String X1 = "x1";

    @Mock
    private BitSetGenotype genotype;

    @Mock
    private BitSetPhenotype phenotype;

    @Mock
    private BitSet bits;

    private static final int N_BITS = 22;
    private static final int LOWER_BOUND = -1;
    private static final int UPPER_BOUND = 2;
    private static final int PRECISION = 6;

    private BitSetGenotypeEncoder subject;
    private final Set<String> variablesIdentifiers = newLinkedHashSet(asList(X1, X2, X3));

    private final String variablesBitString = new StringBuilder()
            .append(reverse("1000101110110101000111"))
            .append(reverse("0000001110000000010000"))
            .append(reverse("1110000000111111000101"))
            .toString();
    @Test
    public void testDecoding() {

        reset(genotype, bits);

        prepareDecodingExpectations();

        replay(genotype, bits);

        createSubject();

        final Phenotype<String> actual = subject.decode(genotype);

        final Phenotype<String> expected = expectedPhenotype();

        final double precisionTolerance = Math.pow(10, -(PRECISION));

        assertTrue(DoubleMath.fuzzyEquals(actual.variable(X1), expected.variable(X1), precisionTolerance));
        assertTrue(DoubleMath.fuzzyEquals(actual.variable(X2), expected.variable(X2), precisionTolerance));
        assertTrue(DoubleMath.fuzzyEquals(actual.variable(X3), expected.variable(X3), precisionTolerance));
    }

    private void createSubject() {
        subject = new BitSetGenotypeEncoder(variablesIdentifiers, LOWER_BOUND, UPPER_BOUND, PRECISION);
    }

    private Phenotype<String> expectedPhenotype() {
        final Phenotype<String> expected = new BitSetPhenotype();

        expected.set(X1, X1_VALUE);
        expected.set(X2, X2_VALUE);
        expected.set(X3, X3_VALUE);
        return expected;
    }

    private void prepareDecodingExpectations() {
        expect(genotype.value()).andReturn(bits);
        expect(genotype.toString()).anyTimes();

        for (int i = 0; i < N_BITS * variablesIdentifiers.size(); i++) {
            expect(bits.get(eq(i))).andReturn(variablesBitString.charAt(i) == '1');
        }
    }

    @Test
    public void testEncoding() {
        reset(phenotype);
        expect(phenotype.variable(eq(X1))).andReturn(X1_VALUE);
        expect(phenotype.variable(eq(X2))).andReturn(X2_VALUE);
        expect(phenotype.variable(eq(X3))).andReturn(X3_VALUE);
        replay(phenotype);

        createSubject();

        final BitSetGenotype actual = subject.encode(phenotype);

        final int length = N_BITS * variablesIdentifiers.size();
        final BitSet bits = new BitSet(length);
        for (int i = 0; i < length; i++) {
            if(variablesBitString.charAt(i) == '1') {
                bits.set(i);
            }
        }
        final BitSetGenotype expected = new BitSetGenotype(bits, length);
        assertEquals(expected, actual);
    }

}
