package com.gen4j.population;

import java.util.Collection;
import java.util.List;

import com.gen4j.chromosome.Chromosome;

public interface Population<C extends Chromosome> extends Iterable<Individual<C>> {

    boolean add(Individual<C> chromosome);

    boolean addAll(Collection<Individual<C>> chromosomes);

    boolean remove(Individual<C> chromosome);

    int size();

    boolean isEmpty();

    List<Individual<C>> fitness();

    List<Individual<C>> toList();

    void clearFitness();

    Individual<C> fittest();

    PopulationStatistics statistics();
}