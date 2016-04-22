package com.gen4j.fitness;

import com.gen4j.phenotype.Phenotype;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public final class FitnessCache implements FitnessFunction {

    private LoadingCache<Phenotype, Double> cache;

    public FitnessCache(final FitnessFunction function) {

        cache = CacheBuilder.newBuilder().build(new CacheLoader<Phenotype, Double>() {
            @Override
            public Double load(final Phenotype phenotype) throws Exception {
                return function.evaluate(phenotype);
            }
        });
    }

    @Override
    public double evaluate(final Phenotype phenotype) {
        return cache.getUnchecked(phenotype).doubleValue();
    }

}
