package com.gen4j.operator.selection.roulette;

import static java.util.Arrays.asList;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.DoubleFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.utils.Pair;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@RunWith(PowerMockRunner.class)
public class RouletteTest {

    private static final double FITNESS_1 = 2.2;
    private static final double FITNESS_2 = 3.3;
    private static final double FITNESS_3 = 1.1;

    private static final double TOTAL_FITNESS = FITNESS_1 + FITNESS_2 + FITNESS_3;

    private static final double RELATIVE_FITNESS_1 = FITNESS_1 / TOTAL_FITNESS;
    private static final double RELATIVE_FITNESS_2 = FITNESS_2 / TOTAL_FITNESS;
    private static final double RELATIVE_FITNESS_3 = FITNESS_3 / TOTAL_FITNESS;

    private static final double MAX_RELATIVE_FITNESS = RELATIVE_FITNESS_2;

    private ExpectedException expectedException;

    @Mock
    private Chromosome<Genotype> chromosome1;
    @Mock
    private Chromosome<Genotype> chromosome2;
    @Mock
    private Chromosome<Genotype> chromosome3;

    @Mock("doubles")
    private Random random;

    @Mock("mapToObj")
    private DoubleStream randomDoubles;

    @Mock("collect")
    private Stream<Chromosome<Genotype>> chromosomeStream;

    private Set<Chromosome<Genotype>> chromosomes;

    private Roulette<Genotype> subject;

    @Before
    public void setup() {
        chromosomes = Sets.newLinkedHashSet(asList(chromosome1, chromosome2, chromosome3));
        expectedException = ExpectedException.none();
    }

    private void createChromosomeExpectations() {
        expect(chromosome1.fitness()).andReturn(FITNESS_1).anyTimes();
        expect(chromosome2.fitness()).andReturn(FITNESS_2).anyTimes();
        expect(chromosome3.fitness()).andReturn(FITNESS_3).anyTimes();
    }

    private List<Pair<Chromosome<Genotype>, Double>> createRouletteList() {
        return asList(
                Pair.of(chromosome3, RELATIVE_FITNESS_3),
                Pair.of(chromosome1, RELATIVE_FITNESS_1),
                Pair.of(chromosome2, RELATIVE_FITNESS_2));
    }

    @SuppressWarnings("unchecked")
    private List<Chromosome<Genotype>> expectedSortedChrosomes() {
        return Lists.newArrayList(chromosome1, chromosome2, chromosome3);
    }

    @Test
    public void testCreation() throws Exception {
        resetAll();
        createChromosomeExpectations();
        final List<Pair<Chromosome<Genotype>, Double>> rouletteList = createRouletteList();
        expectNew(Roulette.class, rouletteList, MAX_RELATIVE_FITNESS).andThrow(new Exception());
        expectedException.expect(Exception.class);
        replayAll();

        subject = Roulette.of(chromosomes);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void testSortChromosomes() throws Exception {
        resetAll();

        createChromosomeExpectations();
        expect(random.doubles(eq(3l), eq(0d), eq(MAX_RELATIVE_FITNESS))).andReturn(randomDoubles);
        expect(randomDoubles.mapToObj(anyObject(DoubleFunction.class))).andReturn(chromosomeStream);
        expect(chromosomeStream.collect(anyObject(Collector.class)))
                .andAnswer(new IAnswer<List<Chromosome<Genotype>>>() {
                    @Override
                    public List<Chromosome<Genotype>> answer() throws Throwable {
                        final List<Chromosome<Genotype>> result = new ArrayList<>(3);

                        final Method sortChromosome = MemberModifier.method(Roulette.class, "sortChromosome",
                                double.class);
                        result.add((Chromosome<Genotype>) sortChromosome.invoke(subject, RELATIVE_FITNESS_1 - 0.01));
                        result.add((Chromosome<Genotype>) sortChromosome.invoke(subject, RELATIVE_FITNESS_2 - 0.01));
                        result.add((Chromosome<Genotype>) sortChromosome.invoke(subject, RELATIVE_FITNESS_3 - 0.01));
                        return result;
                    }
                });
        replayAll();

        subject = Roulette.of(chromosomes);

        MemberModifier.field(Roulette.class, "random").set(subject, random);

        assertEquals(expectedSortedChrosomes(), subject.sortChromosomes(3));
    }

}