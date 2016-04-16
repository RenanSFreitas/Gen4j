package com.gen4j.fitness;

import com.gen4j.chromosome.Chromosome;

//TODO add an unbounded type parameter to serve as an optional argument
//Why?
public interface FitnessFunction<C extends Chromosome> {

    double evaluate(C chromosome);
}
