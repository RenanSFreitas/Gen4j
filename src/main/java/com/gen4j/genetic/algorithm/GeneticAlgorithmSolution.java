/**
 *
 */
package com.gen4j.genetic.algorithm;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.Populations;

/**
 * Final product of a {@link GeneticAlgorithm} evolution. Represents the best
 * solution found by the algorithm.
 *
 * @param <C>
 *            the specialization of the {@link Chromosome}
 */
/**
 *
 * @param <C>
 */
public final class GeneticAlgorithmSolution<C extends Chromosome> {

    private final Population<C> population;
    private final int generationsCount;
    private final Individual<C> fittest;
    private final ChromosomeCodeType codeType;
    private final boolean converged;

    /**
     * Builder class for {@link GeneticAlgorithmSolution}s.
     */
    public static class Builder<C extends Chromosome> {
        private Population<C> population;
        private int generationsCount = -1;
        private Individual<C> fittest;
        private ChromosomeCodeType codeType;

        public Builder<C> population(final Population<C> population) {
            this.population = population;
            return this;
        }

        public Builder<C> generationsCount(final int generationsCount) {
            this.generationsCount = generationsCount;
            return this;
        }

        public Builder<C> fittest(final Individual<C> fittest) {
            this.fittest = fittest;
            return this;
        }

        public Builder<C> codeType(final ChromosomeCodeType codeType) {
            this.codeType = codeType;
            return this;
        }

        public GeneticAlgorithmSolution<C> build() {

            checkState(generationsCount >= 0);
            checkState(population != null);
            checkState(generationsCount > -1);
            checkState(fittest != null);
            checkState(codeType != null);

            return new GeneticAlgorithmSolution<>(population, generationsCount, fittest, codeType);
        }
    }

    public static <C extends Chromosome> Builder<C> builder() {
        return new Builder<>();
    }

    private GeneticAlgorithmSolution(final Population<C> population, final int generationsCount,
            final Individual<C> fittest, final ChromosomeCodeType codeType) {

        checkArgument(generationsCount >= 0);

        this.population = requireNonNull(population);
        this.generationsCount = generationsCount;
        this.fittest = requireNonNull(fittest);
        this.codeType = requireNonNull(codeType);
        this.converged = Populations.converged(population);
    }

    /**
     * @return the generations count before the algorithm stopped
     */
    public int generationsCount() {
        return generationsCount;
    }

    /**
     * @return the last generation before the algorithm stopped
     */
    public Population<C> population() {
        return population;
    }

    /**
     * @return the fittest individual found
     */
    public Individual<C> fittest() {
        return fittest;
    }

    /**
     * @return the chromsome code type used
     */
    public ChromosomeCodeType codeType() {
        return codeType;
    }

    /**
     * @return <code>true</code>, in case the algorithm converged before
     *         stopping
     */
    public boolean converged() {
        return converged;
    }
}
