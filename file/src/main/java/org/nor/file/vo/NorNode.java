package org.nor.file.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NorNode {

	private String type;
	private String id;
	private String name;
	private Map<String, Object> attribute;
	private String text;
	private List<NorNode> node;
	private String tab;
	private String cData;
	private String errMessage;

	public NorNode() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeId() {
		String returnId = (this.id == null ? "" : this.id) + "|" + (this.name == null ? "" : this.name);

		if (returnId.startsWith("|") || returnId.endsWith("|")) {
			returnId = returnId.replaceAll("[|]", "");
		}

		return returnId;
	}

	public Map<String, Object> getAttribute() {
		return attribute;
	}

	public void setAttribute(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<NorNode> getNode() {
		return node;
	}

	public void setNode(List<NorNode> node) {
		this.node = node;
	}

	public void addNode(NorNode node) {
		if (this.node == null) {
			this.node = new ArrayList<NorNode>();
		}
		this.node.add(node);
	}

	public String getCData() {
		return cData;
	}

	public void setCData(String cData) {
		this.cData = cData;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}
	
	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String toString() {
		return String.format("\r\n" + tab + " type:[%10s] id/name:[%20s] attribute:[%15s] cData:[%s]", type,
				getNodeId(), attribute, cData) + (node == null ? "" : node);
		// return ToStringBuilder.reflectionToString(this);
	}
}
