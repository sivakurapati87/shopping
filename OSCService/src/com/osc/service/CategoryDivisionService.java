package com.osc.service;

import java.util.List;

import com.osc.json.CategoryDivisionJson;

public interface CategoryDivisionService {
	public void saveOrUpdate(CategoryDivisionJson categoryDivisionJson);

	public List<CategoryDivisionJson> getAllCategoryDivisions();
	
	public CategoryDivisionJson getCategoryDivisionById(Long id);
	
	public void deleteCategoryDivisionById(Long id, Long userId);
}
