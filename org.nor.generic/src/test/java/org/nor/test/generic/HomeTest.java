package org.nor.test.generic;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.nor.generic.vo.Home;

/**
 * @author Administrator
 *
 */
public class HomeTest {

  
  /**
   * 
   * @throws Exception : 
   */
  @Test
  public final void home() throws Exception {
    
    Home str = new Home();
    assertEquals("java.lang.String", str.newInstance().getClass().getName());
  }
  
}
