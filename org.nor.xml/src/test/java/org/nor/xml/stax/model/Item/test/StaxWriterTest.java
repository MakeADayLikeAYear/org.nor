package org.nor.xml.stax.model.Item.test;

import org.junit.Test;
import org.nor.xml.stax.model.Item.StaxWriter;

/**
 * @author Administrator
 *
 */
public class StaxWriterTest {

  /**
   * 
   */
  @Test
  public final void saveConfigTest() {
    StaxWriter configFile = new StaxWriter();
    configFile.setFile("src/test/resources/config2.xml");
    try {
      configFile.saveConfig();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
