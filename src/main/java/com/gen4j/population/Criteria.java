package com.gen4j.population;

import com.gen4j.genotype.Genotype;

public interface Criteria<G extends Genotype> {

    boolean apply(Population<G> population, int generation);
}
