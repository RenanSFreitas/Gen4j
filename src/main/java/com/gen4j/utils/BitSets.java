package com.gen4j.utils;

import static com.gen4j.utils.Strings.reverse;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.requireNonNull;

import java.util.BitSet;

public final class BitSets {

    private BitSets() {
        throw new AssertionError("No instance for ya");
    }

    public static String toString(final BitSet bitSet, final int length) {
        requireNonNull(bitSet);
        checkArgument(bitSet.length() <= length);
        final StringBuilder builder = new StringBuilder();
        for (int i = length - 1; i > -1; i--) {
            builder.append(bitSet.get(i) ? '1' : '0');
        }
        return builder.toString();
    }

    public static BitSet fromString(final String bitsString) {
        checkArgument(!isNullOrEmpty(bitsString));
        final BitSet bits = new BitSet(bitsString.length());
        final char[] chars = reverse(bitsString).toCharArray();
        for( int i = 0; i<chars.length; i++ ) {
            final char ch = chars[i];
            if (ch != '1' && ch != '0') {
                throw new IllegalArgumentException("String must consist entirely on '1's and '0's.");
            }
            bits.set(i, ch == '1');
        }
        return bits;
    }
}
