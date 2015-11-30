package org.nor.tutorials.java.generics.n.restrictions;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PairTest {

  @Test
  public void Pair() {
    // Error
    // Pair<int, char> p = new Pair<>(8, 'a'); // compile-time error
    Pair<Integer, Character> p1 = new Pair<>(8, 'a');
    Pair<Integer, Character> p2 = new Pair<>(Integer.valueOf(8), new Character('a'));

    assertEquals(p1.getKey(), p2.getKey());
    assertEquals(p1.getValue(), p2.getValue());

  }
}
