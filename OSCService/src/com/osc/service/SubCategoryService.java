package com.osc.service;

import java.util.List;
import java.util.Map;

import com.osc.json.SubCategoryJson;

public interface SubCategoryService {
	public void saveOrUpdate(SubCategoryJson subCategoryJson);

	public List<SubCategoryJson> getAllSubCategories();
	
	public Map<String,List<SubCategoryJson>> getAllSubCategoriesWithCategory();
	
	public Map<String,Map<String,List<SubCategoryJson>>> allCategoriesWithSubCategory();
	
	public SubCategoryJson getSubCategoryById(Long id);
	
	public void deleteSubCategoryById(Long id,Long userId);

}
