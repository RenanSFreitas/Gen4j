package com.gen4j.runner.impl;

import static com.google.common.collect.ImmutableList.of;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.leq;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;

import java.util.Collection;
import java.util.Optional;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.genotype.GenotypeEncoder;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Criteria;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;

@RunWith(PowerMockRunner.class)
public class GeneticAlgorithmTest {

    private static final int GENOTYPE_LENGHT = 5;
    private static final int POPULATION_SIZE = 15;
    private static final int GENERATIONS_COUNT = 10;

    @Mock
    private Criteria<Genotype> stopCriteria;

    @Mock
    private FitnessFunction<Genotype> fitnessFunction;

    @Mock
    private GenotypeEncoder<Genotype, String> encoder;

    @Mock
    private PopulationInstantiator<Void, Genotype> populationInstantiator;

    @Mock
    private Population<Genotype> initialPopulation;

    @Mock
    private Selector<Genotype> selector;

    @Mock
    private GeneticOperator<Genotype> operator;

    @Mock
    private GeneticAlgorithmFactory<Genotype, String, Void> factory;

    @Mock
    private Chromosome<Genotype> chromosome1;
    @Mock
    private Chromosome<Genotype> chromosome2;

    @Mock
    private Genotype genotype1;
    @Mock
    private Genotype genotype2;

    private GeneticAlgorithm<Genotype, String, Void> subject;

    @SuppressWarnings("unchecked")
    @Test
    public void testCommonScenario() {

        resetAll();

        expect(factory.encoder()).andReturn(encoder);
        expect(factory.fitnessFunction()).andReturn(fitnessFunction).anyTimes();
        expect(factory.populationInstantiator()).andReturn(populationInstantiator);
        final int expectedStopCriteriaCalls = GENERATIONS_COUNT + 1;
        expect(factory.stopCriteria()).andReturn(stopCriteria).times(expectedStopCriteriaCalls);
        expect(factory.genotypeGenerator()).andReturn(Optional.empty());
        expect(stopCriteria.apply(
                anyObject(Population.class), leq(expectedStopCriteriaCalls)))
        .andAnswer(() -> ((Integer)getCurrentArguments()[1]).intValue() <= expectedStopCriteriaCalls);

        expect(initialPopulation.size()).andReturn(POPULATION_SIZE).anyTimes();
        expect(operator.probability()).andReturn(0.7).anyTimes();
        expect(operator.chromosomeCount()).andReturn(2).anyTimes();
        selector.population(anyObject(Population.class));
        expectLastCall().anyTimes();
        expect(selector.select(EasyMock.eq(2))).andReturn(of(chromosome1, chromosome2)).anyTimes();
        expect(chromosome1.genotype()).andReturn(genotype1).anyTimes();
        expect(chromosome2.genotype()).andReturn(genotype2).anyTimes();
        expect(operator.apply(anyObject(Collection.class))).andReturn(of(genotype1, genotype2));
        expect(genotype1.length()).andReturn(GENOTYPE_LENGHT).anyTimes();
        expect(genotype2.length()).andReturn(GENOTYPE_LENGHT).anyTimes();

        replayAll();

        subject = new GeneticAlgorithm<>();
        subject.addGeneticOperator(operator, selector);
        subject.execute(initialPopulation, factory);
    }

}
