package com.gen4j.operator;

import java.util.Collection;

import com.gen4j.chromosome.Chromosome;

public interface GeneticOperator<C extends Chromosome> {

    double probability();

    void probability(double probability);

    Collection<C> apply(Collection<C> chromosomes);

    int chromosomeCount();
}
