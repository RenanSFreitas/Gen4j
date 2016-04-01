package com.gen4j.phenotype;

public interface Phenotype<V> {

    double variable(V identifier);

    void set(V identifier, double d);

}
