package org.nor.xml.stax.model.Item.test;

import java.util.List;

import org.junit.Test;
import org.nor.xml.model.Item;
import org.nor.xml.stax.model.Item.StaXParser;


/**
 * @author Administrator
 *
 */
public class StaXParserTest {
  
  /**
   * 
   */
  @Test
  public final void readConfigTest() {
    StaXParser read = new StaXParser();
    List<Item> readConfig = read.readConfig("classes/config.xml");
    for (Item item : readConfig) {
      System.out.println(item);
    }

  }
}
