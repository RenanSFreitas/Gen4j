package com.gen4j.population;

import java.util.Collection;
import java.util.NavigableMap;

import com.gen4j.genotype.Genotype;

public interface Population<G extends Genotype> extends Iterable<Chromosome<G>> {

    boolean add(Chromosome<G> chromosome);

    boolean addAll(Collection<Chromosome<G>> chromosomes);

    boolean remove(Chromosome<G> chromosome);

    int size();

    boolean isEmpty();

    NavigableMap<Chromosome<G>, Double> fitness();

    void clearFitness();

    Chromosome<G> fittest();
}