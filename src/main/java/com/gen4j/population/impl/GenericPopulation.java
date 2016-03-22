package com.gen4j.population.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;

public class GenericPopulation<G extends Genotype> implements Population<G>
{
    private final Set<Chromosome<G>> chromosomes;

    public GenericPopulation(final int size)
    {
        checkArgument(size > 0);
        chromosomes = new HashSet<>(size);
    }

    @Override
    public boolean add(final Chromosome<G> chromosome) {
        return chromosomes.add(chromosome);
    }

    @Override
    public boolean remove(final Chromosome<G> chromosome) {
        return chromosomes.remove(chromosome);
    }

    @Override
    public int size() {
        return chromosomes.size();
    }

    @Override
    public Iterator<Chromosome<G>> iterator() {
        return chromosomes.iterator();
    }
}
