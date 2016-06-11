package com.gen4j.fitness;

import com.gen4j.genetic.algorithm.GeneticAlgorithm;
import com.gen4j.phenotype.Phenotype;

/**
 * {@link GeneticAlgorithm}'s fitness function representation. An abstraction
 * for a function that maps a given {@link Phenotype} into a real valued number.
 */
public interface FitnessFunction {

    /**
     * Evaluates the fitness of a given {@link Phenotype}. The fitness measure
     * is a representation of the correspondent genetic material quality.
     * Usually, it's directly proportional to the chance of survival of the
     * genetic material owner.
     *
     * @param phenotype
     *            the phenotype of a genetic material
     * @return the fitness measure
     */
    double evaluate(Phenotype phenotype);
}
