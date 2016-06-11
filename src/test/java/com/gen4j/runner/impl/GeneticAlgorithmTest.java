package com.gen4j.runner.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.leq;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genetic.algorithm.GeneticAlgorithm;
import com.gen4j.genetic.algorithm.GeneticAlgorithmSolution;
import com.gen4j.operator.CrossOver;
import com.gen4j.operator.Mutation;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Criteria;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;

@RunWith(PowerMockRunner.class)
public class GeneticAlgorithmTest {

    private static final int CHROMOSOME_LENGHT = 5;
    private static final int POPULATION_SIZE = 15;
    private static final int GENERATIONS_COUNT = 10;

    @Mock
    private Criteria<Chromosome> stopCriteria;

    @Mock
    private FitnessFunction fitnessFunction;

    @Mock
    private ChromosomeCoder<Chromosome> coder;

    @Mock
    private PopulationInstantiator<Chromosome> populationInstantiator;

    @Mock
    private Population<Chromosome> population;

    @Mock
    private Selector<Chromosome> selector;

    @Mock
    private CrossOver<Chromosome> crossOver;

    @Mock
    private Mutation<Chromosome> mutation;

    @Mock
    private GeneticAlgorithmFactory<Chromosome> factory;

    @Mock
    private Individual<Chromosome> fittest;

    @Mock
    private Individual<Chromosome> individual1;
    @Mock
    private Individual<Chromosome> individual2;

    @Mock
    private Chromosome chromosome1;
    @Mock
    private Chromosome chromosome2;

    private GeneticAlgorithm<Chromosome> subject;
    private List<Individual<Chromosome>> selectedIndividuals;

    @SuppressWarnings("unchecked")
    @Test
    public void testCommonScenario() {

        resetAll();

        prepareFactoryExpectations();

        expect(population.size()).andReturn(POPULATION_SIZE).anyTimes();
        expect(population.addAll(anyObject(Collection.class))).andReturn(Boolean.TRUE).times(GENERATIONS_COUNT);
        expect(population.fittest()).andReturn(fittest).times(GENERATIONS_COUNT, Integer.MAX_VALUE);
        expect(fittest.fitness()).andReturn(1d).anyTimes();

        expect(individual1.chromosome()).andReturn(chromosome1).anyTimes();
        expect(individual2.chromosome()).andReturn(chromosome2).anyTimes();
        expect(chromosome1.length()).andReturn(CHROMOSOME_LENGHT).anyTimes();
        expect(chromosome2.length()).andReturn(CHROMOSOME_LENGHT).anyTimes();

        prepareOperators();

        replayAll();

        subject = GeneticAlgorithm.create(selector, crossOver, mutation);

        final GeneticAlgorithmSolution<Chromosome> solution = subject.evolve(population, factory);

        assertEquals(GENERATIONS_COUNT, solution.generationsCount());
        assertEquals(fittest, solution.fittest());
    }

    @SuppressWarnings("unchecked")
    private void prepareFactoryExpectations() {
        expect(factory.coder()).andReturn(coder).anyTimes();
        expect(factory.fitnessFunction()).andReturn(fitnessFunction).anyTimes();
        expect(factory.populationInstantiator()).andReturn(populationInstantiator).times(GENERATIONS_COUNT);
        expect(factory.chromosomeGenerator()).andReturn(Optional.empty()).times(GENERATIONS_COUNT, Integer.MAX_VALUE);
        final int expectedStopCriteriaCalls = GENERATIONS_COUNT + 1;
        expect(factory.stopCriteria()).andReturn(stopCriteria).times(expectedStopCriteriaCalls);

        expect(coder.chromosomeLength()).andReturn(CHROMOSOME_LENGHT).times(GENERATIONS_COUNT, Integer.MAX_VALUE);
        expect(populationInstantiator.instantiate()).andReturn(population).times(GENERATIONS_COUNT);
        expect(stopCriteria.apply(anyObject(Population.class), leq(expectedStopCriteriaCalls)))
                .andAnswer(() -> ((Integer) getCurrentArguments()[1]).intValue() >= GENERATIONS_COUNT)
                .times(expectedStopCriteriaCalls);
    }

    @SuppressWarnings("unchecked")
    private void prepareOperators() {
        expect(crossOver.probability()).andReturn(0.7).anyTimes();
        expect(crossOver.chromosomeCount()).andReturn(2).anyTimes();
        selector.population(anyObject(Population.class));
        expectLastCall().anyTimes();
        selectedIndividuals = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (i % 2 == 0) {
                selectedIndividuals.add(individual1);
            } else {
                selectedIndividuals.add(individual2);
            }
        }
        expect(selector.select(eq(POPULATION_SIZE))).andReturn(selectedIndividuals).anyTimes();
//        expect(operator.apply(anyObject(Collection.class), eq(factory), generationCount)).andReturn(selectedIndividuals).anyTimes();
    }

}
