package org.nor.tutorials.java.generics.c.mathods;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class UtilTest {

  @Test
  public void compare() {
    
    String p1a = "apple";
    String p1b = "pear";
    
    Pair<Integer, String> p1 = new Pair<>(1, p1a);
    Pair<Integer, String> p2 = new Pair<>(2, p1b);
    boolean same1 = Util.<Integer, String>compare(p1, p2);
    assertFalse(same1);
    
    Pair<Integer, String> p3 = new Pair<>(1, p1a);
    Pair<Integer, String> p4 = new Pair<>(2, p1b);
    boolean same2 = Util.compare(p3, p4);
    assertFalse(same2);
  }
}
