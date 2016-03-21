package com.gen4j.fitness;

import com.gen4j.genotype.Genotype;

public interface FitnessFunction<G extends Genotype> {

    double evaluate(G genotype);
}
