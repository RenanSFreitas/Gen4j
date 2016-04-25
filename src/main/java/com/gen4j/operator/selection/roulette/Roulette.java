package com.gen4j.operator.selection.roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.utils.Pair;

final class Roulette<C extends Chromosome> {

    private final Random random = new Random(System.nanoTime());

    private final List<Pair<Individual<C>, Double>> roulette;

    static <C extends Chromosome> Roulette<C> of(final Population<C> population) {

        final List<Individual<C>> populationFitness = population.fitness();

        final double maximum = populationFitness.get(populationFitness.size() - 1).fitness();

        // Displacement to ensure relative fitness belongs to [0;1]
        final double sumFitness = populationFitness
                .stream()
                .mapToDouble(i -> Math.exp(-8 * i.fitness() / maximum))
                .sum();


        final List<Pair<Individual<C>, Double>> roulette = new ArrayList<>(population.size());

        double accumulatedFitness = 0d;
        for (final Individual<C> individual : populationFitness) {
            // sums current relative fitness (displaced)
            accumulatedFitness += Math.exp(-8 * individual.fitness() / maximum) / sumFitness;
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

    public Individual<C> sortChromosome() {
        return sortChromosome(random.nextDouble());
    }

    List<Individual<C>> sortChromosomes(final int count) {
        final List<Individual<C>> chromosomes = new ArrayList<>(count);
        for( int i = 0; i < count; i++ ) {
            chromosomes.add(sortChromosome(random.nextDouble()));
        }
        return chromosomes;
    }

    private static <C extends Chromosome> void print(final List<Individual<C>> individuals) {
        final TreeMap<Double, Integer> count = new TreeMap<>();

        for (final Individual<C> i : individuals) {
            final Double k = Double.valueOf(i.fitness());
            final Integer c = count.getOrDefault(k, Integer.valueOf(0)) + 1;
            count.put(k, c);
        }

        for (final Map.Entry<Double, Integer> e : count.entrySet()) {
            System.out.println(e.getKey() + " | " + e.getValue());
        }
    }

}