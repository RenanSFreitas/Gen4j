package com.gen4j.operator.bit;

import static java.util.Arrays.asList;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Random;

import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.factory.AbstractGeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Individual;
import com.gen4j.utils.BitSets;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BitSetCrossOver.class, BitChromosome.class })
public class BitSetCrossOverTest {

    private final int GENOTYPE_LENGTH = 8;
    private final int CROSSOVER_POINT = 3;

    @Mock
    private Random random;

    @Mock
    private Individual<BitChromosome> firstParent;
    @Mock
    private Individual<BitChromosome> secondParent;

    @Mock
    private BitChromosome firstParentChromosome;
    @Mock
    private BitChromosome secondParentChromosome;


    @Mock("fitnessFunction")
    private AbstractGeneticAlgorithmFactory<BitChromosome> factory;

    @Mock
    private FitnessFunction fitnessFunction;

    @Mock
    private ChromosomeCoder<BitChromosome> coder;

    private final BitSet firstParentBits = BitSets.fromString("10101010");
    private final BitSet secondParentBits = BitSets.fromString("01010101");

    private final BitSet firstOffspring = BitSets.fromString("01010010");
    private final BitSet secondOffspring = BitSets.fromString("10101101");

    @TestSubject
    private BitSetCrossOver subject;

    @Before
    public void setUp() throws IllegalAccessException {
        subject = new BitSetCrossOver();
        MemberMatcher.field(BitSetCrossOver.class, "random").set(subject, random);
    }

    @Test
    public void testCrossOver() {

        resetAll();

        expect(firstParentChromosome.length()).andReturn(GENOTYPE_LENGTH).times(2);
        expect(secondParentChromosome.length()).andReturn(GENOTYPE_LENGTH).times(2);

        expect(random.nextInt(eq(GENOTYPE_LENGTH))).andReturn(CROSSOVER_POINT);

        expect(firstParentChromosome.value()).andReturn(firstParentBits).times(GENOTYPE_LENGTH);
        expect(secondParentChromosome.value()).andReturn(secondParentBits).times(GENOTYPE_LENGTH);

        expect(firstParent.chromosome()).andReturn(firstParentChromosome);
        expect(secondParent.chromosome()).andReturn(secondParentChromosome);

        expect(factory.fitnessFunction()).andReturn(fitnessFunction).times(2);
        expect(factory.coder()).andReturn(coder).times(2);
        replayAll();

        final Iterator<Individual<BitChromosome>> actual = subject.apply(asList(firstParent, secondParent), factory)
                .iterator();

        assertEquals(firstOffspring, actual.next().chromosome().value());
        assertEquals(secondOffspring, actual.next().chromosome().value());
    }
}
