package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ItemFieldValue")
public class ItemFieldValue extends BaseEntity {
	private Long itemFieldNameId;
	private String itemFieldValue;
	private Long itemId;
	@ManyToOne
	@JoinColumn(name = "itemFieldNameId", insertable = false, updatable = false)
	private ItemFieldName itemFieldName;

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

	public ItemFieldName getItemFieldName() {
		return itemFieldName;
	}

	public void setItemFieldName(ItemFieldName itemFieldName) {
		this.itemFieldName = itemFieldName;
	}
}
