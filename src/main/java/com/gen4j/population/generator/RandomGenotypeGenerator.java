package com.gen4j.population.generator;

import com.gen4j.genotype.Genotype;

public interface RandomGenotypeGenerator<G extends Genotype>
{
    G generate(int length);
}
