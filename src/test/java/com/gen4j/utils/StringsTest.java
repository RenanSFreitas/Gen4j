package com.gen4j.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class StringsTest {

    @Test
    public void testReverse() {
        assertEquals("321dcba", Strings.reverse("abcd123"));
    }

}
