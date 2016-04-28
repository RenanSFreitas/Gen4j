package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;

public interface Individual<C extends Chromosome> extends Comparable<Individual<C>> {

    C chromosome();

    double fitness();
}
