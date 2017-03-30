package com.osc.json;

public class SubCategoryJson extends BaseJson {
	private Integer categoryDivisionId;
	private String name;
	private String categoryDivisionName;
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

	public Integer getCategoryDivisionId() {
		return categoryDivisionId;
	}

	public void setCategoryDivisionId(Integer categoryDivisionId) {
		this.categoryDivisionId = categoryDivisionId;
	}

	public String getCategoryDivisionName() {
		return categoryDivisionName;
	}

	public void setCategoryDivisionName(String categoryDivisionName) {
		this.categoryDivisionName = categoryDivisionName;
	}

}