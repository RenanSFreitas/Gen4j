package com.gen4j.operator.selection.roulette;

import static java.lang.Double.compare;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.utils.Pair;

class Roulette<G extends Genotype> {

    private final Random random = new Random(System.nanoTime());

    private final List<Pair<Chromosome<G>, Double>> roulette;
    private final double maxRelativeFitness;

    static <G extends Genotype> Roulette<G> of(final Set<Chromosome<G>> chromosomes) {

        final SortedMap<Chromosome<G>, Double> populationFitness =
                new TreeMap<>((c1, c2) -> compare(c1.fitness(), c2.fitness()));

        double totalFitnessAux = 0;

        for (final Chromosome<G> c : chromosomes) {
            final double fitness = c.fitness();
            populationFitness.put(c, fitness);
            totalFitnessAux += fitness;
        }

        final List<Pair<Chromosome<G>, Double>> roulette = new ArrayList<>(chromosomes.size());

        final double totalFitness = totalFitnessAux;

        populationFitness.forEach((chromosome, fitness) -> roulette.add(Pair.of(chromosome, fitness / totalFitness)));

        final double maxRelativeFitness = populationFitness.lastKey().fitness() / totalFitness;

        return new Roulette<>(roulette, maxRelativeFitness);
    }

    private Roulette(final List<Pair<Chromosome<G>, Double>> roulette, final double maxRelativeFitness) {
        this.roulette = roulette;
        this.maxRelativeFitness = maxRelativeFitness;
    }

    private Chromosome<G> sortChromosome(final double relativeFitness) {
        return roulette.stream().filter(p -> p.second() > relativeFitness).findFirst().get().first();
    }

    List<Chromosome<G>> sortChromosomes(final int count) {
        return random.doubles(count, 0d, maxRelativeFitness)
                .mapToObj(i -> sortChromosome(i))
                .collect(toList());
    }
}