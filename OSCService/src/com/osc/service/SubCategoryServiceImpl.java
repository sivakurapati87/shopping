package com.osc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.SubCategoryDao;
import com.osc.entity.SubCategory;
import com.osc.json.SubCategoryJson;
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
			StringBuilder sb = new StringBuilder("select s.id,s.name,s.categoryDivision.name,s.user.userName from SubCategory s where s.isDeleted = false order by s.name ASC");
			List<?> categories = subCategoryDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				subCategoryJsons = new ArrayList<SubCategoryJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					SubCategoryJson json = new SubCategoryJson();
					json.setId((Integer) obj[0]);
					json.setName((String) obj[1]);
					json.setCategoryDivisionName((String)obj[2]);
					json.setStrCreatedBy(Util.getStringValueOfObj(obj[3]));
					subCategoryJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return subCategoryJsons;
	}
	
	public SubCategoryJson getSubCategoryById(Integer id) {
		SubCategoryJson subCategoryJson = null;
		try {
			SubCategory subCategory = (SubCategory) subCategoryDao.getById(SubCategory.class, id);

			if (subCategory != null) {
				subCategoryJson = new SubCategoryJson();
				TransformEntityToJson.getSubCategoryJson(subCategory,subCategoryJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return subCategoryJson;
	}

	public void deleteSubCategoryById(Integer id, Integer userId) {
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

}
