package com.osc.util;

import java.util.Date;

import com.osc.entity.Category;
import com.osc.entity.CategoryDivision;
import com.osc.entity.SubCategory;
import com.osc.entity.User;
import com.osc.json.CategoryDivisionJson;
import com.osc.json.CategoryJson;
import com.osc.json.SubCategoryJson;
import com.osc.json.UserJson;

public class TransformJsonToEntity {
//	public static void getUser(UserJson userJson, User user) {
//		user.setFullName(userJson.getFullName());
//		user.setIsAdmin(userJson.getIsAdmin());
//		user.setEmpType(userJson.getEmpType());
//		user.setPassword(Util.passwordEncryption(userJson.getPassword()));
//		user.setUserName(userJson.getUserName());
//		if (user.getId() != null) {
//			user.setUpdatedBy(userJson.getUpdatedBy());
//			user.setUpdatedOn(new Date());
//		} else {
//			user.setCreatedBy(userJson.getCreatedBy());
//			user.setCreatedOn(new Date());
//		}
//	}

	public static void getCategory(CategoryJson categoryJson, Category category) {
		category.setName(categoryJson.getName());
		if (category.getId() != null) {
			category.setUpdatedBy(categoryJson.getUpdatedBy());
			category.setUpdatedOn(new Date());
		} else {
			category.setCreatedBy(categoryJson.getCreatedBy());
			category.setCreatedOn(new Date());
		}
	}

	public static void getCategoryDivision(CategoryDivisionJson categoryDivisionJson, CategoryDivision categoryDivision) {
		categoryDivision.setName(categoryDivisionJson.getName());
		categoryDivision.setCategoryId(categoryDivisionJson.getCategoryId());
		categoryDivision.setIsDeleted(Boolean.FALSE);
		if (categoryDivision.getId() != null) {
			categoryDivision.setUpdatedBy(categoryDivisionJson.getUpdatedBy());
			categoryDivision.setUpdatedOn(new Date());
		} else {
			categoryDivision.setCreatedBy(categoryDivisionJson.getCreatedBy());
			categoryDivision.setCreatedOn(new Date());
		}
	}

	public static void getSubCategory(SubCategoryJson subCategoryJson, SubCategory subCategory) {
		subCategory.setName(subCategoryJson.getName());
		subCategory.setCategoryDivisionId(subCategoryJson.getCategoryDivisionId());
		subCategory.setIsDeleted(Boolean.FALSE);
		if (subCategory.getId() != null) {
			subCategory.setUpdatedBy(subCategoryJson.getUpdatedBy());
			subCategory.setUpdatedOn(new Date());
		} else {
			subCategory.setCreatedBy(subCategoryJson.getCreatedBy());
			subCategory.setCreatedOn(new Date());
		}
	}

	public static void getUser(UserJson userJson, User user) {
		try {
			if (userJson != null) {
				if (userJson.getId() != null) {
					user.setUpdatedBy(userJson.getUpdatedBy());
					user.setUpdatedOn(new Date());
				} else {
					user.setCreatedOn(new Date());
					user.setCreatedBy(userJson.getCreatedBy());
				}
				user.setIsAdmin(userJson.getIsAdmin());
				user.setIsDeleted(Boolean.TRUE);
				user.setEmpType(userJson.getEmpType());
				user.setEmail(userJson.getEmail());
				user.setFullName(userJson.getFullName());
				user.setMobileNumber(userJson.getMobileNumber());
				user.setPassword(Util.passwordEncryption(userJson.getPassword()));
				user.setUserName(userJson.getUserName());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
