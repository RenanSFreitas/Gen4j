package com.gen4j.population.generic;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Double.compare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;

//TODO junit
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

    private final List<Chromosome<G>> chromosomes;
    private final Comparator<? super Chromosome<G>> fitnessComparator = (c1, c2) -> compare(c1.fitness(), c2.fitness());
    private NavigableMap<Chromosome<G>, Double> populationFitness;

    public GenericPopulation(final int size)
    {
        checkArgument(size > -1);
        chromosomes = new ArrayList<>(size);
    }

    @Override
    public boolean add(final Chromosome<G> chromosome) {
        return chromosomes.add(chromosome);
    }

    @Override
    public boolean addAll(final Collection<Chromosome<G>> chromosomes) {
        return this.chromosomes.addAll(chromosomes);
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
    public NavigableMap<Chromosome<G>, Double> fitness() {
        if (populationFitness == null) {
            populationFitness = new TreeMap<>(fitnessComparator);
            chromosomes.forEach(c -> populationFitness.put(c, c.fitness()));
        }
        return Collections.unmodifiableNavigableMap(populationFitness);
    }

    @Override
    public void clearFitness() {
        populationFitness = null;
    }

    @Override
    public Chromosome<G> fittest() {
        return fitness().lastKey();
    }
}
