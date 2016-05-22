package org.nor.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexValue {

	static final Logger logger = LoggerFactory.getLogger(RegexValue.class);

	/**
	 * <pre>
	 * 정규식의 특정패턴의 groupIndex 의 값을 가져온다.
	 * </pre>
	 * 
	 * @param text
	 * @param patternStr
	 * @param groupIndex
	 * @return
	 */
	public static String getPatternValue(String text, String patternStr, int groupIndex) {

		if (logger.isTraceEnabled()) {
			logger.trace("patternStr : [{}]", patternStr);
		}

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			return matcher.group(groupIndex);
		} else {
			return null;
		}
	}
	
	/**
	 * <pre>
	 * 정규식의 특정패턴의 groupIndex 의 값을 가져온다.
	 * </pre>
	 * 
	 * @param text
	 * @param patternStr
	 * @param groupIndex
	 * @return
	 */
	public static String[] getPatternValue(String text, String patternStr, int[] groupIndex) {

		if (logger.isTraceEnabled()) {
			logger.trace("patternStr : [{}]", patternStr);
		}

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			String[] rtnStr = new String[groupIndex.length];
			for (int i=0, size=groupIndex.length; i<size; i++) {
				rtnStr[i] = matcher.group(groupIndex[i]);
			}
			return rtnStr;
		} else {
			return null;
		}
	}

}
