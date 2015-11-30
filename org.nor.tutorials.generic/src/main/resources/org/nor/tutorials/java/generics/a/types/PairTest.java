package org.nor.tutorials.java.generics.a.types;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PairTest {

  @Test
  public void pair() {
    
    String p1a = "Even";
    int p1b = 8;
    String p2a = "hello";
    String p2b = "world";
    
    Pair<String, Integer> p1 = new OrderedPair<String, Integer>(p1a, p1b);
    Pair<String, String>  p2 = new OrderedPair<String, String>(p2a, p2b);
    
    OrderedPair<String, Integer> p3 = new OrderedPair<>(p1a, p1b);
    OrderedPair<String, String>  p4 = new OrderedPair<>(p2a, p2b);


    assertEquals(p1a, p1.getKey());
    assertEquals(p1b, p1.getValue());
    assertEquals(p2a, p2.getKey());
    assertEquals(p2b, p2.getValue());
    
    assertEquals(p1a, p3.getKey());
    assertEquals(p1b, p3.getValue());
    assertEquals(p2a, p4.getKey());
    assertEquals(p2b, p4.getValue());
  }
}
