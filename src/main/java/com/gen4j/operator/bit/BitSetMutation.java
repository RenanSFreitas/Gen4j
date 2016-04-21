package com.gen4j.operator.bit;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.population.Individual;
import com.google.common.base.Preconditions;

public final class BitSetMutation implements GeneticOperator<BitChromosome> {

    private final Random random = new Random(System.nanoTime());

    private double probability = 0.01;

    @Override
    public int chromosomeCount() {
        return 1;
    }

    @Override
    public List<Individual<BitChromosome>> apply(final Collection<Individual<BitChromosome>> individuals,
            final GeneticAlgorithmFactory<BitChromosome> factory) {

        final BitChromosome mutant = new BitChromosome(getOnlyElement(individuals).chromosome());
        final BitSet bits = mutant.value();
        bits.flip(random.nextInt(mutant.length()));
        return Collections.singletonList(factory.individual(mutant));
    }

    @Override
    public double probability() {
        return probability;
    }

    @Override
    public void probability(final double probability) {
        Preconditions.checkArgument(probability > 0d && probability < 1d);
        this.probability = probability;
    }

}
