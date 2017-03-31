package com.osc.util;

import com.osc.entity.BaseEntity;
import com.osc.entity.Category;
import com.osc.entity.CategoryDivision;
import com.osc.entity.ItemFieldName;
import com.osc.entity.SubCategory;
import com.osc.entity.User;
import com.osc.json.BaseJson;
import com.osc.json.CategoryDivisionJson;
import com.osc.json.CategoryJson;
import com.osc.json.ItemFieldNameJson;
import com.osc.json.SubCategoryJson;
import com.osc.json.UserJson;

public class TransformEntityToJson {
	public static void getUserJson(User user, UserJson userJson) {
		userJson.setFullName(user.getFullName());
		userJson.setIsAdmin(user.getIsAdmin());
		userJson.setEmail(user.getEmail());
		userJson.setEmpType(user.getEmpType());
		userJson.setMobileNumber(user.getMobileNumber());
		userJson.setUserName(user.getUserName());
		getBaseJson(userJson, user);
	}

	public static void getBaseJson(BaseJson baseJson, BaseEntity baseEntity) {
		baseJson.setId(baseEntity.getId());
		baseJson.setCreatedBy(baseEntity.getCreatedBy());
		baseJson.setCreatedOn(Util.convertDateToString(baseEntity.getCreatedOn()));
		baseJson.setUpdatedBy(baseEntity.getUpdatedBy());
		baseJson.setUpdatedOn(Util.convertDateToString(baseEntity.getUpdatedOn()));
	}

	public static void getCategoryJson(CategoryJson categoryJson, Category category) {
		categoryJson.setName(category.getName());
		getBaseJson(categoryJson, category);
	}
	
	public static void getItemFieldNameJson(ItemFieldNameJson itemFieldNameJson,ItemFieldName itemFieldName) {
		itemFieldNameJson.setFieldName(itemFieldName.getFieldName());
		getBaseJson(itemFieldNameJson, itemFieldName);
	}

	public static void getCategoryDivisionJson(CategoryDivision categoryDivision, CategoryDivisionJson categoryDivisionJson) {
		categoryDivisionJson.setName(categoryDivision.getName());
		categoryDivisionJson.setCategoryId(categoryDivision.getCategoryId());
		getBaseJson(categoryDivisionJson, categoryDivision);
	}

	public static void getSubCategoryJson(SubCategory subCategory, SubCategoryJson json) {
		json.setName(subCategory.getName());
		json.setCategoryDivisionId(subCategory.getCategoryDivisionId());
		getBaseJson(json, subCategory);
	}

}
