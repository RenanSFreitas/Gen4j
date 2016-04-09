package com.gen4j.operator.selection.roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;
import com.gen4j.utils.Pair;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

final class Roulette<G extends Genotype> {

    private final Random random = new Random(System.nanoTime());

    private final List<Pair<Chromosome<G>, Double>> roulette;

    static <G extends Genotype> Roulette<G> of(final Population<G> population) {

        final NavigableMap<Chromosome<G>, Double> populationFitness = population.fitness();

        final double minimum = populationFitness.firstKey().fitness();
        final double displacement = minimum < 0d ? -minimum : 0d;

        final double totalFitness = populationFitness.values()
                .stream()
                .mapToDouble(d -> d.doubleValue() + displacement)
                .sum();


        final List<Pair<Chromosome<G>, Double>> roulette = new ArrayList<>(population.size());

        double accumulatedFitness = 0d;
        for (final Map.Entry<Chromosome<G>, Double> fitness : populationFitness.descendingMap().entrySet()) {
            // sums currrent relative fitness (displaced)
            accumulatedFitness += (fitness.getValue() + displacement) / totalFitness;
            roulette.add(Pair.of(fitness.getKey(), accumulatedFitness));
        }

        return new Roulette<>(roulette);
    }

    private Roulette(final List<Pair<Chromosome<G>, Double>> roulette) {
        this.roulette = roulette;
    }

    private Chromosome<G> sortChromosome(final double relativeFitness) {

        Pair<Chromosome<G>, Double> previous = null;
        for (final Pair<Chromosome<G>, Double> current : roulette) {
            // compare chromosome relative fitness with the given one
            if (current.second() > relativeFitness) {
                return current.first();
            }

            previous = current;
        }
        return previous.first();
    }

    List<Chromosome<G>> sortChromosomes(final int count) {
        final Builder<Chromosome<G>> chromosomes = ImmutableList.<Chromosome<G>>builder();
        for( int i = 0; i < count; i++ ) {
            chromosomes.add(sortChromosome(random.nextDouble()));
        }
        return chromosomes.build();
    }
}