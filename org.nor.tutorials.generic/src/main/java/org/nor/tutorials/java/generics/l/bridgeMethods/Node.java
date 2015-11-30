package org.nor.tutorials.java.generics.l.bridgeMethods;

public class Node<T> {

  public T data;

  public Node(T data) {
    this.data = data;
  }

  public void setData(T data) {
    System.out.println("Node.setData");
    this.data = data;
  }
}
/**
 * <pre>
 * public class Node {
 * 
 *   public Object data;
 * 
 *   public Node(Object data) {
 *     this.data = data;
 *   }
 * 
 *   public void setData(Object data) {
 *     System.out.println("Node.setData");
 *     this.data = data;
 *   }
 * }
 * 
 * </pre>
 */
