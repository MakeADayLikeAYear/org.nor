package org.nor.file;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FileReaderTest {

	static final Logger logger = LoggerFactory.getLogger(FileReaderTest.class);

	@Test
	public void testgetFileList() {
		String targetPath = "E:/새 폴더/새 폴더";

		logger.debug("targetPath : [{}]", targetPath);

		List<String> result = FileReader.getFileList(targetPath, null);

		Assert.assertNotNull(result, "getFileList is null");
		logger.debug("result File Count : [{}]", result.size());
	}

	@Test
	public void testgetFileList2() {
		String targetPath = "E:/새 폴더";
		String filter = "7z";

		logger.debug("targetPath : [{}] filter : [{}]", targetPath, filter);

		List<String> result = FileReader.getFileList(targetPath, null, filter);

		Assert.assertNotNull(result, "getFileList is null");
		logger.debug("result File Count : [{}]", result.size());
	}
}
