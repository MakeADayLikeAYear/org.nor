package org.nor.tutorials.java.generics.j.capture;

import java.util.List;

public class WildcardFixed {
  void foo(List<?> i) {
    fooHelper(i);
  }

  // Helper method created so that the wildcard can be captured
  // through type inference.
  private <T> void fooHelper(List<T> l) {
    l.set(0, l.get(0));
  }

  private <T extends Number> void fooHelper1(List<T> l, T j) {
    l.set(0, j);
  }

  @SuppressWarnings("unchecked")
  private <T extends Number> void fooHelper2(List<T> l, Object j) {
    l.set(0, (T) j);
  }

  <T extends Number> void swapFirst1(List<T> l1, List<T> l2) {
    T temp = l1.get(0);
    fooHelper1(l1, l2.get(0));
    fooHelper1(l2, temp);
  }

  void swapFirst2(List<? extends Number> l1, List<? extends Number> l2) {
    Number temp = l1.get(0);
    fooHelper2(l1, l2.get(0));
    fooHelper2(l2, temp);
  }
}
