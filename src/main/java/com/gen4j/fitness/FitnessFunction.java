package com.gen4j.fitness;

import com.gen4j.genotype.Genotype;

//TODO add an unbounded type parameter to serve as an optional argument
public interface FitnessFunction<G extends Genotype> {

    double evaluate(G genotype);
}
