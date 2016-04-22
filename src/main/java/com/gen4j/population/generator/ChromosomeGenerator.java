package com.gen4j.population.generator;

import com.gen4j.chromosome.Chromosome;

public interface ChromosomeGenerator<C extends Chromosome>
{
    C generate(int length);
}
