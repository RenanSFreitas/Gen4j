package com.gen4j.population;

import java.util.Optional;

import com.gen4j.genotype.Genotype;

public interface PopulationInstantiator<P, G extends Genotype>
{
    Population<G> instantiate(Optional<P> parameter);
}
