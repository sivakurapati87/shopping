package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ItemSpecificationFieldValues")
public class ItemSpecificationFieldValues extends BaseEntity {
	private Integer itemFieldInfoId;
	private String itemFieldValue;
	private Integer itemId;
	@ManyToOne
	@JoinColumn(name = "itemFieldInfoId", insertable = false, updatable = false)
	private ItemSpecificationFieldNames itemSpecificationFieldName;

	public ItemSpecificationFieldNames getItemSpecificationFieldName() {
		return itemSpecificationFieldName;
	}

	public void setItemSpecificationFieldName(ItemSpecificationFieldNames itemSpecificationFieldName) {
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
