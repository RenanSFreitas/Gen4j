package com.gen4j.factory.bit;

import java.util.Optional;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.factory.GenericGeneticAlgorithmFactory;
import com.gen4j.population.generator.RandomBitChromosomeGenerator;
import com.gen4j.population.generator.ChromosomeGenerator;

public abstract class BitSetGeneticAlgorithmFactory extends GenericGeneticAlgorithmFactory<BitChromosome, String> {

    @Override
    public Optional<ChromosomeGenerator<BitChromosome>> chromosomeGenerator() {
        return Optional.of(new RandomBitChromosomeGenerator());
    }

}
