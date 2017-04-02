package com.osc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.CategoryDivisionDao;
import com.osc.entity.CategoryDivision;
import com.osc.json.CategoryDivisionJson;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class CategoryDivisionServiceImpl implements CategoryDivisionService {
	private Logger LOG = Logger.getLogger(CategoryDivisionServiceImpl.class);
	@Autowired
	private CategoryDivisionDao categoryDivisionDao;

	public void saveOrUpdate(CategoryDivisionJson categoryDivisionJson) {
		try {
			CategoryDivision categoryDivision = null;
			if (categoryDivisionJson.getId() != null) {
				categoryDivision = (CategoryDivision) categoryDivisionDao.getById(CategoryDivision.class, categoryDivisionJson.getId());
			} else {
				categoryDivision = new CategoryDivision();
			}
			TransformJsonToEntity.getCategoryDivision(categoryDivisionJson, categoryDivision);
			categoryDivisionDao.saveOrUpdate(categoryDivision);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public List<CategoryDivisionJson> getAllCategoryDivisions() {
		List<CategoryDivisionJson> categoryDivisionJsons = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select c.id,c.name,c.category.name,c.user.userName from CategoryDivision c where c.isDeleted = false order by c.name ASC");
			List<?> categories = categoryDivisionDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				categoryDivisionJsons = new ArrayList<CategoryDivisionJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					CategoryDivisionJson json = new CategoryDivisionJson();
					json.setId((Long) obj[0]);
					json.setName((String) obj[1]);
					json.setCategoryName((String) obj[2]);
					json.setStrCreatedBy(Util.getStringValueOfObj(obj[3]));
					categoryDivisionJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return categoryDivisionJsons;
	}

	public CategoryDivisionJson getCategoryDivisionById(Long id) {
		CategoryDivisionJson categoryDivisionJson = null;
		try {
			CategoryDivision categoryDivision = (CategoryDivision) categoryDivisionDao.getById(CategoryDivision.class, id);

			if (categoryDivision != null) {
				categoryDivisionJson = new CategoryDivisionJson();
				TransformEntityToJson.getCategoryDivisionJson(categoryDivision, categoryDivisionJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return categoryDivisionJson;
	}

	public void deleteCategoryDivisionById(Long id, Long userId) {
		try {
			CategoryDivision categoryDivision = (CategoryDivision) categoryDivisionDao.getById(CategoryDivision.class, id);

			if (categoryDivision != null) {
				categoryDivision.setIsDeleted(Boolean.TRUE);
				categoryDivision.setUpdatedBy(userId);
				categoryDivision.setUpdatedOn(new Date());
			}
			categoryDivisionDao.saveOrUpdate(categoryDivision);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

}
