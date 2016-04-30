package com.gen4j.operator.selection.roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.utils.Pair;

final class Roulette<C extends Chromosome> {

    private final Random random = new Random(System.nanoTime());

    private final List<Pair<Individual<C>, Double>> roulette;

    static <C extends Chromosome> Roulette<C> of(final Population<C> population) {

        final List<Individual<C>> fitness = population.fitness();

        final double maximum = fitness.get(fitness.size() - 1).fitness();
        final double minimum = fitness.get(0).fitness();

        final double maxMinusMin = maximum - minimum;
        final double sumFitness = fitness.stream()
                .mapToDouble(i -> (i.fitness() - minimum) / maxMinusMin)
                .sum();

        final List<Pair<Individual<C>, Double>> roulette = new ArrayList<>(population.size());

        double cumulativeProbability = 0d;
        for (int i = 0; i < fitness.size(); i++) {
            final Individual<C> individual = fitness.get(i);
            cumulativeProbability += ((individual.fitness() - minimum) / maxMinusMin) / sumFitness;
            roulette.add(Pair.of(individual, cumulativeProbability));
        }

        return new Roulette<>(roulette);
    }

    private Roulette(final List<Pair<Individual<C>, Double>> roulette) {
        this.roulette = roulette;
    }

    @SuppressWarnings("null")
    private Individual<C> sortChromosome(final double relativeFitness) {

        Pair<Individual<C>, Double> previous = null;
        for (int i = 0; i < roulette.size(); i++) {
            final Pair<Individual<C>, Double> current = roulette.get(i);
            // compare chromosome relative fitness with the given one
            if (current.second() > relativeFitness) {
                return current.first();
            }

            previous = current;
        }
        return previous.first();
    }

    List<Individual<C>> sortChromosomes(final int count) {
        final List<Individual<C>> chromosomes = new ArrayList<>(count);
        for( int i = 0; i < count; i++ ) {
            chromosomes.add(sortChromosome(random.nextDouble()));
        }
        return chromosomes;
    }
}