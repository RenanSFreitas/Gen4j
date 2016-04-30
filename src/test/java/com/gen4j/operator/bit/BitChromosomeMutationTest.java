package com.gen4j.operator.bit;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.singleton;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;

import java.util.BitSet;
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
@PrepareForTest({ BitFlipMutation.class, BitChromosome.class })
public class BitChromosomeMutationTest {


    private static final int GENOTYPE_LENGTH = 8;
    private static final int MUTANT_BIT = 4;
    private static final boolean MUTANT_BIT_VALUE = true;

    @Mock
    private Random random;

    @Mock
    private BitChromosome chromosome;

    @Mock
    private Individual<BitChromosome> individual;

    private static final String GENOTYPE_BITS = "00101011";
    private BitSet chromosomeBits;

    @TestSubject
    private BitFlipMutation subject;

    @Mock
    private FitnessFunction fitnessFunction;

    @Mock("fitnessFunction")
    private AbstractGeneticAlgorithmFactory<BitChromosome> factory;

    @Mock
    private ChromosomeCoder<BitChromosome> coder;

    @Before
    public void setUp() throws IllegalAccessException {
        subject = new BitFlipMutation();
        MemberMatcher.field(BitFlipMutation.class, "random").set(subject, random);

        chromosomeBits = BitSets.fromString(GENOTYPE_BITS);
    }

    @Test
    public void testMutation() {

        resetAll();

        expect(chromosome.length()).andReturn(GENOTYPE_LENGTH);

        expect(chromosome.value()).andReturn(chromosomeBits).times(2);

        expect(random.nextInt(eq(GENOTYPE_LENGTH))).andReturn(MUTANT_BIT);

        expect(individual.chromosome()).andReturn(chromosome);

        expect(factory.fitnessFunction()).andReturn(fitnessFunction);

        expect(factory.coder()).andReturn(coder);

        replayAll();

        final Individual<BitChromosome> mutant = getOnlyElement(subject.apply(singleton(individual), factory, generationCount));

        assertEquals(MUTANT_BIT_VALUE, mutant.chromosome().value().get(MUTANT_BIT));
    }

}

