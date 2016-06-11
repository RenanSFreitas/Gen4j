/**
 *
 */
package com.gen4j.factory.fp;

import java.util.Optional;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.CustomGeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Criteria;
import com.gen4j.population.generator.ChromosomeGenerator;
import com.gen4j.population.generator.RandomFloatingPointChromosomeGenerator;

/**
 * Factory of chromosomes of real valued genes.
 *
 */
public class FloatingPointChromosomeFactory extends CustomGeneticAlgorithmFactory<FloatingPointChromosome> {

    private final Optional<ChromosomeGenerator<FloatingPointChromosome>> generator;

    public FloatingPointChromosomeFactory(
            final ChromosomeCoder<FloatingPointChromosome> coder,
            final FitnessFunction fitnessFunction,
            final Criteria<FloatingPointChromosome> criteria,
            final ChromosomeGenerator<FloatingPointChromosome> generator)
    {
        super(coder, fitnessFunction, criteria);
        this.generator = Optional.of(generator);
    }

    public FloatingPointChromosomeFactory(
            final ChromosomeCoder<FloatingPointChromosome> coder,
            final FitnessFunction fitnessFunction,
            final Criteria<FloatingPointChromosome> criteria) {

        super(coder, fitnessFunction, criteria);
        generator = Optional.of(new RandomFloatingPointChromosomeGenerator(coder.ranges()));
    }

    @Override
    public Optional<ChromosomeGenerator<FloatingPointChromosome>> chromosomeGenerator() {
        return generator;
    }
}
