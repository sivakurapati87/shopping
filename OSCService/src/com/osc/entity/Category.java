package com.osc.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {
	private String name;
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Where(clause = "isDeleted='false'")
	private List<CategoryDivision> categoryDivisionList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CategoryDivision> getCategoryDivisionList() {
		return categoryDivisionList;
	}

	public void setCategoryDivisionList(List<CategoryDivision> categoryDivisionList) {
		this.categoryDivisionList = categoryDivisionList;
	}
}
