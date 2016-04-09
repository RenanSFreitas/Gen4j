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

import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.population.generator.RandomGenotypeGenerator;

@RunWith(PowerMockRunner.class)
public class PopulationBuilderTest
{
    private static final int INVALID_SIZE = -1;
    private static final int INVALID_GENOTYPE_LENGTH = -1;

    private static final int VALID_POPULATION_SIZE = 1;
    private static final int VALID_GENOTYPE_LENGTH = 1;

    @Mock
    private PopulationInstantiator<Void, Genotype> populationInstantiator;

    @Mock
    private Population<Genotype> population;

    @Mock
    private FitnessFunction<Genotype> fitnessFunction;

    @Mock
    private RandomGenotypeGenerator<Genotype> genotypeGenerator;

    @Mock
    private GeneticAlgorithmFactory<Genotype, String, Void> geneticAlgorithmFactory;

    @Mock
    private Genotype genotype;

    private PopulationBuilder<Genotype, String, Void> subject;

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
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .build();

    }

    private void prepareCommonExpectations() {
        expect(geneticAlgorithmFactory.genotypeGenerator()).andReturn(Optional.of(genotypeGenerator)).anyTimes();
    }

    @SuppressWarnings("unchecked")
    private void prepareCreationExpectations() {

        expect(geneticAlgorithmFactory.fitnessFunction()).andReturn(fitnessFunction).times(VALID_POPULATION_SIZE);
        expect(geneticAlgorithmFactory.populationInstantiator()).andReturn(populationInstantiator).times(VALID_POPULATION_SIZE);

        expect(populationInstantiator.instantiate(anyObject(Optional.class))).andReturn(population);
        expect(genotypeGenerator.generate(eq(VALID_GENOTYPE_LENGTH))).andReturn(genotype).times(VALID_POPULATION_SIZE);
        expect(population.addAll(anyObject(Collection.class))).andReturn(true);
        expect(population.size()).andReturn(0);
        expect(population.add(anyObject(Chromosome.class))).andReturn(true);
    }

    @Test
    public void testInvalidSize()
    {
        prepareCommonExpectations();
        replayAll();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Population size should be greater than zero.");

        PopulationBuilder.of(geneticAlgorithmFactory)
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .size(INVALID_SIZE)
            .build();
    }

    @Test
    public void testInvalidLength()
    {
        prepareCommonExpectations();
        replayAll();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Chromosome length should be greater than zero.");

        PopulationBuilder.of(geneticAlgorithmFactory)
                .genotypeLength(INVALID_GENOTYPE_LENGTH)
                .size(VALID_POPULATION_SIZE)
                .build();

    }

    @Test
    public void testInvalidInitialChromosomes()
    {
        expect(geneticAlgorithmFactory.genotypeGenerator()).andReturn(Optional.empty());
        replayAll();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(
                "Genotype generator must be provided when initial chromosomes cardinality is less than population size");

        PopulationBuilder.of(geneticAlgorithmFactory)
            .size(VALID_POPULATION_SIZE)
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .build();
    }
}
