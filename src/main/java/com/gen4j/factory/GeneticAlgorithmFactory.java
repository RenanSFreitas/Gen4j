package com.gen4j.factory;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.genotype.GenotypeEncoder;

public interface GeneticAlgorithmFactory<G extends Genotype, K> {

    GenotypeEncoder<G, K> encoder();

    FitnessFunction<G> fitnessFunction();
}
