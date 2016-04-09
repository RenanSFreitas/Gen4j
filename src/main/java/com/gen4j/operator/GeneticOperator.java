package com.gen4j.operator;

import java.util.Collection;

public interface GeneticOperator<G> {

    double probability();

    void probability(double probability);

    Collection<G> apply(Collection<G> genotypes);

    int chromosomeCount();
}
