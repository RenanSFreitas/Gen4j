package com.gen4j.factory.bit;

import java.util.Optional;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.factory.CustomGeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Criteria;
import com.gen4j.population.generator.ChromosomeGenerator;
import com.gen4j.population.generator.RandomBitChromosomeGenerator;

/**
 * Factory of chromosomes of bit valued genes.
 */
public class BitChromosomeFactory extends CustomGeneticAlgorithmFactory<BitChromosome> {

    public BitChromosomeFactory(
            final ChromosomeCoder<BitChromosome> coder,
            final FitnessFunction fitnessFunction,
            final Criteria<BitChromosome> criteria) {
        super(coder, fitnessFunction, criteria);
    }

    @Override
    public Optional<ChromosomeGenerator<BitChromosome>> chromosomeGenerator() {
        return Optional.of(new RandomBitChromosomeGenerator());
    }

}
