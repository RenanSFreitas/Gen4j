package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Criteria;
import com.gen4j.population.Individual;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generator.ChromosomeGenerator;

public interface GeneticAlgorithmFactory<C extends Chromosome> {

    Individual<C> individual(C chromosome);

    ChromosomeCoder<C> coder();

    FitnessFunction<C> fitnessFunction();

    Criteria<C> stopCriteria();

    Optional<ChromosomeGenerator<C>> chromosomeGenerator();

    PopulationInstantiator<C> populationInstantiator();
}
