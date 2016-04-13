package com.gen4j.population.generator;

import com.gen4j.chromosome.Chromosome;

public interface ChromosomeGenerator<G extends Chromosome>
{
    G generate(int length);
}
