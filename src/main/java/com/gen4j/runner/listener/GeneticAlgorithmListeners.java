/**
 *
 */
package com.gen4j.runner.listener;

import java.io.IOException;
import java.io.OutputStream;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.genetic.algorithm.GeneticAlgorithmSolution;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationStatistics;

public final class GeneticAlgorithmListeners {

    public GeneticAlgorithmListeners() {
        throw new AssertionError("No instances for utility classes.");
    }

    private static abstract class OutputStreamListenerWriter<C extends Chromosome>
            extends AbstractGeneticAlgorithmListener<C> {
        final int regularity;
        final int precision;
        final OutputStream stream;

        private OutputStreamListenerWriter(final int regularity, final int precision, final OutputStream stream) {
            this.regularity = regularity;
            this.precision = precision;
            this.stream = stream;
        }

        @Override
        public void newGeneration(final Population<C> population, final int generationCount,
                final Individual<C> fittest) {
            if (generationCount % regularity != 0) {
                return;
            }

            final byte[] bytes = getBytes(population, generationCount, fittest);
            try {
                stream.write(bytes, 0, bytes.length);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void newSolution(final GeneticAlgorithmSolution<C> solution, final Individual<C> fittest) {
            final byte[] bytes = getBytes(solution.population(), solution.generationsCount(), fittest);
            try {
                stream.write(bytes, 0, bytes.length);
                stream.flush();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if(System.out != stream) {
                        stream.close();
                    }
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        abstract byte[] getBytes(final Population<C> population, final int generationCount, Individual<C> fittest);
    }

    public static <C extends Chromosome> GeneticAlgorithmListener<C> writeBestFitness(final int regularity,
            final int precision) {
        return writeBestFitness(regularity, precision, System.out);
    }

    public static <C extends Chromosome> GeneticAlgorithmListener<C> writeAverageFitness(final int regularity,
            final int precision) {
        return writeAverageFitness(regularity, precision, System.out);
    }

    public static <C extends Chromosome> GeneticAlgorithmListener<C> writeBestFitness(final int regularity,
            final int precision,
            final OutputStream stream) {
        return new OutputStreamListenerWriter<C>(regularity, precision, stream) {

            @Override
            byte[] getBytes(final Population<C> population, final int generationCount, final Individual<C> fittest) {

                return String.format("fitness = %." + precision + "f\n",
                        Double.valueOf(population.fittest().fitness())).getBytes();
            }
        };
    }

    public static <C extends Chromosome> GeneticAlgorithmListener<C> writeAverageFitness(final int regularity,
            final int precision,
            final OutputStream stream) {
        return new OutputStreamListenerWriter<C>(regularity, precision, stream) {

            @Override
            byte[] getBytes(final Population<C> population, final int generationCount, final Individual<C> fittest) {
                double fitness = 0d;
                for (final Individual<C> individual : population) {
                    fitness += individual.fitness();
                }
                fitness /= population.size();
                return String.format("fitness = %." + precision + "f\n", Double.valueOf(fitness)).getBytes();
            }
        };
    }

    public static <C extends Chromosome> GeneticAlgorithmListener<C> writeFitnessStatistics(final int regularity,
            final int precision) {
        return writeFitnessStatistics(regularity, precision, System.out);
    }

    public static <C extends Chromosome> GeneticAlgorithmListener<C> writeFitnessStatistics(final int regularity,
            final int precision,
            final OutputStream stream) {
        return new OutputStreamListenerWriter<C>(regularity, precision, stream) {

            @Override
            byte[] getBytes(final Population<C> population, final int generationCount, final Individual<C> fittest) {

                final PopulationStatistics statistics = population.statistics();
                final Double avg = Double.valueOf(statistics.meanFitness());
                final Double total = Double.valueOf(statistics.totalFitness());
                final Double max = Double.valueOf(statistics.maxFitness());
                final Double min = Double.valueOf(statistics.minFitness());
                final Double med = Double.valueOf(statistics.medianFitness());

                return String.format(
                        "gen=%d "
                        + "tot = %." + precision + "f "
                        + "avg = %." + precision + "f "
                        + "med = %." + precision + "f "
                        + "max = %." + precision + "f "
                        + "min = %." + precision + "f "
                        + "fit = %." + precision + "f\n",
                        generationCount, total, avg, med, max, min, fittest.fitness())
                        .getBytes();
            }
        };
    }

}
