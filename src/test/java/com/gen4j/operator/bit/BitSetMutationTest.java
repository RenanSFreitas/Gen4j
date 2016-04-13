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

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.utils.BitSets;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BitSetMutation.class, BitChromosome.class })
public class BitSetMutationTest {


    private static final int GENOTYPE_LENGTH = 8;
    private static final int MUTANT_BIT = 4;
    private static final boolean MUTANT_BIT_VALUE = true;

    @Mock
    private Random random;

    @Mock
    private BitChromosome chromosome;

    private static final String GENOTYPE_BITS = "00101011";
    private BitSet chromosomeBits;

    @TestSubject
    private BitSetMutation subject;

    @Before
    public void setUp() throws IllegalAccessException {
        subject = new BitSetMutation();
        MemberModifier.field(BitSetMutation.class, "random").set(subject, random);

        chromosomeBits = BitSets.fromString(GENOTYPE_BITS);
    }

    @Test
    public void testMutation() {
        reset(random, chromosome);

        expect(chromosome.length()).andReturn(GENOTYPE_LENGTH);

        expect(chromosome.value()).andReturn(chromosomeBits).times(2);

        expect(random.nextInt(eq(GENOTYPE_LENGTH))).andReturn(MUTANT_BIT);

        replay(random, chromosome);

        final BitChromosome mutant = getOnlyElement(subject.apply(singleton(chromosome)));

        assertEquals(MUTANT_BIT_VALUE, mutant.value().get(MUTANT_BIT));
    }

}
