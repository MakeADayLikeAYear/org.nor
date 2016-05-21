package org.nor.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

	/**
	 * <pre>
	 * 특정 경로 내에 존재하는 File 경로 들의 목록을 생성한다.
	 * 
	 * </pre>
	 * @param path
	 * @param rtnFileList
	 * @return
	 */
	public static List<String> getFileList(String path, List<String> rtnFileList) {
		return getFileList(path, rtnFileList, null);
	}
	
	/**
	 * <pre>
	 * 특정 경로 내에 존재하는 File 경로 들의 목록을 생성한다.
	 * 단 file 경로 명에는 chkNameStr 의 명칭이포함되어야 한다. 
	 * 
	 * </pre>
	 * @param path
	 * @param rtnFileList
	 * @param chkNameStr
	 * @return
	 */
	public static List<String> getFileList(String path, List<String> rtnFileList, String chkNameStr) {
		if (rtnFileList == null) {
			rtnFileList = new ArrayList<>();
		}

		File file = new File(path);

		if (file.isDirectory()) {
			for (File tmpFile : file.listFiles()) {
				getFileList(tmpFile.getPath(), rtnFileList, chkNameStr);
			}

		} else if (file.isFile()) {
			if (chkNameStr == null || file.getName().indexOf(chkNameStr) > -1) {
				rtnFileList.add(file.getPath());
			}
		}

		return rtnFileList;
	}
}
