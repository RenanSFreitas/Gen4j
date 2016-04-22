package com.gen4j.fitness;

import com.gen4j.phenotype.Phenotype;

//TODO add an unbounded type parameter to serve as an optional argument
//Why?
public interface FitnessFunction {

    double evaluate(Phenotype phenotype);
}
