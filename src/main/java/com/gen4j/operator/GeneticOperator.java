package com.gen4j.operator;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;

/**
 * Genetic operator interface.
 */
public interface GeneticOperator<C extends Chromosome> {

    /**
     * Returns the probability which this operator will be applied with.
     */
    double probability();

    /**
     * @param probability
     *            the probability which this operator will be applied with
     */
    void probability(double probability);

    int chromosomeCount();

    ChromosomeCodeType chromosomeCodeType();
}
