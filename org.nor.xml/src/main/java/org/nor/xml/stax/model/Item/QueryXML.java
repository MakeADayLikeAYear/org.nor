package org.nor.xml.stax.model.Item;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.nor.generic.vo.Factory;
import org.nor.xml.model.Item;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Administrator
 *
 */
public class QueryXML<T> {

  /**
   * 
   */
  // private VOFactory<T> vo = new VOFactory<>();

  /**
   * @throws ParserConfigurationException : x
   * @throws SAXException : y
   * @throws IOException : z
   * @throws XPathExpressionException : i
   */
  public final void query() throws ParserConfigurationException, SAXException,
      IOException, XPathExpressionException {
    // standard for reading an XML file
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);

    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse("classes/person.xml");
    XPathExpression expr = null;

    // create an XPathFactory
    XPathFactory xFactory = XPathFactory.newInstance();

    // create an XPath object
    XPath xpath = xFactory.newXPath();

    // compile the XPath expression
    expr = xpath.compile("//person[firstname='Lars']/lastname/text()");
    // run the query and get a nodeset
    Object result = expr.evaluate(doc, XPathConstants.NODESET);

    // cast the result to a DOM NodeList
    NodeList nodes = (NodeList) result;
    for (int i = 0; i < nodes.getLength(); i++) {
      System.out.println(nodes.item(i).getNodeValue());
    }

    // new XPath expression to get the number of people with name Lars
    expr = xpath.compile("count(//person[firstname='Lars'])");
    // run the query and get the number of nodes
    Double number = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
    System.out.println("Number of objects " + number);

