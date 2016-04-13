package com.gen4j.population;

import java.util.Collection;
import java.util.NavigableMap;

import com.gen4j.chromosome.Chromosome;

public interface Population<G extends Chromosome> extends Iterable<Individual<G>> {

    boolean add(Individual<G> chromosome);

    boolean addAll(Collection<Individual<G>> chromosomes);

    boolean remove(Individual<G> chromosome);

    int size();

    boolean isEmpty();

    NavigableMap<Individual<G>, Double> fitness();

    void clearFitness();

    Individual<G> fittest();
}