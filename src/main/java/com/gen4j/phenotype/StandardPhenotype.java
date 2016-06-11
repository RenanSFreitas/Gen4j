package com.gen4j.phenotype;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.ImmutableSet;

public final class StandardPhenotype implements Phenotype {

    private final Map<String, Double> m = new HashMap<>();

    public StandardPhenotype() {}

    @Override
    public double variable(final String identifier) {
        checkArgument(!isNullOrEmpty(identifier));
        final Double value = m.get(identifier);
        checkArgument(value != null, "Invalid phenotype variable %s ", identifier);
        return value.doubleValue();
    }

    @Override
    public void set(final String identifier, final double d) {
        checkArgument(!isNullOrEmpty(identifier));
        m.put(identifier, Double.valueOf(d));
    }

    @Override
    public Set<String> variables() {
        return ImmutableSet.copyOf(m.keySet());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StandardPhenotype)) {
            return false;
        }

        return m.equals(((StandardPhenotype) obj).m);
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