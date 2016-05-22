package org.nor.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.nor.file.vo.NorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlReader {

	static final Logger logger = LoggerFactory.getLogger(XmlReader.class);

	/**
	 * <pre>
	 * Document 를 NorNode VO 형태로 변경한다.
	 * </pre>
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static NorNode getNoList(Document doc) throws Exception {
		NorNode norNode = new NorNode();
		norNode.setTab("");
		if (doc != null) {
			getNoList(doc.getChildNodes(), norNode);
		}

		return norNode;
	}

	/**
	 * <pre>
	 * nodeList 로 부터 norNode VO 형태로 데이터를 생성한다.
	 * 내부적으로 재귀호출한다.
	 * </pre>
	 * 
	 * @param nodeList
	 * @param norNode
	 * @throws Exception
	 */
	public static void getNoList(NodeList nodeList, NorNode norNode) throws Exception {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				NorNode childNorNode = new NorNode();
				childNorNode.setTab(norNode.getTab() + "\t");
				childNorNode.setType(tempNode.getNodeName());

				norNode.addNode(childNorNode);

				Map<String, Object> attributeMap = new HashMap<String, Object>();
				childNorNode.setAttribute(attributeMap);

				NamedNodeMap tempNodeMap = tempNode.getAttributes();
				if (tempNodeMap != null) {
					for (int index = 0, size = tempNodeMap.getLength(); index < size; index++) {
						if ("id".equals(tempNodeMap.item(index).getNodeName())) {
							childNorNode.setId(tempNodeMap.item(index).getNodeValue());
						} else if ("name".equals(tempNodeMap.item(index).getNodeName())) {
							childNorNode.setName(tempNodeMap.item(index).getNodeValue());
						} else {
							attributeMap.put(tempNodeMap.item(index).getNodeName(),
									tempNodeMap.item(index).getNodeValue());
						}
					}
				}

				getNoList(tempNode.getChildNodes(), childNorNode);
			} else if (tempNode.getNodeType() == Node.ATTRIBUTE_NODE) {
				if (logger.isTraceEnabled())
					logger.trace("ATTRIBUTE_NODE : [{}]", tempNode.getNodeValue());
			} else if (tempNode.getNodeType() == Node.TEXT_NODE) {
				if (logger.isTraceEnabled())
					logger.trace("TEXT_NODE : [{}]", tempNode.getNodeValue());
			} else if (tempNode.getNodeType() == Node.CDATA_SECTION_NODE) {
				norNode.setCData(tempNode.getNodeValue());
			} else {
				if (logger.isTraceEnabled())
					logger.trace("NODE TYYPE : [{}] -- [{}] : [{}]", tempNode.getNodeType(), tempNode.getNodeName(),
							tempNode.getNodeValue());
			}
		}
	}

	/**
	 * <pre>
	 * 특정경로의 파일을 Document 로 parse 한다.
	 * </pre>
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(String path) throws Exception {
		return createDomParser(new FileInputStream(path));
	}

	/**
	 * <pre>
	 * 특정경로의 파일을 parse 해서 NorNode VO로 return 한다.
	 * </pre>
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static NorNode getNorNode(String path) {
		NorNode norNode = null;
		try {
			norNode = getNoList(getDocument(path));
		} catch (Exception e) {
			norNode = new NorNode();
			norNode.setErrMessage(e.getMessage());
		}

//		if (logger.isTraceEnabled())
//			logger.trace("norNode : [{}]", norNode);

		return norNode;
	}

	/**
	 * http://webprogrammer.tistory.com/858
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Document createDomParser(InputStream inputStream) throws Exception {

		// Use factory to create a DOM document
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;

		try { // Get a DOM parser from the Factory
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// logger.error("createDomParser error1", e1);
			throw new Exception(e1);
		}

		try { // Request the DOM parser to parse the file
			return builder.parse(inputStream);
		} catch (SAXException e2) {
			// logger.error("createDomParser error2", e2);
			throw new Exception(e2);
		} catch (IOException e3) {
			// logger.error("createDomParser error3", e3);
			throw new Exception(e3);
		}
	}
}
