package org.nor.file.find;

import java.util.List;

import org.nor.file.XmlReader;
import org.nor.file.vo.NorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class XfdlTest {

	static final Logger logger = LoggerFactory.getLogger(XfdlTest.class);

	@Test
	public void testgetFileList() throws Exception {
		String targetPath = "E:/새 폴더/TestFile.xml";
		logger.debug("targetPath : [{}]", targetPath);

		NorNode norNode = XmlReader.getNorNode(targetPath);
		Assert.assertNotNull(norNode, "doc is null");

		List<String> result = Xfdl.getTransaction(norNode);

		Assert.assertNotNull(result, "getTransaction is null");
		logger.debug("result : [{}]", result);
	}

}
