package com.osc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.CategoryDao;
import com.osc.entity.Category;
import com.osc.json.CategoryJson;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private Logger LOG = Logger.getLogger(CategoryServiceImpl.class);
	@Autowired
	private CategoryDao categoryDao;

	public void saveOrUpdate(CategoryJson categoryJson) {
		try {
			Category category = null;
			if (categoryJson.getId() != null) {
				category = (Category) categoryDao.getById(Category.class, categoryJson.getId());
			} else {
				category = new Category();
			}
			TransformJsonToEntity.getCategory(categoryJson, category);
			categoryDao.saveOrUpdate(category);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}

	}

	public List<CategoryJson> getAllCategories() {
		List<CategoryJson> categoryJsons = null;
		try {
			StringBuilder sb = new StringBuilder("select c.id,c.name,c.user.userName from Category c where c.isDeleted = false order by c.name ASC");
			List<?> categories = categoryDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				categoryJsons = new ArrayList<CategoryJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					CategoryJson json = new CategoryJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setName(Util.getStringValueOfObj(obj[1]));
					json.setStrCreatedBy(Util.getStringValueOfObj(obj[2]));
					categoryJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return categoryJsons;
	}

	public CategoryJson getCategoryById(Integer id) {
		CategoryJson categoryJson = null;
		try {
			Category category = (Category) categoryDao.getById(Category.class, id);

			if (category != null) {
				categoryJson = new CategoryJson();
				TransformEntityToJson.getCategoryJson(categoryJson, category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return categoryJson;
	}

	public void deleteCategoryById(Integer id, Integer userId) {
		try {
			Category category = (Category) categoryDao.getById(Category.class, id);

			if (category != null) {
				category.setIsDeleted(Boolean.TRUE);
				category.setUpdatedBy(userId);
				category.setUpdatedOn(new Date());
			}
			categoryDao.saveOrUpdate(category);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

}
