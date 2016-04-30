package com.gen4j.population.exchange;

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

public enum StandardPopulationExchanger {

    TOTAL_EXCHANGE {
        @Override
        public <C extends Chromosome> PopulationExchanger<C> get() {
            return (p, o, f) -> o;
        }
    },
    STEADY_STATE {
        @Override
        public <C extends Chromosome> PopulationExchanger<C> get() {
            return (p, o, f) -> {
                final List<Individual<C>> exchanged = newArrayList(concat(p.fitness(), o.fitness()));
                Collections.sort(exchanged);
                final int exchangedSize = exchanged.size();
                return PopulationBuilder.of(f).size(o.size())
                        .initialChromosomes(exchanged.subList(exchangedSize - o.size(), exchangedSize))
                        .build();
            };
        }
    },
    STEADY_STATE_NO_DUPLICATES {
        @Override
        public <C extends Chromosome> PopulationExchanger<C> get() {
            return (p, o, factory) -> {
                final List<Individual<C>> parents = p.fitness();
                final int parentSize = parents.size();
                final List<Individual<C>> exchanged = new ArrayList<>(parentSize);
                final Iterator<Individual<C>> iterator = newTreeSet(concat(parents, o.fitness())).descendingIterator();
                for (int counter = 0; counter < parentSize && iterator.hasNext(); counter++) {
                    exchanged.add(iterator.next());
                }
                return PopulationBuilder.of(factory).size(parentSize).initialChromosomes(exchanged).build();
            };
        }
    };

    public abstract <C extends Chromosome> PopulationExchanger<C> get();
}
