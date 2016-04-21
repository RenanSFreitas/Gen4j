package com.gen4j.population.generic;

import static java.lang.Double.compare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;

//TODO junit
public class GenericPopulation<C extends Chromosome> implements Population<C>
{
    private static class GenericPopulationInstatiator<C extends Chromosome> implements PopulationInstantiator<C> {

        @Override
        public Population<C> instantiate() {
            return new GenericPopulation<>();
        }
    }

    public static <C extends Chromosome> PopulationInstantiator<C> intantiator() {
        return new GenericPopulationInstatiator<>();
    }

    private final List<Individual<C>> individuals;
    private final Comparator<? super Individual<C>> fitnessComparator = (c1, c2) -> compare(c1.fitness(), c2.fitness());
    private NavigableMap<Individual<C>, Double> populationFitness;

    public GenericPopulation()
    {
        individuals = new ArrayList<>(50);
    }

    @Override
    public boolean add(final Individual<C> chromosome) {
        return individuals.add(chromosome);
    }

    @Override
    public boolean addAll(final Collection<Individual<C>> chromosomes) {
        return this.individuals.addAll(chromosomes);
    }

    @Override
    public boolean remove(final Individual<C> chromosome) {
        return individuals.remove(chromosome);
    }

    @Override
    public int size() {
        return individuals.size();
    }

    @Override
    public boolean isEmpty() {
        return individuals.isEmpty();
    }

    @Override
    public Iterator<Individual<C>> iterator() {
        return individuals.iterator();
    }

    @Override
    public NavigableMap<Individual<C>, Double> fitness() {
        if (populationFitness == null) {
            populationFitness = new TreeMap<>(fitnessComparator);
            for (final Individual<C> individual : individuals) {
                populationFitness.put(individual, individual.fitness());
            }
        }
        return Collections.unmodifiableNavigableMap(populationFitness);
    }

    @Override
    public void clearFitness() {
        populationFitness = null;
    }

    @Override
    public Individual<C> fittest() {
        return fitness().lastKey();
    }
}
