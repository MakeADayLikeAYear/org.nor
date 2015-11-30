package org.nor.tutorials.java.generics.k.genTypes;

public class Node2<T extends Comparable<T>> {

  private T data;
  private Node2<T> next;

  public Node2(T data, Node2<T> next) {
    this.data = data;
    this.next = next;
  }

  public T getData() {
    return data;
  }

  public Node2<T> getNext() {
    return next;
  }
  // ...
}
/**
 * <pre>
 * public class Node {
 * 
 *   private Comparable data;
 *   private Node next;
 * 
 *   public Node(Comparable data, Node next) {
 *     this.data = data;
 *     this.next = next;
 *   }
 * 
 *   public Comparable getData() {
 *     return data;
 *   }
 *   // ...
 * }
 * 
 * </pre>
 */

