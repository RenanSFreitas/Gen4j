package com.gen4j.operator.selection.roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.utils.Pair;

final class Roulette<C extends Chromosome> {

    private final Random random = new Random(System.nanoTime());

    private final List<Pair<Individual<C>, Double>> roulette;

    static <G extends Chromosome> Roulette<G> of(final Population<G> population) {

        final NavigableMap<Individual<G>, Double> populationFitness = population.fitness();

        final double minimum = populationFitness.firstKey().fitness();

        // Displacement to ensure relative fitness belongs to [0;1]
        final double displacement = minimum < 0d ? -minimum : 0d;

        final double totalFitness = populationFitness.values()
                .stream()
                .mapToDouble(d -> d.doubleValue() + displacement)
                .sum();


        final List<Pair<Individual<G>, Double>> roulette = new ArrayList<>(population.size());

        double accumulatedFitness = 0d;
        for (final Map.Entry<Individual<G>, Double> fitness : populationFitness.descendingMap().entrySet()) {
            // sums currrent relative fitness (displaced)
            accumulatedFitness += (fitness.getValue() + displacement) / totalFitness;
            roulette.add(Pair.of(fitness.getKey(), accumulatedFitness));
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