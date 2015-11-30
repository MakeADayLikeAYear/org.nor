package org.nor.tutorials.java.generics.n.restrictions;

import java.util.ArrayList;
import java.util.List;

public class MobileDevice<T> {
  // Error : static 이면 당연히 안되겠네..
  // static은 항상 존재하는데... 어떻게 type을 줄 수가 있나!!
  // private static T os;
  private T os;

  public T getOs() {
    return os;
  }

  public void setOs(T os) {
    this.os = os;
  }

  // Error : Because the Java compiler erases all type parameters in generic code, you cannot verify
  // which parameterized type for a generic type is being used at runtime:

  // public static <E> void rtti(List<E> list) {
  // if (list instanceof ArrayList<Integer>) { // compile-time error
  // // ...
  // }
  // }
  
  public static void rtti(List<?> list) {
    if (list instanceof ArrayList<?>) { // OK; instanceof requires a reifiable type
      // ...
    }
  }


  // ...
}

