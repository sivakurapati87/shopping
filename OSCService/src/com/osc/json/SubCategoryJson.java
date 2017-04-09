package com.osc.json;

public class SubCategoryJson extends BaseJson {
	private Long categoryDivisionId;
	private String name;
	private String categoryDivisionName;
	private Boolean isUniqueProduct;
	private CategoryJson category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryJson getCategory() {
		return category;
	}

	public void setCategory(CategoryJson category) {
		this.category = category;
	}

	public Long getCategoryDivisionId() {
		return categoryDivisionId;
	}

	public void setCategoryDivisionId(Long categoryDivisionId) {
		this.categoryDivisionId = categoryDivisionId;
	}

	public String getCategoryDivisionName() {
		return categoryDivisionName;
	}

	public void setCategoryDivisionName(String categoryDivisionName) {
		this.categoryDivisionName = categoryDivisionName;
	}

	public Boolean getIsUniqueProduct() {
		return isUniqueProduct != null ? isUniqueProduct : false;
	}

	public void setIsUniqueProduct(Boolean isUniqueProduct) {
		this.isUniqueProduct = isUniqueProduct;
	}

}