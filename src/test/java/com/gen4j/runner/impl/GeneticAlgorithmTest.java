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

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Criteria;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;

@RunWith(PowerMockRunner.class)
public class GeneticAlgorithmTest {

    private static final int GENOTYPE_LENGHT = 5;
    private static final int POPULATION_SIZE = 15;
    private static final int GENERATIONS_COUNT = 10;

    @Mock
    private Criteria<Chromosome> stopCriteria;

    @Mock
    private FitnessFunction<Chromosome> fitnessFunction;

    @Mock
    private ChromosomeCoder<Chromosome, String> coder;

    @Mock
    private PopulationInstantiator<Void, Chromosome> populationInstantiator;

    @Mock
    private Population<Chromosome> initialPopulation;

    @Mock
    private Selector<Chromosome> selector;

    @Mock
    private GeneticOperator<Chromosome> operator;

    @Mock
    private GeneticAlgorithmFactory<Chromosome, String, Void> factory;

    @Mock
    private Individual<Chromosome> individual1;
    @Mock
    private Individual<Chromosome> individual2;

    @Mock
    private Chromosome chromosome1;
    @Mock
    private Chromosome chromosome2;

    private GeneticAlgorithm<Chromosome, String, Void> subject;

    @SuppressWarnings("unchecked")
    @Test
    public void testCommonScenario() {

        resetAll();

        expect(factory.coder()).andReturn(coder);
        expect(factory.fitnessFunction()).andReturn(fitnessFunction).anyTimes();
        expect(factory.populationInstantiator()).andReturn(populationInstantiator);
        final int expectedStopCriteriaCalls = GENERATIONS_COUNT + 1;
        expect(factory.stopCriteria()).andReturn(stopCriteria).times(expectedStopCriteriaCalls);
        expect(factory.chromosomeGenerator()).andReturn(Optional.empty());
        expect(stopCriteria.apply(anyObject(Population.class), leq(expectedStopCriteriaCalls)))
                .andAnswer(() -> ((Integer) getCurrentArguments()[1]).intValue() <= expectedStopCriteriaCalls);

        expect(initialPopulation.size()).andReturn(POPULATION_SIZE).anyTimes();
        expect(operator.probability()).andReturn(0.7).anyTimes();
        expect(operator.chromosomeCount()).andReturn(2).anyTimes();
        selector.population(anyObject(Population.class));
        expectLastCall().anyTimes();
        expect(selector.select(EasyMock.eq(2))).andReturn(of(individual1, individual2)).anyTimes();
        expect(individual1.chromosome()).andReturn(chromosome1).anyTimes();
        expect(individual2.chromosome()).andReturn(chromosome2).anyTimes();
        expect(operator.apply(anyObject(Collection.class))).andReturn(of(chromosome1, chromosome2));
        expect(chromosome1.length()).andReturn(GENOTYPE_LENGHT).anyTimes();
        expect(chromosome2.length()).andReturn(GENOTYPE_LENGHT).anyTimes();

        replayAll();

        subject = new GeneticAlgorithm<>();
        subject.addGeneticOperator(operator, selector);
        subject.evolve(initialPopulation, factory);
    }

}
