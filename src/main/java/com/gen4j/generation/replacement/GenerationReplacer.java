package com.gen4j.generation.replacement;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.genetic.algorithm.GeneticAlgorithm;
import com.gen4j.population.Population;

/**
 * Represents a strategy of generation replacement.
 *
 * @param <C>
 *            the specialization of the {@link Chromosome}
 */
public interface GenerationReplacer<C extends Chromosome> {

    /**
     * Returns a population based on the <code>parentsGeneration</code> and the
     * <code>offspringGeneration</code>, a population of individuals assumed to
     * be generated from individuals of the population referenced by the
     * <code>parentsGeneration</code> argument.
     *
     * @param parentsGeneration
     *            a population of individuals, some of which are expected to be
     *            parents of those on <code>offspringGeneration</code>
     * @param offspringGeneration
     *            a population of individuals, expected to be offsprings of
     *            those on <code>offspringGeneration</code>
     * @param factory
     *            a factory of instances of {@link GeneticAlgorithm} related
     *            classes
     * @return a population based on the current <code>generation</code> and the
     *         <code>offspringGeneration</code>
     */
    Population<C> replace(Population<C> parentsGeneration, Population<C> offspringGeneration,
            GeneticAlgorithmFactory<C> factory);
}
