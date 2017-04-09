package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subcategory")
public class SubCategory extends BaseEntity{
	private Long categoryDivisionId;
	private String name;
	@ManyToOne
	@JoinColumn(name = "categoryDivisionId", insertable = false, updatable = false)
	private CategoryDivision categoryDivision;

	private Boolean isUniqueProduct;
	/*@OneToMany(mappedBy = "subCategory", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Where(clause = "isDeleted='false'")
	private List<Item> itemsList;*/


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCategoryDivisionId() {
		return categoryDivisionId;
	}

	public void setCategoryDivisionId(Long categoryDivisionId) {
		this.categoryDivisionId = categoryDivisionId;
	}
	public CategoryDivision getCategoryDivision() {
		return categoryDivision;
	}

	public void setCategoryDivision(CategoryDivision categoryDivision) {
		this.categoryDivision = categoryDivision;
	}
	public Boolean getIsUniqueProduct() {
		return isUniqueProduct;
	}
	public void setIsUniqueProduct(Boolean isUniqueProduct) {
		this.isUniqueProduct = isUniqueProduct;
	}
	
}