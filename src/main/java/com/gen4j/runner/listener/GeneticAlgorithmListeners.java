/**
 *
 */
package com.gen4j.runner.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NavigableMap;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.runner.GeneticAlgorithmSolution;

public final class GeneticAlgorithmListeners {

    public GeneticAlgorithmListeners() {
        throw new AssertionError("No instances for utility classes.");
    }

    private static abstract class OutputStreamListenerWriter extends AbstractGeneticAlgorithmListener {
        final int regularity;
        final int precision;
        final OutputStream stream;

        private OutputStreamListenerWriter(final int regularity, final int precision, final OutputStream stream) {
            this.regularity = regularity;
            this.precision = precision;
            this.stream = stream;
        }

        @Override
        public void newGeneration(final Population<? extends Chromosome> population, final int generationCount) {
            if (generationCount % regularity != 0) {
                return;
            }

            final byte[] bytes = getBytes(population, generationCount);
            try {
                stream.write(bytes, 0, bytes.length);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void newSolution(final GeneticAlgorithmSolution<? extends Chromosome> solution) {
            final byte[] bytes = getBytes(solution.population(), solution.generationsCount());
            try {
                stream.write(bytes, 0, bytes.length);
                stream.flush();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if(System.out != stream)stream.close();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        abstract byte[] getBytes(final Population<? extends Chromosome> population, final int generationCount);
    }

    public static GeneticAlgorithmListener writeBestFitness(final int regularity, final int precision) {
        return writeBestFitness(regularity, precision, System.out);
    }

    public static GeneticAlgorithmListener writeAverageFitness(final int regularity, final int precision) {
        return writeAverageFitness(regularity, precision, System.out);
    }

    public static GeneticAlgorithmListener writeBestFitness(final int regularity, final int precision,
            final OutputStream stream) {
        return new OutputStreamListenerWriter(regularity, precision, stream) {

            @Override
            byte[] getBytes(final Population<? extends Chromosome> population, final int generationCount) {
                return String.format("fitness = %." + precision + "f\n",
                        Double.valueOf(population.fittest().fitness())).getBytes();
            }
        };
    }

    public static GeneticAlgorithmListener writeAverageFitness(final int regularity, final int precision,
            final OutputStream stream) {
        return new OutputStreamListenerWriter(regularity, precision, stream) {

            @Override
            byte[] getBytes(final Population<? extends Chromosome> population, final int generationCount) {
                double fitness = 0d;
                for (final Individual<? extends Chromosome> individual : population) {
                    fitness += individual.fitness();
                }
                fitness /= population.size();
                return String.format("fitness = %." + precision + "f\n", Double.valueOf(fitness)).getBytes();
            }
        };
    }

    public static GeneticAlgorithmListener writeFitnessStatistics(final int regularity, final int precision) {
        return writeFitnessStatistics(regularity, precision, System.out);
    }

    public static GeneticAlgorithmListener writeFitnessStatistics(final int regularity, final int precision,
            final OutputStream stream) {
        return new OutputStreamListenerWriter(regularity, precision, stream) {

            @Override
            byte[] getBytes(final Population<? extends Chromosome> population, final int generationCount) {
                final NavigableMap<?, Double> fitness = population.fitness();
                double avg = 0d;
                double total = 0d;
                for (final Double val : fitness.values()) {
                    total += val.doubleValue();
                }
                avg = total / fitness.size();
                final Double max = fitness.lastEntry().getValue();
                final Double min = fitness.firstEntry().getValue();

                return String.format("gen=%d total = %." + precision + "f avg = %." + precision + "f max = %."
                        + precision + "f min = %." + precision + "f\n", generationCount, total, avg, max, min)
                        .getBytes();
            }
        };
    }

}
