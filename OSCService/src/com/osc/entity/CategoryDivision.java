package com.osc.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "category_division")
public class CategoryDivision extends BaseEntity {
	private String name;
	private Long categoryId;
	@ManyToOne
	@JoinColumn(name = "categoryId", insertable = false, updatable = false)
	private Category category;

	@OneToMany(mappedBy = "categoryDivision", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Where(clause = "isDeleted='false'")
	private List<SubCategory> subCategorieList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<SubCategory> getSubCategorieList() {
		return subCategorieList;
	}

	public void setSubCategorieList(List<SubCategory> subCategorieList) {
		this.subCategorieList = subCategorieList;
	}

}
