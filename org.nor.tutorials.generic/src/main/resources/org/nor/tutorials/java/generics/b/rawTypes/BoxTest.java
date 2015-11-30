package org.nor.tutorials.java.generics.b.rawTypes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BoxTest {

  @SuppressWarnings("unchecked")
  @Test
  public void box() {
    
    // Case 1.
    Box<String> stringBox1 = new Box<>();
    @SuppressWarnings("rawtypes")
    Box rawBox1 = stringBox1;               // OK
    assertEquals(rawBox1, stringBox1);

    // Case 2.
    @SuppressWarnings("rawtypes")
    Box rawBox2 = new Box();           // rawBox is a raw type of Box<T>
//    @SuppressWarnings("unchecked")
    Box<Integer> intBox2 = rawBox2;     // warning: unchecked conversion
    assertEquals(intBox2, rawBox2);
    
    // Case 3.
    Box<String> stringBox3 = new Box<>();
    @SuppressWarnings("rawtypes")
    Box rawBox3 = stringBox3;
    rawBox3.set(8);  // warning: unchecked invocation to set(T)
    assertEquals(stringBox3, rawBox3);
  }
}
