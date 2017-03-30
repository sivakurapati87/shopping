package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ItemSpecificationFieldNames")
public class ItemSpecificationFieldNames extends BaseEntity {
	private String fieldName;
	private Integer subCategoryTypeId;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getSubCategoryTypeId() {
		return subCategoryTypeId;
	}

	public void setSubCategoryTypeId(Integer subCategoryTypeId) {
		this.subCategoryTypeId = subCategoryTypeId;
	}
}
