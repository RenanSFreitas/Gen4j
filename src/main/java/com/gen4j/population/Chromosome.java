package com.gen4j.population;

import com.gen4j.genotype.Genotype;

public interface Chromosome<G extends Genotype> {

    G genotype();

    double fitness();
}
