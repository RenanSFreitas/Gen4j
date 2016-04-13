package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.ChromosomeEncoder;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Criteria;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generator.ChromosomeGenerator;

public interface GeneticAlgorithmFactory<G extends Chromosome, V, P> {

    ChromosomeEncoder<G, V> encoder();

    FitnessFunction<G> fitnessFunction();

    Criteria<G> stopCriteria();

    Optional<ChromosomeGenerator<G>> chromosomeGenerator();

    PopulationInstantiator<P, G> populationInstantiator();
}
