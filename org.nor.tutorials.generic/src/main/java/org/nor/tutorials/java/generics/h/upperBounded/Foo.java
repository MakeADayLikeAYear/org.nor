package org.nor.tutorials.java.generics.h.upperBounded;

import java.util.List;

public class Foo {


  public static void process(List<? extends Foo> list) {
    // for (Foo elem : list) {
    // // ...
    // }
  }

  public static double sumOfList(List<? extends Number> list) {
    double s = 0.0;
    for (Number n : list)
      s += n.doubleValue();
    return s;
  }

  public static void printList(List<?> list) {
    for (Object elem : list)
      System.out.print(elem + " ");
    System.out.println();
  }

  public static void addNumbers(List<? super Integer> list) {
    for (int i = 1; i <= 10; i++) {
      list.add(i);
    }
  }

}
