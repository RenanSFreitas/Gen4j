package com.gen4j.factory.bit;

import java.util.Optional;

import com.gen4j.factory.GenericGeneticAlgorithmFactory;
import com.gen4j.genotype.bit.BitSetGenotype;
import com.gen4j.population.generator.BitSetGenotypeGenerator;
import com.gen4j.population.generator.RandomGenotypeGenerator;

public abstract class BitSetGeneticAlgorithmFactory extends GenericGeneticAlgorithmFactory<BitSetGenotype, String> {

    @Override
    public Optional<RandomGenotypeGenerator<BitSetGenotype>> genotypeGenerator() {
        return Optional.of(new BitSetGenotypeGenerator());
    }

}
