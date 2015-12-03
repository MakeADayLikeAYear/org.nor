package org.nor.xml.stax.model.Item.test;

import java.util.List;

import org.junit.Test;
import org.nor.xml.model.Item;
import org.nor.xml.stax.model.Item.QueryXML;


/**
 * @author Administrator
 *
 */
public class QueryXMLTest {
  
  
  /**
   * @throws Exception : x
   */
//  @Test
  public final void queryNoParamTest() throws Exception {
    QueryXML read = new QueryXML();
    read.query();
  }
  
  /**
   * @throws Exception : x
   */
//  @Test
  public final void queryTest2() throws Exception {
    QueryXML read = new QueryXML();
    read.query2("classes/person.xml");

  }
  
  /**
   * @throws Exception : x
   */
  @Test
  public final void queryTest() throws Exception {
    QueryXML read = new QueryXML();
    List<Item> readConfig = read.query("classes/config.xml", Item.class);
    for (Item item : readConfig) {
      System.out.println(item);
    }

  }
}
