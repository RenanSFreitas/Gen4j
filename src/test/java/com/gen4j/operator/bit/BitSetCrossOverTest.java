package com.gen4j.operator.bit;

import static com.gen4j.utils.Strings.reverse;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;

import java.util.BitSet;
import java.util.Random;

import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.genotype.bit.BitSetGenotype;
import com.gen4j.operator.bit.BitSetCrossOver;
import com.gen4j.utils.BitSets;
import com.gen4j.utils.Pair;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BitSetCrossOver.class, BitSetGenotype.class })
public class BitSetCrossOverTest {

    private final int GENOTYPE_LENGTH = 8;
    private final int CROSSOVER_POINT = 3;

    @Mock
    private Random random;

    @Mock
    private BitSetGenotype firstParent;
    @Mock
    private BitSetGenotype secondParent;

    @Mock
    private BitSet firstParentBits;
    @Mock
    private BitSet secondParentBits;

    private final String parentBitsString1 = reverse("10101010");
    private final String parentBitsString2 = reverse("01010101");

    private final String firstOffspring = "01010010";
    private final String secondOffspring = "10101101";

    @TestSubject
    private BitSetCrossOver subject;

    @Before
    public void setUp() throws IllegalAccessException {
        subject = new BitSetCrossOver();
        MemberModifier.field(BitSetCrossOver.class, "random").set(subject, random);
    }

    @Test
    public void test() {
        reset(random, firstParent, secondParent, firstParentBits, secondParentBits);

        expect(firstParent.length()).andReturn(GENOTYPE_LENGTH).times(2);
        expect(secondParent.length()).andReturn(GENOTYPE_LENGTH).times(2);

        expect(random.nextInt(eq(GENOTYPE_LENGTH))).andReturn(CROSSOVER_POINT);

        expect(firstParent.value()).andReturn(firstParentBits).times(GENOTYPE_LENGTH);
        expect(secondParent.value()).andReturn(secondParentBits).times(GENOTYPE_LENGTH);

        for (int i = 0; i < GENOTYPE_LENGTH; i++) {
            expect(firstParentBits.get(eq(i))).andReturn(parentBitsString1.charAt(i) == '1').times(1);
            expect(secondParentBits.get(eq(i))).andReturn(parentBitsString2.charAt(i) == '1').times(1);
        }

        replay(random, firstParent, secondParent, firstParentBits, secondParentBits);

        final Pair<BitSetGenotype, BitSetGenotype> actual = subject.apply(firstParent, secondParent);

        assertEquals(firstOffspring, BitSets.toString(actual.first().value(), GENOTYPE_LENGTH));
        assertEquals(secondOffspring, BitSets.toString(actual.second().value(), GENOTYPE_LENGTH));
    }
}
