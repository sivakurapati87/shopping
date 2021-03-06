package com.osc.json;

public class SubCategoryJson extends BaseJson {
	private Long categoryDivisionId;
	private String name;
	private String categoryDivisionName;
	private String categoryName;
	private Boolean isUniqueProduct;
	private CategoryJson category;
	private Boolean showItemsInHomePage;
	private String subCategoryImagePath;

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

	public Boolean getShowItemsInHomePage() {
		return showItemsInHomePage != null ? showItemsInHomePage : false;
	}

	public void setShowItemsInHomePage(Boolean showItemsInHomePage) {
		this.showItemsInHomePage = showItemsInHomePage;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryImagePath() {
		return subCategoryImagePath;
	}

	public void setSubCategoryImagePath(String subCategoryImagePath) {
		this.subCategoryImagePath = subCategoryImagePath;
	}

}