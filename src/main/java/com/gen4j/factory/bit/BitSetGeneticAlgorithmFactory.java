package com.gen4j.factory.bit;

import com.gen4j.factory.GenericGeneticAlgorithmFactory;
import com.gen4j.genotype.bit.BitSetGenotype;
import com.gen4j.population.generator.BitSetGenotypeGenerator;
import com.gen4j.population.generator.RandomGenotypeGenerator;

public abstract class BitSetGeneticAlgorithmFactory extends GenericGeneticAlgorithmFactory<BitSetGenotype, String> {

    @Override
    public RandomGenotypeGenerator<BitSetGenotype> genotypeGenerator() {
        return new BitSetGenotypeGenerator();
    }

}
