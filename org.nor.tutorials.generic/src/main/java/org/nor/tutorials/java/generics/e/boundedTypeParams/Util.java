package org.nor.tutorials.java.generics.e.boundedTypeParams;

public class Util {
  
  /** 
   * 제네릭 변수에는 >, < 연산자를 사용할 수 없다.
   * @param anArray
   * @param elem
   * @return
   */
  public static <T> int countGreaterThan(T[] anArray, T elem) {
    int count = 0;
//    for (T e : anArray)
//      if (e > elem) // compiler error
        ++count;
    return count;
  }

  /**
   * 제네릭 변수뒤에 연산자 >. < 를 사용하지 못하므로 method를 이용.
   * @param anArray
   * @param elem
   * @return
   */
  public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
    int count = 0;
    for (T e : anArray)
      if (e.compareTo(elem) > 0)
        ++count;
    return count;
  }

}
