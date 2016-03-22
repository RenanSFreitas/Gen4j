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
    private Genotype genotype;

    private PopulationBuilder<Void, Genotype> subject;

    @Test
    public void testPopulationCreation()
    {
        reset(populationInstantiator, population, genotypeGenerator, genotype);
        
        prepareCreationExpectations();

        replay(populationInstantiator, population, genotypeGenerator, genotype);

        subject = PopulationBuilder.of(populationInstantiator);
        
        subject
            .size(VALID_POPULATION_SIZE)
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .fitnessFunction(fitnessFunction)
            .genotypeGenerator(genotypeGenerator)
            .build();

    }

    @SuppressWarnings("unchecked")
    private void prepareCreationExpectations() {
        expect(populationInstantiator.instantiate(anyObject(Optional.class))).andReturn(population);
        expect(genotypeGenerator.generate(eq(VALID_GENOTYPE_LENGTH))).andReturn(genotype).times(VALID_POPULATION_SIZE);
        expect(population.add(anyObject(Chromosome.class))).andReturn(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidSize()
    {
        PopulationBuilder.of(populationInstantiator).size(INVALID_SIZE).build();
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidLength()
    {
        PopulationBuilder.of(populationInstantiator).size(INVALID_LENGTH).build();

    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidFitnessFunction()
    {
        PopulationBuilder.of(populationInstantiator)
            .size(VALID_POPULATION_SIZE)
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .genotypeGenerator(genotypeGenerator)
            .build();
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidGenotypeGenerator()
    {
        PopulationBuilder.of(populationInstantiator)
            .size(VALID_POPULATION_SIZE)
            .genotypeLength(VALID_GENOTYPE_LENGTH)
            .fitnessFunction(fitnessFunction)
            .build();
    }
}
