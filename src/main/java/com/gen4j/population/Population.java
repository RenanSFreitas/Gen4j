package com.gen4j.population;

import java.util.Set;

import com.gen4j.genotype.Genotype;

public interface Population<G extends Genotype> extends Iterable<Chromosome<G>> {

    boolean add(Chromosome<G> chromosome);

    boolean remove(Chromosome<G> chromosome);

    int size();

    boolean isEmpty();

    Set<Chromosome<G>> set();
}