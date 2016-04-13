package com.gen4j.fitness;

import com.gen4j.chromosome.Chromosome;

//TODO add an unbounded type parameter to serve as an optional argument
public interface FitnessFunction<G extends Chromosome> {

    double evaluate(G chromosome);
}
