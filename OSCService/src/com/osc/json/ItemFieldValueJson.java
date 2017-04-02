package com.osc.json;


public class ItemFieldValueJson extends BaseJson {
	private Long itemFieldNameId;
	private String itemFieldValue;
	private Long itemId;


	public String getItemFieldValue() {
		return itemFieldValue;
	}

	public void setItemFieldValue(String itemFieldValue) {
		this.itemFieldValue = itemFieldValue;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getItemFieldNameId() {
		return itemFieldNameId;
	}

	public void setItemFieldNameId(Long itemFieldNameId) {
		this.itemFieldNameId = itemFieldNameId;
	}
}
