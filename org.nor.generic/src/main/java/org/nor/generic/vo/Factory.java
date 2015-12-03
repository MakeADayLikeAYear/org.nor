package org.nor.generic.vo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Administrator
 *
 * @param <T>
 */
public class Factory<T> {

  /**
   * 
   * @return :
   * @throws ClassNotFoundException :
   * @throws IllegalAccessException :
   * @throws InstantiationException :
   */
  @SuppressWarnings("unchecked")
  public final T newInstance() throws ClassNotFoundException,
      IllegalAccessException, InstantiationException {

    Type tType = null;
    ParameterizedType pType = null;
    Class<T> clazz = null;

    tType = getClass().getGenericSuperclass();
    pType = (ParameterizedType) tType;
    tType = pType.getActualTypeArguments()[0];
    clazz = (Class<T>) tType;

    return clazz.newInstance();
  }

  /**
   * @param className :
   * @return :
   * @throws ClassNotFoundException :
   * @throws IllegalAccessException :
   * @throws InstantiationException :
   */
  @SuppressWarnings("unchecked")
  public final T newInstance(final String className)
      throws ClassNotFoundException, IllegalAccessException,
      InstantiationException {

    return newInstance((Class<T>) Class.forName(className));
  }

  /**
   * 
   * @param cls :
   * @return :
   * @throws IllegalAccessException :
   * @throws InstantiationException :
   */
  public final T newInstance(final Class<T> cls)
      throws IllegalAccessException, InstantiationException {

    return cls.newInstance();
  }

  /**
   * @param obj :
   * @param cls :
   * @param methodName :
   * @param methodParam :
   * @throws IllegalAccessException : 
   * @throws IllegalArgumentException : 
   * @throws InvocationTargetException : 
   */
  public final void invoke(final T obj, final Class<T> cls,
      final String methodName, final Object[] methodParam)
          throws IllegalAccessException, IllegalArgumentException,
          InvocationTargetException {
    Method[] methods = cls.getMethods();
    for (Method method : methods) {
      if (methodName.equals(method.getName())) {
        method.invoke(obj, methodParam);
        return;
      }
    }
  }
}
