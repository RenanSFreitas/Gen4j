package com.gen4j.population;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableMap;

import com.gen4j.genotype.Genotype;
import com.google.common.collect.Iterators;

public final class ImmutablePopulation<G extends Genotype> implements Population<G> {

    private final Population<G> delegate;

    public static <G extends Genotype> Population<G> of(final Population<G> population) {
        return new ImmutablePopulation<>(population);
    }

    private ImmutablePopulation(final Population<G> delegate) {
        this.delegate = requireNonNull(delegate);
    }

    @Override
    public boolean add(final Chromosome<G> chromosome) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final Collection<Chromosome<G>> chromosomes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Chromosome<G> chromosome) {
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
    public NavigableMap<Chromosome<G>, Double> fitness() {
        return delegate.fitness();
    }

    @Override
    public void clearFitness() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Chromosome<G>> iterator() {
        return Iterators.unmodifiableIterator(delegate.iterator());
    }

    @Override
    public Chromosome<G> fittest() {
        return delegate.fittest();
    }
}
