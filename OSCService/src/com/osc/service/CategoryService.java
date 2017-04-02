package com.osc.service;

import java.util.List;

import com.osc.json.CategoryJson;

public interface CategoryService {
	public void saveOrUpdate(CategoryJson categoryJson);

	public List<CategoryJson> getAllCategories();
	
	public CategoryJson getCategoryById(Long id);
	
	public void deleteCategoryById(Long id,Long userId);

}
