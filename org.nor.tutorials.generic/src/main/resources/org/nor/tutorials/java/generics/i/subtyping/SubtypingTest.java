package org.nor.tutorials.java.generics.i.subtyping;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SubtypingTest {

  @Test
  public void Subtyping() {
    SubtypingB b = new SubtypingB();
    SubtypingA a = b;
    assertEquals(a, b);
    
    // Error
    // List<SubtypingB> lb = new ArrayList<>();
    // List<SubtypingA> la = lb;   // compile-time error
    
    List<? extends SubtypingB> lb = new ArrayList<>();
    List<? extends SubtypingA> la = lb;   // compile-time error
    assertEquals(la, lb);
  }
}
