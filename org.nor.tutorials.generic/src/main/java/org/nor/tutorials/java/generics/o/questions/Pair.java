package org.nor.tutorials.java.generics.o.questions;

import java.util.List;

public class Pair<K, V> {

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public void setValue(V value) {
    this.value = value;
  }

  private K key;
  private V value;


  public static <T extends Comparable<T>> int findFirstGreaterThan(T[] at, T elem) {
    return 0;
    // ...
  }

  public static void print(List<? extends Number> list) {
    for (Number n : list)
      System.out.print(n + " ");
    System.out.println();
  }

  public static <T extends Number> void print2(List<T> list) {
    for (Number n : list)
      System.out.print(n + " ");
    System.out.println();
  }

}
