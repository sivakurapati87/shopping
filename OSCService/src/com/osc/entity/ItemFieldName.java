package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ItemFieldName")
public class ItemFieldName extends BaseEntity {
	private String fieldName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
