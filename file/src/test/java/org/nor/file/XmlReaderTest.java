package org.nor.file;

import org.nor.file.vo.NorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

public class XmlReaderTest {

	static final Logger logger = LoggerFactory.getLogger(XmlReaderTest.class);

	@Test
	public void testgetFileList() throws Exception {
		String targetPath = "E:/새 폴더/TestFile.xml";
		logger.debug("targetPath : [{}]", targetPath);

		Document doc = XmlReader.getDocument(targetPath);
		Assert.assertNotNull(doc, "doc is null");

		NorNode norNode = XmlReader.getNoList(doc);
		Assert.assertNotNull(norNode, "norNode is null");

		logger.debug("norNode : [{}]", norNode);
	}

}
