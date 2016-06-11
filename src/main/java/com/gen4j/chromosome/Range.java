/**
 *
 */
package com.gen4j.chromosome;

import com.google.common.base.Preconditions;

/**
 * Represents an interval of real values.
 */
public final class Range {

    private final double lowerBound;
    private final double upperBound;
    private final double length;

    private Range(final double lowerBound, final double upperBound) {
        Preconditions.checkArgument(lowerBound < upperBound);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        length = upperBound - lowerBound;
    }

    public static Range of(final double lowerBound, final double upperBound) {
        return new Range(lowerBound, upperBound);
    }

    public double lowerBound() {
        return lowerBound;
    }

    public double upperBound() {
        return upperBound;
    }

    public double length() {
        return length;
    }
}