    // do we have more than 2 people with name Lars?
    expr = xpath.compile("count(//person[firstname='Lars']) >2");
    // run the query and get the number of nodes
    Boolean check = (Boolean) expr.evaluate(doc, XPathConstants.BOOLEAN);
    System.out.println(check);
  }

  /**
   * @param configFile : x
   * @return List<Item>
   * @throws ParserConfigurationException : y
   * @throws SAXException : z
   * @throws IOException : x
   * @throws XPathExpressionException : 3
   */
  public final List<Item> query2(final String configFile)
      throws ParserConfigurationException, SAXException, IOException,
      XPathExpressionException {

    // standard for reading an XML file
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);

    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(configFile);
    XPathExpression expr = null;
    NodeList nodes = null;
    NodeList nodes2 = null;

    // create an XPathFactory
    XPathFactory xFactory = XPathFactory.newInstance();

    // create an XPath object
    XPath xpath = xFactory.newXPath();

    // root
    expr = xpath.compile("/");
    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    String rootNodeName = nodes.item(0).getFirstChild().getNodeName();
    System.out.println("rootNodeName : " + rootNodeName);

    expr = xpath.compile("/" + rootNodeName);
    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

    Map<String, Integer> nodeMap = new HashMap<>();
    Integer cnt = null;

    nodes = nodes.item(0).getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      if (Node.ELEMENT_NODE == nodes.item(i).getNodeType()) {
        String nodeName1 = nodes.item(i).getNodeName();
        System.out.println(nodeName1);

        if (nodeMap.containsKey(nodeName1)) {
          cnt = nodeMap.get(nodeName1);
        } else {
          cnt = new Integer(0);
        }

        cnt = cnt + 1;
        nodeMap.put(nodeName1, cnt);

        expr = xpath
            .compile("/" + rootNodeName + "/" + nodeName1 + "[" + cnt + "]");
        nodes2 = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        nodes2 = nodes2.item(0).getChildNodes();
        for (int j = 0; j < nodes2.getLength(); j++) {
          if (Node.ELEMENT_NODE == nodes2.item(j).getNodeType()) {
            String nodeName2 = nodes2.item(j).getNodeName();
            System.out.println(nodeName2);
          }
        }

      }
    }

    return null;
  }

  /**
   * @param configFile : x
   * @return List<Item>
   * @throws ParserConfigurationException : y
   * @throws SAXException : z
   * @throws IOException : x
   * @throws XPathExpressionException : 3
   */
  public final List<Item> query(final String configFile)
      throws ParserConfigurationException, SAXException, IOException,
      XPathExpressionException {

    // standard for reading an XML file
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);

    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(configFile);

    XPathExpression expr = null;
    NodeList nodes = null;
    List<Item> itemList = new ArrayList<>();

    // create an XPathFactory
    XPathFactory xFactory = XPathFactory.newInstance();

    // create an XPath object
    XPath xpath = xFactory.newXPath();

    // root
    expr = xpath.compile("count(//item)");
    int size = ((Double) expr.evaluate(doc, XPathConstants.NUMBER)).intValue();
    for (int i = 0; i < size; i++) {
      Item item = new Item();
      itemList.add(item);
    }

    expr = xpath.compile("//item/@date");
    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    for (int i = 0; i < nodes.getLength(); i++) {
      itemList.get(i).setDate(nodes.item(i).getNodeValue());
    }

    expr = xpath.compile("//item/mode/text()");
    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    for (int i = 0; i < nodes.getLength(); i++) {
      itemList.get(i).setMode(nodes.item(i).getNodeValue());
    }

    expr = xpath.compile("//item/unit/text()");
    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    for (int i = 0; i < nodes.getLength(); i++) {
      itemList.get(i).setUnit(nodes.item(i).getNodeValue());
    }

    expr = xpath.compile("//item/current/text()");
    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    for (int i = 0; i < nodes.getLength(); i++) {
      itemList.get(i).setCurrent(nodes.item(i).getNodeValue());
    }

    expr = xpath.compile("//item/interactive/text()");
    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    for (int i = 0; i < nodes.getLength(); i++) {
      itemList.get(i).setInteractive(nodes.item(i).getNodeValue());
    }

    return itemList;
  }


  /**
   * @param configFile :
   * @param cls :
   * @return List<Item>
   * @throws ParserConfigurationException :
   * @throws SAXException :
   * @throws IOException :
   * @throws XPathExpressionException :
   * @throws InstantiationException :
   * @throws IllegalAccessException :
   * @throws IllegalArgumentException :
   * @throws InvocationTargetException :
   */
  public final List<T> query(final String configFile, final Class<T> cls)
      throws ParserConfigurationException, SAXException, IOException,
      XPathExpressionException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

    // standard for reading an XML file
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);

    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(configFile);
    Factory<T> factoryItem = new Factory<>();

    XPathExpression expr = null;
    // NodeList nodes = null;
    List<T> itemList = new ArrayList<>();

    // create an XPathFactory
    XPathFactory xFactory = XPathFactory.newInstance();

    // create an XPath object
    XPath xpath = xFactory.newXPath();

    // root
    expr = xpath.compile("count(//item)");
    int size = ((Double) expr.evaluate(doc, XPathConstants.NUMBER)).intValue();
    for (int i = 0; i < size; i++) {
      itemList.add(factoryItem.newInstance(cls));
    }

    setXpathText(doc, xpath, "//item/@date", itemList, factoryItem, cls);
    setXpathText(doc, xpath, "//item/mode/text()", itemList, factoryItem, cls);
    setXpathText(doc, xpath, "//item/unit/text()", itemList, factoryItem, cls);
    setXpathText(doc, xpath, "//item/current/text()", itemList, factoryItem,
        cls);
    setXpathText(doc, xpath, "//item/interactive/text()", itemList, factoryItem,
        cls);

    return itemList;
  }

  /**
   * @param doc :
   * @param xpath :
   * @param path :
   * @param voList :
   * @param factoryItem :
   * @param cls :
   * @throws XPathExpressionException :
   * @throws IllegalAccessException :
   * @throws IllegalArgumentException :
   * @throws InvocationTargetException :
   */
  public final void setXpathText(final Document doc, final XPath xpath,
      final String path, final List<T> voList, final Factory<T> factoryItem,
      final Class<T> cls)
          throws XPathExpressionException, IllegalAccessException,
          IllegalArgumentException, InvocationTargetException {
    XPathExpression expr = xpath.compile(path);
    NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

    String methodName = null;
    if (path.split("@").length == 2) {
      methodName = path.split("@")[1];
    } else if (path.split("/text()").length == 2) {
      String name = path.split("/text()")[0];
      methodName = name.substring(name.lastIndexOf("/") + 1);
    }

    methodName = "set" + methodName.substring(0, 1).toUpperCase()
        + methodName.substring(1);

    for (int i = 0; i < nodes.getLength(); i++) {
      factoryItem.invoke(voList.get(i), cls, methodName,
          new Object[] { nodes.item(i).getNodeValue() });
    }

  }
}
