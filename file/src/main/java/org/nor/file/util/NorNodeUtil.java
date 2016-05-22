package org.nor.file.util;

import java.util.ArrayList;
import java.util.List;

import org.nor.file.vo.NorNode;

public class NorNodeUtil {

	/**
	 * <pre>
	 * NorNode 내부에 특정 값을 가지는 값을 찾는다.
	 * - key : id, name, type
	 * 
	 * 재귀호출하여 특정 값을 찾는다.
	 * - NorNode - getNode(NorNode List)
	 * </pre>
	 * 
	 * @param key
	 * @param value
	 * @param node
	 * @param rtnNode
	 * @return
	 */
	public static List<NorNode> getSearch(String key, String value, NorNode node, List<NorNode> rtnNode) {
		if (rtnNode == null) {
			rtnNode = new ArrayList<NorNode>();
		}
		
		// 2016.05.21 SSH
		// Error Message 가 있는 경우 더이상 재귀호출하지 않는다.
		if (node.getErrMessage() != null && !"".equals(node.getErrMessage())) {
			rtnNode.add(node);
			return rtnNode;
		}

		if (value != null) {
			switch (key) {
			case "type":
				if (value.equals(node.getType())) {
					rtnNode.add(node);
				}
				break;

			case "id":
				if (value.equals(node.getId())) {
					rtnNode.add(node);
				}
				break;

			case "name":
				if (value.equals(node.getName())) {
					rtnNode.add(node);
				}
				break;
			}
		}

		if (node.getNode() != null) {
			for (NorNode tmpNode : node.getNode()) {
				getSearch(key, value, tmpNode, rtnNode);
			}
		}

		return rtnNode;
	}
}
