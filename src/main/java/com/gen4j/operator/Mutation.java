package com.gen4j.operator;

import com.gen4j.genotype.Genotype;

public interface Mutation<G extends Genotype> extends GeneticOperator {

    double DEFAULT_PROBABILITY = 0.1;

    G mutate(G genotype);
}
