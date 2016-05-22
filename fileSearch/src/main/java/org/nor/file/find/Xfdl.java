package org.nor.file.find;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nor.file.util.NorNodeUtil;
import org.nor.file.vo.NorNode;
import org.nor.regex.RegexValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Xfdl {

	static final Logger logger = LoggerFactory.getLogger(Xfdl.class);
	static final String SERVICE = "([a-z]+[-]ext[-][0-9A-Z_a-z]+[-]service)";
	static final String SERVICE2 = "([a-z]+[-][a-z]+[-]ext[-][0-9A-Z_a-z]+[-]service)";
	static final String SERVICE3 = "([0-9A-Z_a-z]+[-]service)";
	static final String SERVICE4 = "([a-z]+[-]ext[-][a-z]+[-][0-9A-Z_a-z]+[-]service)";

	public static List<String> getTransaction(NorNode node) {
		List<NorNode> scriptNode = NorNodeUtil.getSearch("type", "Script", node, null);

		List<String> transactionList = new ArrayList<String>();

		if (logger.isTraceEnabled())
			logger.trace("scriptNode size : [{}]", scriptNode.size());

		if (scriptNode.size() == 0) {
			return transactionList;
		}

		// 2016.05.21 SSH
		// Error Message 가 있는 경우 에러 메시지를 설정하도록 한다.
		if (scriptNode.get(0).getErrMessage() != null) {
			transactionList.add(scriptNode.get(0).getErrMessage());
			return transactionList;
		}

		// tab -> space, ' --> " 로 변환.
		String script = scriptNode.get(0).getCData().replaceAll("\t", " ").replaceAll("\'", "\"").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ");

		if (script == null) {
			transactionList.add("script is null");
			return transactionList;
		}

		String[] tmpArr = script.split("=event");

		if (logger.isTraceEnabled())
			logger.trace("tmpArr : [{}]", tmpArr.length);

		boolean isOne = false; 
		
		for (String tmpStr : tmpArr) {
			String transactionStr = null;
			String transactionStr2 = null;

			if (logger.isTraceEnabled())
				logger.trace("tmpStr : [{}]", tmpStr);

			String[] tmpArr2 = tmpStr.split("nexacro.do[?]ServiceName=");

			if (logger.isTraceEnabled())
				logger.trace("tmpArr2 : [{}]", tmpArr2.length);

			if (tmpArr2.length == 1) {
				// aa-aaa-aaaaaa-aaaaa
				String patternStr = "(" + SERVICE + ")";
				transactionStr = RegexValue.getPatternValue(tmpArr2[0], patternStr, 1);

				if (transactionStr != null) {
					// aaaa = "aaa
					String patternStr_2 = "([a-zA-Z0-9_]+[ |=|]+[\"])([a-zA-Z0-9_]+)$";
					transactionStr2 = RegexValue.getPatternValue(tmpArr2[0], patternStr_2, 2);

					if (transactionStr2 != null) {
						transactionStr = transactionStr + "|" + transactionStr2;
						isOne = true;
					}
				}
			}

			if (transactionStr != null && !transactionList.contains(transactionStr)) {
				transactionList.add(transactionStr);
				continue;
			}

			if (tmpArr2.length == 2) {
				
				if (isOne) {
					isOne = false;
					continue;
				}
				
				String[] tmpArr3 = tmpArr2[1].split("&");

				if (logger.isTraceEnabled()) {
					logger.trace("tmpArr3 : [{}]", tmpArr3.length);
					logger.trace("tmpArr3[0] : [{}]", tmpArr3[0]);
					logger.trace("tmpArr3[1] : [{}]", tmpArr3[1]);
				}

				if (tmpArr3[0].split("\\+").length > 1) {
					String tempKey = null;

					// " + Key + "
					if (tmpArr3[0].split("\\+").length == 3) {
						tempKey = tmpArr3[0].split("\\+")[1].trim();
					}
					
					// = Key + "
					if (tmpArr3[0].split("\\+").length == 2 && tmpArr3[0].split("=").length == 2) {
						tempKey = tmpArr3[0].split("\\+")[0].split("=")[1].trim();
					}

					if (tempKey != null) {

						// var Key = "key"; ---------------------------- START
						String patternStr = "(var[ ]+" + tempKey + "[ |=|\"]+)(" + SERVICE2 + ")";
						transactionStr = RegexValue.getPatternValue(tmpArr2[0], patternStr, 2);

						if (transactionStr == null) {
							patternStr = "(var[ ]+" + tempKey + "[ |=|\"]+)(" + SERVICE3 + ")";
							transactionStr = RegexValue.getPatternValue(tmpArr2[0], patternStr, 2);
						}

						if (transactionStr == null) {
							patternStr = "(var[ ]+" + tempKey + "[ |=|\"]+)(" + SERVICE4 + ")";
							transactionStr = RegexValue.getPatternValue(tmpArr2[0], patternStr, 2);
						}

						if (transactionStr == null) {
							patternStr = "(var[ ]+" + tempKey + "[ |=|\"]+)(" + SERVICE + ")";
							transactionStr = RegexValue.getPatternValue(tmpArr2[0], patternStr, 2);
						}
						// var Key = "key"; ---------------------------- END
						
						// var keyName = "aa-aaa-" + aaaaaaaaaa + "-aaa";
						if (transactionStr == null) {
							String patternStr_2 = "(var[ ]+)(" + tempKey + ")([=| |\"]+)([^\"]+)([ |\"|+]+)([a-zA-Z0-9_]+)([ |\"|+]+)([-]service)";
							String[] patternValue_2 = RegexValue.getPatternValue(script, patternStr_2, new int[] { 4, 6, 8 });

							if (patternValue_2 != null) {
								// var aaaaaaaaaa = this.name;
								String patternStr_3 = "(var[ ]+)(" + patternValue_2[1] + ")([=| ]+)(this.name)";
								String patternValue_3 = RegexValue.getPatternValue(script, patternStr_3, 4);

								if ("this.name".equals(patternValue_3)) {
									patternValue_3 = NorNodeUtil.getSearch("type", "Form", node, null).get(0).getId();
									transactionStr = patternValue_2[0] + patternValue_3 + patternValue_2[2];
									;
								}
							}
							if (patternValue_2 != null && transactionStr == null) {
								// var callpgm = "aaaaa";
								if ("callpgm".equals(patternValue_2[1])) {
									transactionStr = patternValue_2[0] + patternValue_2[1] + patternValue_2[2];
								}
							}

							if (patternValue_2 != null && transactionStr == null) {
								// var aaaaaaaaaa = "aaaaa";
								String patternStr_3 = "(var[ ]+)(" + patternValue_2[1] + ")([=| |\"]+)([^\"]+)";
								String patternValue_3 = RegexValue.getPatternValue(script, patternStr_3, 4);

								if (patternValue_3 != null) {
									transactionStr = patternValue_2[0] + patternValue_3 + patternValue_2[2];
								}
							}
						}
						
						// var aaa = asef
						if (transactionStr == null) {
							String patternStr_4 = "(var[ ]+" + tempKey + "+[ |=]+)([A-Za-z0-9_]+)";
							String patternValue_4 = RegexValue.getPatternValue(tmpArr2[0], patternStr_4, 2);
							transactionStr = patternValue_4;
						}

						// aaaaa=aa-aaa-"+key+"-aaaa
						if (transactionStr == null) {
							String patternStr_5 = "([a-z]{2}[-]ext[-])([\"| |+]+[a-zA-Z0-9_]+[\"| |+]+)([-]service)";
							String[] patternValue_5 = RegexValue.getPatternValue(script, patternStr_5, new int[] { 1, 3 });
							if (patternValue_5 != null) {
								// var aaaaaaaaaa = "aaaaa";
								String patternStr_6 = "(var[ ]+)(" + tempKey + ")([=| |\"]+)([^\"]+)";
								String patternValue_6 = RegexValue.getPatternValue(script, patternStr_6, 4);

								if (patternValue_6 != null) {
									transactionStr = patternValue_5[0] + patternValue_6 + patternValue_5[1];
								}
							}
						}
					}
				}

				if (transactionStr == null) {
					// Service
					transactionStr = RegexValue.getPatternValue(tmpArr3[0], SERVICE, 1);

					if (transactionStr == null) {

						// var key = keyName + "
						String patternStr_1 = "(var[ ]+[A-Za-z0-9_]+[ |=]+)([A-Za-z0-9_]+)([+| ])";
						String patternValue_1 = RegexValue.getPatternValue(tmpArr3[0], patternStr_1, 2);

						if (patternValue_1 != null) {

							// var keyName = "aa-aaa-" + aaaaaaaaaa + "-aaa";
							String patternStr_2 = "(var[ ]+)(" + patternValue_1 + ")([=| |\"]+)([^\"]+)([ |\"|+]+)([a-zA-Z0-9_]+)([ |\"|+]+)([-]service)";
							String[] patternValue_2 = RegexValue.getPatternValue(script, patternStr_2, new int[] { 4, 6, 8 });

							if (patternValue_2 != null) {
								// var aaaaaaaaaa = "aaaaa";
								String patternStr_3 = "(var[ ]+)(" + patternValue_2[1] + ")([=| |\"]+)([^\"]+)";
								String patternValue_3 = RegexValue.getPatternValue(script, patternStr_3, 4);

								if (patternValue_3 != null) {
									transactionStr = patternValue_2[0] + patternValue_3 + patternValue_2[2];
								}
							}
						}

						if (transactionStr == null) {
							System.out.println("========================== error key1//" + tmpArr3[0]);
							logger.error("========================== error key1//" + tmpArr3[0]);
						} else {

							String patternStr = "(\")([a-z]{2}[-]ext[-][A-Z0-9_]+[-]service)";

							Pattern pattern = Pattern.compile(patternStr);
							Matcher matcher = pattern.matcher(tmpArr3[0]);

							if (matcher.find()) {
								transactionStr = matcher.group(2);
							} else {
								transactionStr = tmpArr3[0];
							}
						}
					}
				}

				// " + Value + "
				if (tmpArr3[1].split("\\+").length == 3) {
					String temp1 = tmpArr3[1].split("\\+")[1].trim();
					// String patternStr = "(var[ ]+" + temp1 + "[ |=]+[\"][ ]+)([^\"]+)";
					String patternStr = "(var[ ]+" + temp1 + "[ |=]+[\"])([^\"]+)";

					transactionStr2 = RegexValue.getPatternValue(tmpArr2[0], patternStr, 2);

					// var aaaa = this.aaaa;
					if (transactionStr2 == null) {
						String patternStr_2 = "(var[ ]+" + temp1 + "[ |=]+)(this.[^ ]+)([;])";
						String patternValue_2 = RegexValue.getPatternValue(tmpArr2[0], patternStr_2, 2);

						if (patternValue_2 != null) {
							String patternStr_3 = "(" + patternValue_2 + "[ |=]+)(this.[^ ]+)([;])";
							String patternValue_3 = RegexValue.getPatternValue(tmpArr2[0], patternStr_3, 2);
							if (patternValue_3 != null) {
								transactionStr2 = patternValue_3;
							}
						}
					}

					// var aaaa = aaaa
					if (transactionStr2 == null) {
						String patternStr_2 = "(var[ ]+" + temp1 + "[ |=]+)([^ |^;]+)";
						transactionStr2 = RegexValue.getPatternValue(tmpArr2[0], patternStr_2, 2);
					}

					// aaaa"+aaa+"
					if (transactionStr2 == null && tmpArr3[1].split("\\+")[0].trim().endsWith("\"")) {
						transactionStr2 = tmpArr3[1].split("\\+")[0].replaceAll(" |\"", "");
					}

					if (transactionStr2 == null) {
						System.out.println("========================== error vlaue");
					}

					transactionStr = transactionStr + "|" + transactionStr2;
				}
				// event
				else {
					transactionStr = transactionStr + "|" + tmpArr3[1];
				}

				if (!transactionList.contains(transactionStr)) {
					transactionList.add(transactionStr);
				}
			}
		}

		return transactionList;
	}

	// /**
	// * <pre>
	// * 특정조건의 값을 검출한다.
	// * </pre>
	// *
	// * @param node
	// * @return
	// */
	// public static List<String> getTransaction2(NorNode node) {
	// List<NorNode> scriptNode = NorNodeUtil.getSearch("type", "Script", node, null);
	//
	// List<String> transactionList = new ArrayList<String>();
	//
	// if (logger.isTraceEnabled())
	// logger.trace("scriptNode size : [{}]", scriptNode.size());
	//
	// if (scriptNode.size() > 0) {
	//
	// // if (logger.isTraceEnabled())
	// // logger.trace("scriptNode getCData : [{}]", scriptNode.get(0).getCData());
	//
	// // 2016.05.21 SSH
	// // Error Message 가 있는 경우 에러 메시지를 설정하도록 한다.
	// if (scriptNode.get(0).getErrMessage() != null) {
	// transactionList.add(scriptNode.get(0).getErrMessage());
	//
	// } else if (scriptNode.get(0).getCData() != null) {
	//
	// String[] tmpArr = scriptNode.get(0).getCData().split("=event");
	//
	// if (logger.isTraceEnabled())
	// logger.trace("tmpArr : [{}]", tmpArr.length);
	//
	// for (String tmpStr : tmpArr) {
	//
	// logger.trace("tmpStr : [{}]", tmpStr);
	//
	// String[] tmpArr2 = tmpStr.split("nexacro.do[?]ServiceName=");
	//
	// if (logger.isTraceEnabled())
	// logger.trace("tmpArr2 : [{}]", tmpArr2.length);
	//
	// if (tmpArr2.length == 2) {
	// String transactionStr = null;
	// String transactionStr2 = null;
	// String[] tmpArr3 = tmpArr2[1].split("&");
	//
	// if (logger.isTraceEnabled()) {
	// logger.trace("tmpArr3 : [{}]", tmpArr3.length);
	// logger.trace("tmpArr3[0] : [{}]", tmpArr3[0]);
	// logger.trace("tmpArr3[1] : [{}]", tmpArr3[1]);
	// }
	// // " + Key + "
	// if (tmpArr3[0].split("\\+").length == 3) {
	// System.out.println(tmpArr2[0]);
	// System.out.println("-----------------" + tmpArr3[0].split("\\+")[1].trim());
	// transactionStr = tmpArr2[0].split(tmpArr3[0].split("\\+")[1].trim())[1].split(";")[0]
	// .replaceAll("=|\"| ", "");
	// }
	// // ab-cdf-EFGHIJK-service
	// else {
	//
	// Pattern pattern1 = Pattern.compile("([a-z]+[-]ext[-][0-9A-Z]+[-]service)");
	// Matcher matcher1 = pattern1.matcher(tmpArr3[0]);
	//
	// if (matcher1.find()) {
	// transactionStr = matcher1.group(1);
	// } else {
	// String[] tmpArr4 = tmpArr3[0].split("=");
	// if (tmpArr4.length == 2) {
	//
	// // service + "
	// tmpArr4[1] = tmpArr4[1].replaceAll(" |[+]|\"", "");
	// String patternStr2 = "(" + tmpArr4[1] + ")([= \"]+)(.*)([-]service)([\"])";
	//
	// if (logger.isTraceEnabled()) {
	// logger.trace("patternStr2 : [{}]", patternStr2);
	// }
	//
	// Pattern pattern2 = Pattern.compile(patternStr2);
	// Matcher matcher2 = pattern2.matcher(scriptNode.get(0).getCData());
	//
	// if (matcher2.find()) {
	// String temp = matcher2.group(3) + matcher2.group(4);
	// temp = temp.replaceAll(" ", " ");
	//
	// if (logger.isTraceEnabled()) {
	// logger.trace("temp 1 : [{}]", temp);
	// }
	//
	// // " + Key + "
	// if (temp.split("\\+").length == 3) {
	//
	// String temp2 = temp.split("\\+")[1].trim();
	// if (logger.isTraceEnabled()) {
	// logger.trace("temp 2 : [{}]", temp2);
	// }
	//
	// String patternStr3 = "(var[ ]+)(" + temp2 + ")([= \"]+)([^\"]+)";
	//
	// if (logger.isTraceEnabled()) {
	// logger.trace("patternStr3 : [{}]", patternStr3);
	// }
	//
	// Pattern pattern3 = Pattern.compile(patternStr3);
	// Matcher matcher3 = pattern3.matcher(scriptNode.get(0).getCData());
	//
	// if (matcher3.find()) {
	// String temp3 = matcher3.group(4);
	// if (logger.isTraceEnabled()) {
	// logger.trace("temp 3 : [{}]", temp3);
	// }
	//
	// if (temp3.startsWith("this.name")) {
	// temp3 = NorNodeUtil.getSearch("type", "Form", node, null).get(0)
	// .getId();
	// }
	//
	// transactionStr = temp.replaceAll(" |\"|\\+", "").replaceAll(temp2,
	// temp3);
	// }
	//
	// } else {
	// transactionStr = tmpArr4[1];
	// }
	// } else {
	// transactionStr = tmpArr4[1];
	// }
	// } else {
	//
	// String patternStr = "(\")([a-z]{2}[-]ext[-][A-Z0-9_]+[-]service)";
	//
	// Pattern pattern = Pattern.compile(patternStr);
	// Matcher matcher = pattern.matcher(tmpArr3[0]);
	//
	// if (matcher.find()) {
	// transactionStr = matcher.group(2);
	// } else {
	// transactionStr = tmpArr3[0];
	// }
	// }
	// }
	// }
	//
	// // " + Value + "
	// if (tmpArr3[1].split("\\+").length == 3) {
	//
	// String temp1 = tmpArr3[1].split("\\+")[1].trim();
	// String patternStr = "(var)([ ]+)(" + temp1 + ")([ |=]+)([\"])([^\"]+)";
	// if (logger.isTraceEnabled()) {
	// logger.trace("temp A 1 : [{}]", temp1);
	// logger.trace("patternStr A 1 : [{}]", patternStr);
	// }
	//
	// Pattern pattern = Pattern.compile(patternStr);
	// Matcher matcher = pattern.matcher(tmpArr2[0]);
	//
	// if (matcher.find()) {
	// transactionStr2 = tmpArr2[0].split(tmpArr3[1].split("\\+")[1].trim())[1].split(";")[0]
	// .replaceAll("=| ", "");
	//
	// if (transactionStr2.split("\"").length > 1) {
	// transactionStr2 = transactionStr2.split("\"")[1];
	// } else {
	// transactionStr2 = transactionStr2.replaceAll("\"", "");
	// }
	//
	// } else {
	// transactionStr2 = tmpArr3[1].split("\\+")[0].replaceAll("\"| ", "");
	// }
	//
	// transactionStr = transactionStr + "|" + transactionStr2;
	// }
	// // event
	// else {
	// transactionStr = transactionStr + "|" + tmpArr3[1];
	// }
	//
	// if (!transactionList.contains(transactionStr)) {
	// transactionList.add(transactionStr);
	// }
	// }
	// }
	// }
	// }
	//
	// return transactionList;
	// }
}
