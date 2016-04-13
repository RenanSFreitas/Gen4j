package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;

public interface Individual<C extends Chromosome> {

    C chromosome();

    double fitness();
}
