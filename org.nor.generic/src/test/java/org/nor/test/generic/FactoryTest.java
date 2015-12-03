package org.nor.test.generic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.nor.generic.vo.Factory;

/**
 * @author Administrator
 *
 */
public class FactoryTest {

  /**
   * @throws Exception : 
   */
  @Test
  public final void newInstance1() throws Exception {
    Factory<String> str = new Factory<>();
    assertEquals("java.lang.String",
        str.newInstance("java.lang.String").getClass().getName());
  }
  
  /**
   * @throws Exception : 
   */
  @Test
  public final void newInstance2() throws Exception {
    Factory<String> str = new Factory<>();
    assertEquals("java.lang.String",
        str.newInstance(String.class).getClass().getName());
  }
}
