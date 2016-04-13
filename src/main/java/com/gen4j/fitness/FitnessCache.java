package com.gen4j.fitness;

import com.gen4j.chromosome.Chromosome;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public final class FitnessCache<G extends Chromosome> implements FitnessFunction<G> {

    private LoadingCache<G, Double> cache;

    public FitnessCache(final FitnessFunction<G> function) {

        cache = CacheBuilder.newBuilder()
                .build(new CacheLoader<G, Double>() {
                    @Override
                    public Double load(final G chromosome) throws Exception {
                        return function.evaluate(chromosome);
                    }
                });
    }

    @Override
    public double evaluate(final G chromosome) {
        return cache.getUnchecked(chromosome).doubleValue();
    }

}
