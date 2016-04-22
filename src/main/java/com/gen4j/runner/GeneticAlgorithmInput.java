package com.gen4j.runner;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Criteria;

public interface GeneticAlgorithmInput<G extends Chromosome> {

    Criteria<G> stopCriteria();

    FitnessFunction fitnessFunction();
}
