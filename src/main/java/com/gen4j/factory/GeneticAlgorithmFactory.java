package com.gen4j.factory;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.genotype.GenotypeEncoder;
import com.gen4j.operator.selection.Selection;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generator.RandomGenotypeGenerator;

public interface GeneticAlgorithmFactory<G extends Genotype, V, P> {

    GenotypeEncoder<G, V> encoder();

    FitnessFunction<G> fitnessFunction();

    Selection<G> selector();

    RandomGenotypeGenerator<G> genotypeGenerator();

    PopulationInstantiator<P, G> populationInstantiator();
}
