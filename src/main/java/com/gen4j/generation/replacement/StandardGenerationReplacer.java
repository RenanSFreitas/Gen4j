package com.gen4j.generation.replacement;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newTreeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.PopulationBuilder;

public enum StandardGenerationReplacer {

    GENERATIONAL {
        @Override
        public <C extends Chromosome> GenerationReplacer<C> get() {
            return (generation, nextGeneration, factory) -> nextGeneration;
        }
    },
    STEADY_STATE {
        @Override
        public <C extends Chromosome> GenerationReplacer<C> get() {
            return (generation, nextGeneration, f) -> {
                final List<Individual<C>> exchanged = newArrayList(concat(generation.fitness(), nextGeneration.fitness()));
                Collections.sort(exchanged);
                final int exchangedSize = exchanged.size();
                final int nextGenerationSize = nextGeneration.size();
                return PopulationBuilder.of(f).size(nextGenerationSize)
                        .initialChromosomes(exchanged.subList(exchangedSize - nextGenerationSize, exchangedSize))
                        .build();
            };
        }
    },
    STEADY_STATE_NO_DUPLICATES {
        @Override
        public <C extends Chromosome> GenerationReplacer<C> get() {
            return (generation, nextGeneration, factory) -> {
                final List<Individual<C>> parents = generation.fitness();
                final int parentSize = parents.size();
                final List<Individual<C>> exchanged = new ArrayList<>(parentSize);
                final Iterator<Individual<C>> iterator = newTreeSet(concat(parents, nextGeneration.fitness())).descendingIterator();
                for (int counter = 0; counter < parentSize && iterator.hasNext(); counter++) {
                    exchanged.add(iterator.next());
                }
                return PopulationBuilder.of(factory).size(parentSize).initialChromosomes(exchanged).build();
            };
        }
    };

    public abstract <C extends Chromosome> GenerationReplacer<C> get();
}
