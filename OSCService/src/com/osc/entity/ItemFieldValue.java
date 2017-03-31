package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ItemFieldValue")
public class ItemFieldValue extends BaseEntity {
	private Integer itemFieldInfoId;
	private String itemFieldValue;
	private Integer itemId;
	@ManyToOne
	@JoinColumn(name = "itemFieldInfoId", insertable = false, updatable = false)
	private ItemFieldName itemSpecificationFieldName;

	public ItemFieldName getItemSpecificationFieldName() {
		return itemSpecificationFieldName;
	}

	public void setItemSpecificationFieldName(ItemFieldName itemSpecificationFieldName) {
		this.itemSpecificationFieldName = itemSpecificationFieldName;
	}

	@ManyToOne
	@JoinColumn(name = "itemId", insertable = false, updatable = false)
	private Item item;

	public Integer getItemFieldInfoId() {
		return itemFieldInfoId;
	}

	public void setItemFieldInfoId(Integer itemFieldInfoId) {
		this.itemFieldInfoId = itemFieldInfoId;
	}

	public String getItemFieldValue() {
		return itemFieldValue;
	}

	public void setItemFieldValue(String itemFieldValue) {
		this.itemFieldValue = itemFieldValue;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
