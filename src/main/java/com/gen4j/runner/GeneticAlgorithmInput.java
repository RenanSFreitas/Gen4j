package com.gen4j.runner;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.population.Criteria;

public interface GeneticAlgorithmInput<G extends Genotype> {

    Criteria<G> stopCriteria();

    FitnessFunction<G> fitnessFunction();
}
