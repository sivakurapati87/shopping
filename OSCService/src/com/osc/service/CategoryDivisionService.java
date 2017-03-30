package com.osc.service;

import java.util.List;

import com.osc.json.CategoryDivisionJson;

public interface CategoryDivisionService {
	public void saveOrUpdate(CategoryDivisionJson categoryDivisionJson);

	public List<CategoryDivisionJson> getAllCategoryDivisions();
	
	public CategoryDivisionJson getCategoryDivisionById(Integer id);
	
	public void deleteCategoryDivisionById(Integer id, Integer userId);
}
