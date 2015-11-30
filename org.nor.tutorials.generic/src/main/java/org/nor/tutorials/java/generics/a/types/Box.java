package org.nor.tutorials.java.generics.a.types;

/**
 * Generic version of the Box class.
 * 
 * <pre>
 * Type Parameter Naming Conventions
 * By convention, type parameter names are single, uppercase letters.
 * This stands in sharp contrast to the variable naming conventions that you already know about,
 * and with good reason: Without this convention,
 * it would be difficult to tell the difference between a type variable and an ordinary class or interface name.
 * The most commonly used type parameter names are:
 *  •E - Element (used extensively by the Java Collections Framework)
 *  •K - Key
 *  •N - Number
 *  •T - Type
 *  •V - Value
 *  •S,U,V etc. - 2nd, 3rd, 4th types
 * You'll see these names used throughout the Java SE API and the rest of this lesson.
 * </pre>
 * 
 * @param <T> the type of the value being boxed
 */
public class Box<T> {
  // T stands for "Type"
  private T t;

  public void set(T t) {
    this.t = t;
  }

  public T get() {
    return t;
  }
}
