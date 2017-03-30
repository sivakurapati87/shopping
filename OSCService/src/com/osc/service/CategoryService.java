package com.osc.service;

import java.util.List;

import com.osc.json.CategoryJson;

public interface CategoryService {
	public void saveOrUpdate(CategoryJson categoryJson);

	public List<CategoryJson> getAllCategories();
	
	public CategoryJson getCategoryById(Integer id);
	
	public void deleteCategoryById(Integer id,Integer userId);

}
