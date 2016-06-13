package com.gen4j.genetic.algorithm;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.generation.replacement.GenerationReplacer;
import com.gen4j.operator.CrossOver;
import com.gen4j.operator.Mutation;
import com.gen4j.operator.post.GeneticOperatorsPostProcessor;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Criteria;
import com.gen4j.population.Population;
import com.gen4j.runner.listener.GeneticAlgorithmListener;

/**
 * Genetic algorithm main interface.
 *
 * @param <C>
 *            the specialization of the {@link Chromosome}
 */
public interface GeneticAlgorithm<C extends Chromosome> {

    /**
     * Returns an instance of a Genetic Algorithm based on the given mandatory
     * parameters.
     *
     * @param selector
     *            an individual selection strategy
     * @param crossOver
     *            the cross over operator to be used during the algorithm
     *            evolution
     * @param mutation
     *            the mutation operator to be used during the algorithm
     *            evolution
     * @return an instance of a Genetic algorithm
     */
    static <C extends Chromosome> GeneticAlgorithm<C> create(final Selector<C> selector,
            final CrossOver<C> crossOver, final Mutation<C> mutation) {
        return new GeneticAlgorithmImpl<>(selector, crossOver, mutation);
    }

    /**
     * Performs the evolution of the initial population, based on previously
     * defined parameters and on the {@link GeneticAlgorithmFactory} supplied.
     * <p>
     * The evolution iterations are executed until the {@link Criteria stop
     * criteria} is satisfied.
     *
     * @param population
     *            the initial population
     * @param factory
     *            a factory of genetic algorithm related classes
     * @return the solution found during the Genetic Algorithm execution
     */
    GeneticAlgorithmSolution<C> evolve(Population<C> population, GeneticAlgorithmFactory<C> factory);

    void addListener(GeneticAlgorithmListener<C> listener);

    void removeListener(GeneticAlgorithmListener<C> listener);

    void addGeneticOperatorsPostProcessor(GeneticOperatorsPostProcessor<C> postProcessor);

    void removeGeneticOperatorsPostProcessor(GeneticOperatorsPostProcessor<C> postProcessor);

    void setElitismCount(int elitismCount);

    void setPopulationExchanger(GenerationReplacer<C> exchanger);

    void clearListeners();

}
