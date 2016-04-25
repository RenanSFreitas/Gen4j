package com.gen4j.population.generic;

import static java.lang.Double.compare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.PopulationStatistics;

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

    private List<Individual<C>> individuals;
    private final Comparator<? super Individual<C>> fitnessComparator = (c1, c2) -> compare(c1.fitness(), c2.fitness());
    private boolean populationFitness;

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
    public List<Individual<C>> toList() {
        return Collections.unmodifiableList(individuals);
    }

    @Override
    public List<Individual<C>> fitness() {
        // TODO if an individual is modified, this cached map may become invalid
        if (!populationFitness) {
            Collections.sort(individuals, fitnessComparator);
            individuals = Collections.unmodifiableList(individuals);
            populationFitness = true;
        }
        return individuals;
    }

    @Override
    public void clearFitness() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Individual<C> fittest() {
        return fitness().get(individuals.size() - 1);
    }

    @Override
    public PopulationStatistics statistics() {
        return PopulationStatistics.of(this);
    }
}
