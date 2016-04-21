package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;

public interface PopulationInstantiator<C extends Chromosome>
{
    Population<C> instantiate();
}
