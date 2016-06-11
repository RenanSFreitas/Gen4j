package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genetic.algorithm.GeneticAlgorithm;
import com.gen4j.population.Criteria;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generator.ChromosomeGenerator;

/**
 * A factory of instances of chromosome specific/related classes that are
 * relevant to the {@link GeneticAlgorithm}.
 *
 * @param <C>
 *            the specialization of the {@link Chromosome}
 */
public interface GeneticAlgorithmFactory<C extends Chromosome> {

    /**
     * Creates an instance of an individual containing the genetic material of
     * <code>chromosome</code>.
     *
     * @param chromosome
     *            the genetic material to be used
     * @return an instance of an individual containing the genetic material of
     *         <code>chromosome</code>.
     */
    Individual<C> individual(C chromosome);

    /**
     * Creates an instance of a coder for this chromosome specialization.
     */
    ChromosomeCoder<C> coder();

    /**
     * Returns the fitness function to be used when evaluating the individuals.
     */
    FitnessFunction fitnessFunction();

    /**
     * Returns the {@link GeneticAlgorithm} stop condition.
     */
    Criteria<C> stopCriteria();


    /**
     * Returns an optional generator of chromsomes.
     */
    Optional<ChromosomeGenerator<C>> chromosomeGenerator();

    /**
     * Returns an instantiator of {@link Population}s.
     */
    PopulationInstantiator<C> populationInstantiator();
}
