package com.gen4j.operator.bit;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.singleton;
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
import com.gen4j.utils.BitSets;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BitSetMutation.class, BitSetGenotype.class })
public class BitSetMutationTest {


    private static final int GENOTYPE_LENGTH = 8;
    private static final int MUTANT_BIT = 4;
    private static final boolean MUTANT_BIT_VALUE = true;

    @Mock
    private Random random;

    @Mock
    private BitSetGenotype genotype;

    private static final String GENOTYPE_BITS = "00101011";
    private BitSet genotypeBits;

    @TestSubject
    private BitSetMutation subject;

    @Before
    public void setUp() throws IllegalAccessException {
        subject = new BitSetMutation();
        MemberModifier.field(BitSetMutation.class, "random").set(subject, random);

        genotypeBits = BitSets.fromString(GENOTYPE_BITS);
    }

    @Test
    public void testMutation() {
        reset(random, genotype);

        expect(genotype.length()).andReturn(GENOTYPE_LENGTH);

        expect(genotype.value()).andReturn(genotypeBits).times(2);

        expect(random.nextInt(eq(GENOTYPE_LENGTH))).andReturn(MUTANT_BIT);

        replay(random, genotype);

        final BitSetGenotype mutant = getOnlyElement(subject.apply(singleton(genotype)));

        assertEquals(MUTANT_BIT_VALUE, mutant.value().get(MUTANT_BIT));
    }

}
