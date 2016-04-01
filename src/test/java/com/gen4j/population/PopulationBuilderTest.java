package com.gen4j.population;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.util.Optional;

import org.junit.Test;
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
    private static final int INVALID_LENGTH = -1;

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

    @Test
    public void testPopulationCreation()
    {
        reset(populationInstantiator, population, genotypeGenerator, genotype, geneticAlgorithmFactory);

        prepareCreationExpectations();

        replay(populationInstantiator, population, genotypeGenerator, genotype, geneticAlgorithmFactory);

        subject = PopulationBuilder.of(geneticAlgorithmFactory);

        subject
            .size(VALID_POPULATION_SIZE)
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .genotypeGenerator(genotypeGenerator)
            .build();

    }

    @SuppressWarnings("unchecked")
    private void prepareCreationExpectations() {
        expect(geneticAlgorithmFactory.fitnessFunction()).andReturn(fitnessFunction).times(VALID_POPULATION_SIZE);
        expect(geneticAlgorithmFactory.genotypeGenerator()).andReturn(genotypeGenerator).times(VALID_POPULATION_SIZE);
        expect(geneticAlgorithmFactory.populationInstantiator()).andReturn(populationInstantiator).times(VALID_POPULATION_SIZE);

        expect(populationInstantiator.instantiate(anyObject(Optional.class))).andReturn(population);
        expect(genotypeGenerator.generate(eq(VALID_GENOTYPE_LENGTH))).andReturn(genotype).times(VALID_POPULATION_SIZE);
        expect(population.add(anyObject(Chromosome.class))).andReturn(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidSize()
    {
        PopulationBuilder.of(geneticAlgorithmFactory).size(INVALID_SIZE).build();
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidLength()
    {
        PopulationBuilder.of(geneticAlgorithmFactory).size(INVALID_LENGTH).build();

    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidGeneticAlgorithmFactory()
    {
        PopulationBuilder.of(geneticAlgorithmFactory)
            .size(VALID_POPULATION_SIZE)
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .build();
    }
}
