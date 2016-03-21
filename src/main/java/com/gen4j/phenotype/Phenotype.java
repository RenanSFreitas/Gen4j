package com.gen4j.phenotype;

public interface Phenotype<K> {

    double variable(K identifier);

    void set(K identifier, double d);

}
