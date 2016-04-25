package com.gen4j.operator.selection;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.utils.Pair;

public final class TournamentSelector<C extends Chromosome> extends AbstractSelector<C> {

    private final int arity;
    private final Comparator<Individual<C>> comparator = (c1, c2) -> Double.compare(c1.fitness(), c2.fitness());

    public TournamentSelector(final int arity) {
        checkArgument(arity > 1, "Tournament arity should be greater than 1");
        this.arity = arity;
    }

    @Override
    public void population(final Population<C> population) {
        checkArgument(arity < population.size(), "Population too small for tournament of arity %d", arity);
        super.population(population);
    }

    private Individual<C> select(final List<Individual<C>> individuals) {
        if (arity == 2) {
            return tournamentOfTwo(individuals);
        }

        return tournament(individuals);
    }

    @Override
    public Pair<Individual<C>, Individual<C>> selectPair() {

        final List<Individual<C>> individuals = population().toList();

        return Pair.of(select(individuals), select(individuals));
    }

    private Individual<C> tournament(final List<Individual<C>> individuals) {

        final TreeSet<Individual<C>> tree = new TreeSet<>(comparator);

        for (int i = 0; i < arity; i++) {
            tree.add(individuals.get(random.nextInt(individuals.size())));
        }

        return tree.last();
    }

    private Individual<C> tournamentOfTwo(final List<Individual<C>> individuals) {

        final int size = individuals.size();

        final Individual<C> firstIndividual = individuals.get(random.nextInt(size));
        final Individual<C> secondIndividual = individuals.get(random.nextInt(size));

        return firstIndividual.fitness() > secondIndividual.fitness() ?
                firstIndividual : secondIndividual;
    }

    @Override
    public List<Individual<C>> select(final int count) {
        final List<Individual<C>> selected = new ArrayList<>(count);
        final List<Individual<C>> population = population().toList();
        while (selected.size() < count) {
            selected.add(select(population));
        }
        return selected;
    }

}
