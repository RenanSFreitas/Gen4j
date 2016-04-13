package com.gen4j.population;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;

public interface PopulationInstantiator<P, G extends Chromosome>
{
    Population<G> instantiate(Optional<P> parameter);
}
