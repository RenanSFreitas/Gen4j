package com.gen4j.chromosome;

import com.gen4j.genetic.algorithm.GeneticAlgorithm;
import com.gen4j.population.Individual;

/**
 * The smallest unit the {@link GeneticAlgorithm} deals with, indirectly.
 * <p>
 * It's a representation of an {@link Individual}'s genetic material.
 */
public interface Chromosome {

    /**
     * Returns the value of this chromosome, the genetic material associated.
     */
    Object value();

    /**
     * Returns the length of this chromosome.
     */
    int length();
}
