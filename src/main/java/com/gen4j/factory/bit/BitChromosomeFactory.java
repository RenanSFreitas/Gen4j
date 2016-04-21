package com.gen4j.factory.bit;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.bit.BitChromosomeCoder;
import com.gen4j.factory.GenericGeneticAlgorithmFactory;
import com.gen4j.population.generator.ChromosomeGenerator;
import com.gen4j.population.generator.RandomBitChromosomeGenerator;

public abstract class BitChromosomeFactory extends GenericGeneticAlgorithmFactory<BitChromosome> {

    private final BitChromosomeCoder coder;

    public BitChromosomeFactory(final BitChromosomeCoder coder) {
        this.coder = requireNonNull(coder);
    }

    @Override
    public final ChromosomeCoder<BitChromosome> coder() {
        return coder;
    }

    @Override
    public Optional<ChromosomeGenerator<BitChromosome>> chromosomeGenerator() {
        return Optional.of(new RandomBitChromosomeGenerator());
    }

}
