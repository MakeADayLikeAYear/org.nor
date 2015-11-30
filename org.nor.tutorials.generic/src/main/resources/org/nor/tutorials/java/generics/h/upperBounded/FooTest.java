package org.nor.tutorials.java.generics.h.upperBounded;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FooTest {
  @Test
  public void sumOfListTest() {
    List<Integer> li = Arrays.asList(1, 2, 3);
    assertEquals(Foo.sumOfList(li), 6.0);
    
    List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
    assertEquals(Foo.sumOfList(ld), 7.0);
  }
  
  @Test
  public void printList() {
    List<Integer> li = Arrays.asList(1, 2, 3);
    List<String>  ls = Arrays.asList("one", "two", "three");
    Foo.printList(li);
    Foo.printList(ls);
  }
}
