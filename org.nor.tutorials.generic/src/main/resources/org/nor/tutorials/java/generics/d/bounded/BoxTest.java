package org.nor.tutorials.java.generics.d.bounded;

import org.junit.Test;

public class BoxTest {

  @Test
  public void inspectTest() {
    Box<Integer> integerBox = new Box<Integer>();
    integerBox.set(new Integer(10));

    // Error
    // integerBox.inspect("some text"); // error: this is still String!
    
    integerBox.inspect(5);
  }
}
