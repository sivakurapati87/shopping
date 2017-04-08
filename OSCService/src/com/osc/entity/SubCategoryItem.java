package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subcategory_item")
public class SubCategoryItem extends BaseEntity {
	private Long itemId;
	private Long subCategoryId;
	@ManyToOne
	@JoinColumn(name = "itemId", insertable = false, updatable = false)
	private Item item;
	@ManyToOne
	@JoinColumn(name = "subCategoryId", insertable = false, updatable = false)
	private SubCategory subCategory;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

}
