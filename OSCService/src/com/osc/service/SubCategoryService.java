package com.osc.service;

import java.util.List;

import com.osc.json.SubCategoryJson;

public interface SubCategoryService {
	public void saveOrUpdate(SubCategoryJson subCategoryJson);

	public List<SubCategoryJson> getAllSubCategories();
	
	public SubCategoryJson getSubCategoryById(Integer id);
	
	public void deleteSubCategoryById(Integer id,Integer userId);

}
