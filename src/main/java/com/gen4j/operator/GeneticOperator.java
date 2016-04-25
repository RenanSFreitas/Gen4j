package com.gen4j.operator;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;

public interface GeneticOperator<C extends Chromosome> {

    double probability();

    void probability(double probability);

    ChromosomeCodeType chromosomeCodeType();
}
