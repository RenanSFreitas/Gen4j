package com.gen4j.population;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.population.generator.RandomGenotypeGenerator;
import com.gen4j.population.impl.ChromosomeImpl;

public final class PopulationBuilder<P, G extends Genotype>
{

    private int size;
    private int chromosomeLength;
    private RandomGenotypeGenerator<G> genotypeGenerator;
    private FitnessFunction<G> fitnessFunction;

    private PopulationInstantiator<P, G> populationInstantiator;
    private Optional<P> populationInstantiatorParameter = Optional.empty();

    private PopulationBuilder(PopulationInstantiator<P, G> populationInstantiator)
    {
        this.populationInstantiator = requireNonNull(populationInstantiator);
    }

    public static <P, G extends Genotype> PopulationBuilder<P, G> of(PopulationInstantiator<P, G> populationInstantiator)
    {
        return new PopulationBuilder<>(populationInstantiator);
    }

    public PopulationBuilder<P, G> constructorParameter(P parameter)
    {
        populationInstantiatorParameter = Optional.of(parameter);
        return this;
    }

    public PopulationBuilder<P, G> fitnessFunction(FitnessFunction<G> fitnessFunction)
    {
        this.fitnessFunction = fitnessFunction;
        return this;
    }

    public PopulationBuilder<P, G> size(int size)
    {
        this.size = size;
        return this;
    }

    public PopulationBuilder<P, G> genotypeLength(int length)
    {
        this.chromosomeLength = length;
        return this;
    }

    public PopulationBuilder<P, G> genotypeGenerator(RandomGenotypeGenerator<G> genotypeGenerator)
    {
        this.genotypeGenerator = genotypeGenerator;
        return this;
    }

    public Population<G> build()
    {
        checkState();
        Population<G> population = newPopulationInstance();
        for (int i = 0; i < size; i++)
        {
            boolean added = population.add(new ChromosomeImpl<>(newGenotype(), fitnessFunction));
            
            if (!added) i--;
        }
        return population;
    }

    private G newGenotype()
    {
        return genotypeGenerator.generate(chromosomeLength);
    }

    private Population<G> newPopulationInstance()
    {
        return populationInstantiator.instantiate(populationInstantiatorParameter);
    }

    private void checkState()
    {
        List<String> errors = new ArrayList<>();
        checkState(errors);
        if (!errors.isEmpty())
        {
            throw new IllegalStateException(errors.stream().collect(joining("\n")));
        }
    }

    private void checkState(List<String> errorMessages)
    {
        check(size > 0, "Population size should be greater than zero.", errorMessages);
        check(chromosomeLength > 0, "Chromosome length should be greater than zero.", errorMessages);
        check(genotypeGenerator != null, "Genotype generator must be set.", errorMessages);
        check(fitnessFunction != null, "Fitness function must be set.", errorMessages);
        check(populationInstantiator != null, "Population instantiator must be set.", errorMessages);
    }

    private void check(boolean expression, String errorMessage, List<String> errorMessages)
    {
        if (!expression)
        {
            errorMessages.add(errorMessage);
        }
    }

}
