package com.gen4j.generation.replacement;

import java.util.ArrayList;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationBuilder;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.TreeMultiset;

/**
 * Standard strategies of generation replacement.
 */
public enum StandardGenerationReplacer {

    /**
     * Replacement strategy that entirely replaces the previous generation with
     * the offsprings.
     */
    GENERATIONAL {
        @Override
        public <C extends Chromosome> GenerationReplacer<C> get() {
            return (parentsGeneration, offspringGeneration, factory) -> offspringGeneration;
        }
    },
    STEADY_STATE {
        @Override
        public <C extends Chromosome> GenerationReplacer<C> get() {
            return (parentsGeneration, offspringGeneration, factory) -> {
                return steadyStateReplacement(parentsGeneration, offspringGeneration, factory, true);
            };
        }

    },
    STEADY_STATE_NO_DUPLICATES {
        @Override
        public <C extends Chromosome> GenerationReplacer<C> get() {
            return (parentsGeneration, offspringGeneration, factory) -> {
                return steadyStateReplacement(parentsGeneration, offspringGeneration, factory, false);
            };
        }
    };

    public abstract <C extends Chromosome> GenerationReplacer<C> get();

    protected <C extends Chromosome> Population<C> steadyStateReplacement(final Population<C> parentsGeneration,
            final Population<C> offspringGeneration, final GeneticAlgorithmFactory<C> factory,
            final boolean allowDuplicates) {

        final TreeMultiset<Individual<C>> parents = TreeMultiset.create();
        parents.addAll(parentsGeneration.toList());

        final List<Individual<C>> offsprings = offspringGeneration.toList();
        final List<Individual<C>> survivalOffsprings = new ArrayList<>(offspringGeneration.size());
        for (int i = 0; i < offsprings.size() && !parents.isEmpty(); i++) {
            final Individual<C> worstParent = parents.firstEntry().getElement();
            final Individual<C> offspring = offsprings.get(i);

            if (offspring.fitness() > worstParent.fitness() && (allowDuplicates || !parents.contains(offspring))) {
                parents.remove(worstParent);
                survivalOffsprings.add(offspring);
            }
        }


        final List<Individual<C>> nextGeneration = new ArrayList<>(parents.size());
        for (final Entry<Individual<C>> parent : parents.entrySet()) {
            for (int i = 0; i < parent.getCount(); i++) {
                nextGeneration.add(parent.getElement());
            }
        }
        nextGeneration.addAll(survivalOffsprings);

        return PopulationBuilder.of(factory)
                .size(nextGeneration.size())
                .initialChromosomes(nextGeneration)
                .build();
    }
}
