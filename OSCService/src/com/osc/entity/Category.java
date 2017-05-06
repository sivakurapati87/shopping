package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {
	private String name;
//	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
//	@Fetch(FetchMode.SELECT)
//	@Where(clause = "isDeleted='false'")
//	private List<CategoryDivision> categoryDivisionList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public List<CategoryDivision> getCategoryDivisionList() {
//		return categoryDivisionList;
//	}
//
//	public void setCategoryDivisionList(List<CategoryDivision> categoryDivisionList) {
//		this.categoryDivisionList = categoryDivisionList;
//	}
}
