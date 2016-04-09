package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.genotype.GenotypeEncoder;
import com.gen4j.population.Criteria;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generator.RandomGenotypeGenerator;

public interface GeneticAlgorithmFactory<G extends Genotype, V, P> {

    GenotypeEncoder<G, V> encoder();

    FitnessFunction<G> fitnessFunction();

    Criteria<G> stopCriteria();

    Optional<RandomGenotypeGenerator<G>> genotypeGenerator();

    PopulationInstantiator<P, G> populationInstantiator();
}
