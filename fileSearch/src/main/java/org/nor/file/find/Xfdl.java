package org.nor.file.find;

import java.util.ArrayList;
import java.util.List;

import org.nor.file.util.NorNodeUtil;
import org.nor.file.vo.NorNode;

public class Xfdl {

	/**
	 * <pre>
	 * 특정조건의 값을 검출한다.
	 * </pre>
	 * 
	 * @param node
	 * @return
	 */
	public static List<String> getTransaction(NorNode node) {
		List<NorNode> scriptNode = NorNodeUtil.getSearch("type", "Script", node, null);
		List<String> transactionList = new ArrayList<String>();

		if (scriptNode.size() > 0 && scriptNode.get(0).getCData() != null) {

			String[] tmpArr = scriptNode.get(0).getCData().split("=event");

			for (String tmpStr : tmpArr) {
				String[] tmpArr2 = tmpStr.split("nexacro.do[?]ServiceName=");

				if (tmpArr2.length == 2) {
					String transactionStr = null;
					String[] tmpArr3 = tmpArr2[1].split("&");

					// " + Key + "
					if (tmpArr3[0].split("\\+").length == 3) {
						transactionStr = tmpArr2[0].split(tmpArr3[0].split("\\+")[1].trim())[1].split(";")[0]
								.replaceAll("=|\"| ", "");
					}
					// ab-cdf-EFGHIJK-service
					else {
						transactionStr = tmpArr3[0];
					}

					// " + Value + "
					if (tmpArr3[1].split("\\+").length == 3) {
						transactionStr = transactionStr + "|"
								+ tmpArr2[0].split(tmpArr3[1].split("\\+")[1].trim())[1].split(";")[0]
										.replaceAll("=|\"| ", "");
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
		}
		
		return transactionList;
	}
}
