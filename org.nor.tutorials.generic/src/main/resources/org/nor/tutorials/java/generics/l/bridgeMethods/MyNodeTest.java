package org.nor.tutorials.java.generics.l.bridgeMethods;

import org.junit.Test;

public class MyNodeTest {

  @Test
  public void MyNode1() {
//    MyNode mn = new MyNode(5);
//    Node n = mn; // A raw type - compiler throws an unchecked warning
//    n.setData("Hello");
//    Integer x = mn.data; // Causes a ClassCastException to be thrown
  }
  
  @Test
  public void MyNode2() {
//    MyNode mn = new MyNode(5);
//    Node n = (MyNode)mn;         // A raw type - compiler throws an unchecked warning
//    n.setData("Hello");
//    Integer x = (String)mn.data; // Causes a ClassCastException to be thrown.
  }
}
