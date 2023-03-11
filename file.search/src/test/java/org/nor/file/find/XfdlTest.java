package org.nor.file.find;

import java.util.List;

import org.nor.file.FileReader;
import org.nor.file.XmlReader;
import org.nor.file.vo.NorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class XfdlTest {

	static final Logger logger = LoggerFactory.getLogger(XfdlTest.class);

//	@Test
	public void testgetTransaction() throws Exception {
		String targetPath = "E:/새 폴더/TestFile.xml";
		logger.debug("targetPath : [{}]", targetPath);

		NorNode norNode = XmlReader.getNorNode(targetPath);
		Assert.assertNotNull(norNode, "doc is null");

		List<String> result = Xfdl.getTransaction(norNode);

		Assert.assertNotNull(result, "getTransaction is null");
		logger.debug("result : [{}]", result);
	}

	@Test
	public void testgetTransaction2() throws Exception {
		String targetPath = "E:/새 폴더/새폴더2";
		logger.debug("targetPath : [{}]", targetPath);
		
		List<String> fileList = FileReader.getFileList(targetPath, null, "xfdl");
		Assert.assertNotNull(fileList, "fileList is null");
		
		for (String path : fileList) {
			NorNode norNode = XmlReader.getNorNode(path);
			Assert.assertNotNull(norNode, "doc is null");
			
			logger.debug("path : [{}]", path);
			List<String> result = Xfdl.getTransaction(norNode);

			Assert.assertNotNull(result, "getTransaction is null");
			logger.debug("path : [{}], [{}]", path, result);
		}
	}
}
