package com.gen4j.population;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;

import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.generator.ChromosomeGenerator;

@RunWith(PowerMockRunner.class)
public class PopulationBuilderTest
{
    private static final int INVALID_SIZE = -1;
    private static final int INVALID_GENOTYPE_LENGTH = -1;

    private static final int VALID_POPULATION_SIZE = 1;
    private static final int VALID_GENOTYPE_LENGTH = 1;

    @Mock
    private PopulationInstantiator<Void, Chromosome> populationInstantiator;

    @Mock
    private Population<Chromosome> population;

    @Mock
    private FitnessFunction<Chromosome> fitnessFunction;

    @Mock
    private ChromosomeGenerator<Chromosome> chromosomeGenerator;

    @Mock
    private GeneticAlgorithmFactory<Chromosome, String, Void> geneticAlgorithmFactory;

    @Mock
    private Chromosome chromosome;

    @Mock
    private ChromosomeCoder<Chromosome, String> coder;

    private PopulationBuilder<Chromosome, String, Void> subject;

    @Rule
    private final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        resetAll();
    }

    @Test
    public void testPopulationCreation()
    {
        prepareCommonExpectations();
        prepareCreationExpectations();
        replayAll();

        subject = PopulationBuilder.of(geneticAlgorithmFactory);

        subject
            .size(VALID_POPULATION_SIZE)
            .build();

    }

    private void prepareCommonExpectations() {
        expect(geneticAlgorithmFactory.chromosomeGenerator()).andReturn(Optional.of(chromosomeGenerator)).anyTimes();
    }

    @SuppressWarnings("unchecked")
    private void prepareCreationExpectations() {

        expect(geneticAlgorithmFactory.coder()).andReturn(coder).times(2);
        expect(coder.chromosomeLength()).andReturn(VALID_GENOTYPE_LENGTH).times(2);
        expect(geneticAlgorithmFactory.fitnessFunction()).andReturn(fitnessFunction).times(VALID_POPULATION_SIZE);
        expect(geneticAlgorithmFactory.populationInstantiator()).andReturn(populationInstantiator).times(VALID_POPULATION_SIZE);

        expect(populationInstantiator.instantiate(anyObject(Optional.class))).andReturn(population);
        expect(chromosomeGenerator.generate(eq(VALID_GENOTYPE_LENGTH))).andReturn(chromosome).times(VALID_POPULATION_SIZE);
        expect(population.addAll(anyObject(Collection.class))).andReturn(true);
        expect(population.size()).andReturn(0);
        expect(population.add(anyObject(Individual.class))).andReturn(true);
    }

    @Test
    public void testInvalidSize()
    {
        prepareCommonExpectations();
        prepareChromosomeLengthExpectation(VALID_GENOTYPE_LENGTH);
        replayAll();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Population size should be greater than zero.");

        PopulationBuilder.of(geneticAlgorithmFactory)
            .size(INVALID_SIZE)
            .build();
    }

    private void prepareChromosomeLengthExpectation(final int value) {
        expect(geneticAlgorithmFactory.coder()).andReturn(coder).times(1);
        expect(coder.chromosomeLength()).andReturn(value).times(1);
    }

    @Test
    public void testInvalidLength()
    {
        prepareCommonExpectations();
        prepareChromosomeLengthExpectation(INVALID_GENOTYPE_LENGTH);
        replayAll();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Chromosome length should be greater than zero.");

        PopulationBuilder.of(geneticAlgorithmFactory)
                .size(VALID_POPULATION_SIZE)
                .build();

    }

    @Test
    public void testInvalidInitialChromosomes()
    {
        expect(geneticAlgorithmFactory.chromosomeGenerator()).andReturn(Optional.empty());
        prepareChromosomeLengthExpectation(VALID_GENOTYPE_LENGTH);
        replayAll();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(
                "Genotype generator must be provided when initial chromosomes cardinality is less than population size");

        PopulationBuilder.of(geneticAlgorithmFactory)
            .size(VALID_POPULATION_SIZE)
            .build();
    }
}
