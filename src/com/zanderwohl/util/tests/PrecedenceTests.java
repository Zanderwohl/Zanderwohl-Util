package com.zanderwohl.util.tests;

import com.zanderwohl.util.Precedence;
import junit.framework.TestCase;
import org.junit.Test;

public class PrecedenceTests extends TestCase {

    @Test
    public void testBlanksLastString(){
        assertEquals("last", Precedence.lastNonNull(null, "last"));
        assertEquals("last", Precedence.lastNonNull(null, null, "last"));
        assertEquals("last", Precedence.lastNonNull(null, null, null, "last"));
        assertEquals("last", Precedence.lastNonNull(null, null, null, null, "last"));
        assertEquals("last", Precedence.lastNonNull(null, null, null, null, null, "last"));
    }

    @Test
    public void testBlanksFirstString(){
        assertEquals(Precedence.lastNonNull("first", null), "first");
        assertEquals(Precedence.lastNonNull("first", null, null), "first");
        assertEquals(Precedence.lastNonNull("first", null, null, null), "first");
        assertEquals(Precedence.lastNonNull("first", null, null, null, null), "first");
        assertEquals(Precedence.lastNonNull("first", null, null, null, null, null), "first");
    }

    @Test
    public void testExtremeStrings(){
        assertEquals(Precedence.lastNonNull("first", "last"), "last");
        assertEquals(Precedence.lastNonNull("first", null, "last"), "last");
        assertEquals(Precedence.lastNonNull("first", null, null, "last"), "last");
        assertEquals(Precedence.lastNonNull("first", null, null, null, "last"), "last");
        assertEquals(Precedence.lastNonNull("first", null, null, null, null, "last"), "last");
    }

    @Test
    public void testFullStrings(){
        assertEquals(Precedence.lastNonNull("first", "last"), "last");
        assertEquals(Precedence.lastNonNull("first", "second", "last"), "last");
        assertEquals(Precedence.lastNonNull("first", "second", "third", "last"), "last");
        assertEquals(Precedence.lastNonNull("first", "second", "third", "fourth", "last"), "last");
    }

    @Test
    public void randomStrings(){
        assertEquals(Precedence.lastNonNull("hey there", "another one", "random strings", null), "random strings");
        assertEquals(Precedence.lastNonNull("hey there", "another one", null, "random strings", null), "random strings");
        assertEquals(Precedence.lastNonNull("asdf", "jkl;", null), "jkl;");
    }

    @Test
    public void testOnly(){
        assertEquals(Precedence.lastNonNull("only"), "only");
        assertEquals(Precedence.lastNonNull("some string"), "some string");
    }

    @Test
    public void testOnlyInt(){
        assertEquals(Precedence.lastInt("0"), 0);
        assertEquals(Precedence.lastInt("10"), 10);
        assertEquals(Precedence.lastInt("-10"), -10);
    }

    @Test
    public void testIgnoreBadInts(){
        assertEquals(Precedence.lastInt("0", "2", "elephant"), 2);
        assertEquals(Precedence.lastInt("0", "-2", "elephant"), -2);
        assertEquals(Precedence.lastInt("0", "5.5"), 0);
    }

    @Test
    public void testNullInts(){
        assertEquals(Precedence.lastInt("0", null), 0);
        assertEquals(Precedence.lastInt("5", ""), 5);
        assertEquals(Precedence.lastInt("5", "7", "", null, "hello words"), 7);
    }

    @Test
    public void testFullInts(){
        assertEquals(Precedence.lastInt("1", "2", "4"), 4);
        assertEquals(Precedence.lastInt("1", "32", "64", "55"), 55);
        assertEquals(Precedence.lastInt("1", "32", "64", "55", "53", "75", "99", "22"), 22);
    }

}
