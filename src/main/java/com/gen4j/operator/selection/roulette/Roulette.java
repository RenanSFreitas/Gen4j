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

        final List<Individual<C>> populationFitness = population.fitness();

        final double maximum = populationFitness.get(populationFitness.size()-1).fitness();
        final int selectionPressure = -8;
//        final double minimum = populationFitness.get(0).fitness();

        final double sumFitness = populationFitness
                .stream()
                .mapToDouble(i -> Math.exp(selectionPressure * i.fitness() / maximum))
//                .mapToDouble(i -> i.fitness() + minimum)
                .sum();


        final List<Pair<Individual<C>, Double>> roulette = new ArrayList<>(population.size());

        double accumulatedFitness = 0d;
        for (final Individual<C> individual : populationFitness) {
            // sums currrent relative fitness (displaced)
            accumulatedFitness += Math.exp(selectionPressure * individual.fitness() / maximum) / sumFitness;
//            accumulatedFitness += (individual.fitness() + minimum) / sumFitness;
            roulette.add(Pair.of(individual, accumulatedFitness));
        }

        return new Roulette<>(roulette);
    }

    private Roulette(final List<Pair<Individual<C>, Double>> roulette) {
        this.roulette = roulette;
    }

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