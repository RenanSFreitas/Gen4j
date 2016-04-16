package com.gen4j.phenotype.bit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.gen4j.phenotype.Phenotype;

public class BitSetPhenotype implements Phenotype<String> {

    private final Map<String, Double> m = new HashMap<>();

    public BitSetPhenotype() {}

    @Override
    public double variable(final String identifier) {
        checkArgument(!isNullOrEmpty(identifier));
        return m.get(identifier).doubleValue();
    }

    @Override
    public void set(final String identifier, final double d) {
        checkArgument(!isNullOrEmpty(identifier));
        m.put(identifier, Double.valueOf(d));
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BitSetPhenotype)) {
            return false;
        }

        return m.equals(((BitSetPhenotype) obj).m);
    }

    @Override
    public int hashCode() {
        return m.hashCode();
    }

    @Override
    public String toString() {
        return new TreeMap<>(m).toString();
    }
}