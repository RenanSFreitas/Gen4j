package com.gen4j.population;

import java.util.Map;

import com.gen4j.genotype.Genotype;

public interface Population<G extends Genotype> extends Iterable<Chromosome<G>> {

    boolean add(Chromosome<G> chromosome);

    boolean remove(Chromosome<G> chromosome);

    int size();

    Map<Chromosome<G>, Double> calculateFitness();
}