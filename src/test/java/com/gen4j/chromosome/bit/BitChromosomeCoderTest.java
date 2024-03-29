package com.gen4j.chromosome.bit;

import static com.gen4j.utils.Strings.reverse;
import static java.util.Arrays.asList;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;

import java.util.BitSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.chromosome.Range;
import com.gen4j.phenotype.Phenotype;
import com.gen4j.phenotype.StandardPhenotype;

/**
 * Based on Michalewicz example.
 * <p>
 * MICHALEWICZ, Zbigniew. <b>Genetic algorithms + data structures= evolution
 * programs</b>. Springer Science & Business Media, 2013, p.19-21.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BitChromosome.class, StandardPhenotype.class })
public class BitChromosomeCoderTest {

    private static final double X3_VALUE = 1.627888;

    private static final double X2_VALUE = -0.958973;

    private static final double X1_VALUE = 0.637197;

    private static final String X3 = "x3";

    private static final String X2 = "x2";

    private static final String X1 = "x1";

    @Mock
    private BitChromosome chromosome;

    @Mock
    private StandardPhenotype phenotype;

    @Mock
    private BitSet bits;

    private static final int N_BITS = 22;
    private static final int CHROMOSOME_LENGTH = N_BITS * 3;
    private static final int LOWER_BOUND = -1;
    private static final int UPPER_BOUND = 2;
    private static final int PRECISION = 6;

    private BitChromosomeCoder subject;
    private final List<String> variablesIdentifiers = asList(X1, X2, X3);

    private final String variablesBitString = new StringBuilder()
            .append(reverse("1000101110110101000111"))
            .append(reverse("0000001110000000010000"))
            .append(reverse("1110000000111111000101"))
            .toString();

    @Before
    public void setup() {
        subject = new BitChromosomeCoder(variablesIdentifiers, Range.of(LOWER_BOUND, UPPER_BOUND), PRECISION);
    }

    @Test
    public void testChromosomeLength() {
        assertEquals(CHROMOSOME_LENGTH, subject.chromosomeLength());
    }

    @Test
    public void testDecoding() {

        reset(chromosome, bits);

        prepareDecodingExpectations();

        replay(chromosome, bits);

        final Phenotype actual = subject.decode(chromosome);

        final Phenotype expected = expectedPhenotype();

        final double precisionTolerance = Math.pow(10, -(PRECISION));

        assertEquals(actual.variable(X1), expected.variable(X1), precisionTolerance);
        assertEquals(actual.variable(X2), expected.variable(X2), precisionTolerance);
        assertEquals(actual.variable(X3), expected.variable(X3), precisionTolerance);
    }

    private Phenotype expectedPhenotype() {
        final Phenotype expected = new StandardPhenotype();

        expected.set(X1, X1_VALUE);
        expected.set(X2, X2_VALUE);
        expected.set(X3, X3_VALUE);
        return expected;
    }

    private void prepareDecodingExpectations() {
        expect(chromosome.value()).andReturn(bits);
        expect(chromosome.toString()).anyTimes();

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

        final BitChromosome actual = subject.encode(phenotype);

        final int length = N_BITS * variablesIdentifiers.size();
        final BitSet bits = new BitSet(length);
        for (int i = 0; i < length; i++) {
            if(variablesBitString.charAt(i) == '1') {
                bits.set(i);
            }
        }
        final BitChromosome expected = new BitChromosome(bits, length);
        assertEquals(expected, actual);
    }

}
