package com.gen4j.operator.bit;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.population.Individual;

public final class BitChromosomeMutation extends AbstractGeneticOperator<BitChromosome> {

    public BitChromosomeMutation() {
        super(0.01, 1);
    }

    @Override
    public List<Individual<BitChromosome>> apply(final Collection<Individual<BitChromosome>> individuals,
            final GeneticAlgorithmFactory<BitChromosome> factory) {

        final BitChromosome mutant = new BitChromosome(getOnlyElement(individuals).chromosome());
        final BitSet bits = mutant.value();
        bits.flip(random.nextInt(mutant.length()));
        return Collections.singletonList(factory.individual(mutant));
    }
}
