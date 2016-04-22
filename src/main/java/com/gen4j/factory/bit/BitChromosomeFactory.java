package com.gen4j.factory.bit;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.bit.BitChromosomeCoder;
import com.gen4j.factory.GenericGeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Criteria;
import com.gen4j.population.generator.ChromosomeGenerator;
import com.gen4j.population.generator.RandomBitChromosomeGenerator;

public class BitChromosomeFactory extends GenericGeneticAlgorithmFactory<BitChromosome> {

    private final BitChromosomeCoder coder;
    private final FitnessFunction<BitChromosome> fitnessFunction;
    private final Criteria<BitChromosome> stopCriteria;

    public BitChromosomeFactory(final BitChromosomeCoder coder, final FitnessFunction<BitChromosome> fitnessFunction,
            final Criteria<BitChromosome> stopCriteria) {
        this.coder = requireNonNull(coder);
        this.fitnessFunction = requireNonNull(fitnessFunction);
        this.stopCriteria = requireNonNull(stopCriteria);
    }

    @Override
    public final ChromosomeCoder<BitChromosome> coder() {
        return coder;
    }

    @Override
    public Optional<ChromosomeGenerator<BitChromosome>> chromosomeGenerator() {
        return Optional.of(new RandomBitChromosomeGenerator());
    }

    @Override
    public FitnessFunction<BitChromosome> fitnessFunction() {
        return fitnessFunction;
    }

    @Override
    public Criteria<BitChromosome> stopCriteria() {
        return stopCriteria;
    }

}
