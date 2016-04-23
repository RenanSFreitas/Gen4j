package com.gen4j.population;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableMap;

import com.gen4j.chromosome.Chromosome;
import com.google.common.collect.Iterators;

public final class ImmutablePopulation<G extends Chromosome> implements Population<G> {

    private final Population<G> delegate;

    public static <G extends Chromosome> Population<G> of(final Population<G> population) {

        if (population instanceof ImmutablePopulation) {
            return population;
        }

        return new ImmutablePopulation<>(population);
    }

    private ImmutablePopulation(final Population<G> delegate) {
        this.delegate = requireNonNull(delegate);
    }

    @Override
    public boolean add(final Individual<G> chromosome) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final Collection<Individual<G>> chromosomes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Individual<G> chromosome) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public NavigableMap<Individual<G>, Double> fitness() {
        return delegate.fitness();
    }

    @Override
    public void clearFitness() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Individual<G>> iterator() {
        return Iterators.unmodifiableIterator(delegate.iterator());
    }

    @Override
    public Individual<G> fittest() {
        return delegate.fittest();
    }

    @Override
    public PopulationStatistics statistics() {
        return delegate.statistics();
    }
}
