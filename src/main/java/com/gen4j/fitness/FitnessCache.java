package com.gen4j.fitness;

import com.gen4j.genotype.Genotype;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public final class FitnessCache<G extends Genotype> implements FitnessFunction<G> {

    private LoadingCache<G, Double> cache;

    public FitnessCache(final FitnessFunction<G> function) {

        cache = CacheBuilder.newBuilder()
                .build(new CacheLoader<G, Double>() {
                    @Override
                    public Double load(final G genotype) throws Exception {
                        return function.evaluate(genotype);
                    }
                });
    }

    @Override
    public double evaluate(final G genotype) {
        return cache.getUnchecked(genotype).doubleValue();
    }

}
