package com.osc.service;

import java.util.List;

import com.osc.json.SubCategoryJson;

public interface SubCategoryService {
	public void saveOrUpdate(SubCategoryJson subCategoryJson);

	public List<SubCategoryJson> getAllSubCategories();
	
	public List<SubCategoryJson> getAllSubCategoriesWithCategory();
	
	public SubCategoryJson getSubCategoryById(Long id);
	
	public void deleteSubCategoryById(Long id,Long userId);

}
