package com.gen4j.phenotype;

import java.util.Set;

public interface Phenotype {

    double variable(String identifier);

    Set<String> variables();

    void set(String identifier, double d);

}
