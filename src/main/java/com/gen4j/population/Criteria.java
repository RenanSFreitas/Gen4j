package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;

public interface Criteria<G extends Chromosome> {

    boolean apply(Population<G> population, int generation);
}
