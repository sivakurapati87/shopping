package com.osc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.SubCategoryDao;
import com.osc.entity.SubCategory;
import com.osc.json.SubCategoryJson;
import com.osc.util.Constants;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {
	private Logger LOG = Logger.getLogger(SubCategoryServiceImpl.class);
	@Autowired
	private SubCategoryDao subCategoryDao;

	public void saveOrUpdate(SubCategoryJson subCategoryJson) {
		try {
			SubCategory subCategory = null;
			if (subCategoryJson.getId() != null) {
				subCategory = (SubCategory) subCategoryDao.getById(SubCategory.class, subCategoryJson.getId());
			} else {
				subCategory = new SubCategory();
			}
			TransformJsonToEntity.getSubCategory(subCategoryJson, subCategory);
			subCategoryDao.saveOrUpdate(subCategory);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public List<SubCategoryJson> getAllSubCategories() {
		List<SubCategoryJson> subCategoryJsons = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select s.id,s.name,s.categoryDivision.name,s.user.userName,s.isUniqueProduct,s.showItemsInHomePage from SubCategory s where s.isDeleted = false order by s.name ASC");
			List<?> categories = subCategoryDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				subCategoryJsons = new ArrayList<SubCategoryJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					SubCategoryJson json = new SubCategoryJson();
					json.setId((Long) obj[0]);
					json.setName((String) obj[1]);
					json.setCategoryDivisionName((String) obj[2]);
					json.setStrCreatedBy(Util.getStringValueOfObj(obj[3]));
					json.setIsUniqueProduct(Util.getBooleanValueOfObj(obj[4]));
					json.setShowItemsInHomePage(Util.getBooleanValueOfObj(obj[5]));
					subCategoryJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return subCategoryJsons;
	}

	public SubCategoryJson getSubCategoryById(Long id) {
		SubCategoryJson subCategoryJson = null;
		try {
			SubCategory subCategory = (SubCategory) subCategoryDao.getById(SubCategory.class, id);

			if (subCategory != null) {
				subCategoryJson = new SubCategoryJson();
				TransformEntityToJson.getSubCategoryJson(subCategory, subCategoryJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return subCategoryJson;
	}

	public void deleteSubCategoryById(Long id, Long userId) {
		try {
			SubCategory subCategory = (SubCategory) subCategoryDao.getById(SubCategory.class, id);

			if (subCategory != null) {
				subCategory.setIsDeleted(Boolean.TRUE);
				subCategory.setUpdatedBy(userId);
				subCategory.setUpdatedOn(new Date());
			}
			subCategoryDao.saveOrUpdate(subCategory);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public Map<String,List<SubCategoryJson>> getAllSubCategoriesWithCategory() {
		Map<String,List<SubCategoryJson>> subCategoryJsonsMap = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select s.id,s.name,s.categoryDivision.name,s.categoryDivision.category.name,s.isUniqueProduct from SubCategory s where s.isDeleted = false order by s.name ASC");
			List<?> categories = subCategoryDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				subCategoryJsonsMap = new HashMap<String,List<SubCategoryJson>>();
				List<SubCategoryJson> uniqueSubCategories = new ArrayList<SubCategoryJson>();
				List<SubCategoryJson> multipleSubCategories = new ArrayList<SubCategoryJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					SubCategoryJson json = new SubCategoryJson();
					json.setId((Long) obj[0]);
					json.setName(Util.getStringValueOfObj(obj[1])+"("+Util.getStringValueOfObj(obj[3])+" -> "+Util.getStringValueOfObj(obj[2])+")");
					json.setIsUniqueProduct(Util.getBooleanValueOfObj(obj[4]));
//					subCategoryJsons.add(json);
					if(json.getIsUniqueProduct()){
						uniqueSubCategories.add(json);
					}else{
						multipleSubCategories.add(json);
					}
				}
				subCategoryJsonsMap.put(Constants.General.UNIQUE_SubCategories, uniqueSubCategories);
				subCategoryJsonsMap.put(Constants.General.MULTIPLE_SubCategories, multipleSubCategories);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return subCategoryJsonsMap;
	}
	
	public Map<String,Map<String,List<SubCategoryJson>>> allCategoriesWithSubCategory() {
		Map<String,Map<String,List<SubCategoryJson>>> subCategoryJsonsMap = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select s.id,s.name,s.categoryDivision.name,s.categoryDivision.category.name from SubCategory s where s.isDeleted = false order by s.name ASC");
			List<?> categories = subCategoryDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				subCategoryJsonsMap = new HashMap<String,Map<String,List<SubCategoryJson>>>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					SubCategoryJson json = new SubCategoryJson();
					json.setId((Long) obj[0]);
					json.setName(Util.getStringValueOfObj(obj[1]));
					json.setCategoryDivisionName(Util.getStringValueOfObj(obj[2]));
					json.setCategoryName(Util.getStringValueOfObj(obj[3]));
					
					if(subCategoryJsonsMap.get(json.getCategoryName())!=null){
						if(subCategoryJsonsMap.get(json.getCategoryName()).get(json.getCategoryDivisionName())!=null){
							subCategoryJsonsMap.get(json.getCategoryName()).get(json.getCategoryDivisionName()).add(json);
						}else{
							List<SubCategoryJson> list = new ArrayList<SubCategoryJson>();
							list.add(json);
							subCategoryJsonsMap.get(json.getCategoryName()).put(json.getCategoryDivisionName(), list);
						}
					}else{
						Map<String,List<SubCategoryJson>> map = new HashMap<String, List<SubCategoryJson>>();
						List<SubCategoryJson> list = new ArrayList<SubCategoryJson>();
						list.add(json);
						map.put(json.getCategoryDivisionName(), list);
						subCategoryJsonsMap.put(json.getCategoryName(), map);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return subCategoryJsonsMap;
	}
}
