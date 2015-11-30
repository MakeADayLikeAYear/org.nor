package org.nor.tutorials.java.generics.n.restrictions;

import java.util.List;

public class Pair<K, V> {

  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  // public static <E> void append(List<E> list) {
  // E elem = new E(); // compile-time error
  // list.add(elem);
  // }
  public static <E> void append(List<E> list, Class<E> cls) throws Exception {
    E elem = cls.newInstance(); // OK
    list.add(elem);
  }


  // ...
}

