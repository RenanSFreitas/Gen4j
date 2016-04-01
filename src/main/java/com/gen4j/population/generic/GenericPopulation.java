package com.gen4j.population.generic;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;

public class GenericPopulation<G extends Genotype> implements Population<G>
{
    private static class GenericPopulationInstatiator<G extends Genotype>
            implements PopulationInstantiator<Integer, G> {

        @Override
        public Population<G> instantiate(final Optional<Integer> parameter) {
            return new GenericPopulation<>(parameter.orElse(0));
        }
    }

    public static <G extends Genotype> PopulationInstantiator<Integer, G> intantiator() {
        return new GenericPopulationInstatiator<>();
    }

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
    public boolean isEmpty() {
        return chromosomes.isEmpty();
    }

    @Override
    public Iterator<Chromosome<G>> iterator() {
        return chromosomes.iterator();
    }

    @Override
    public Set<Chromosome<G>> set() {
        return Collections.unmodifiableSet(chromosomes);
    }
}
