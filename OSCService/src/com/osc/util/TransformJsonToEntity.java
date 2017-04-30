package com.osc.util;

import java.util.Date;

import com.osc.entity.BaseEntity;
import com.osc.entity.Category;
import com.osc.entity.CategoryDivision;
import com.osc.entity.Customer;
import com.osc.entity.Item;
import com.osc.entity.ItemCroppedDimension;
import com.osc.entity.ItemFieldName;
import com.osc.entity.ItemFieldValue;
import com.osc.entity.SubCategory;
import com.osc.entity.User;
import com.osc.json.BaseJson;
import com.osc.json.CategoryDivisionJson;
import com.osc.json.CategoryJson;
import com.osc.json.CustomerJson;
import com.osc.json.ItemCroppedDimensionJson;
import com.osc.json.ItemFieldNameJson;
import com.osc.json.ItemFieldValueJson;
import com.osc.json.ItemJson;
import com.osc.json.SubCategoryJson;
import com.osc.json.UserJson;

public class TransformJsonToEntity {
	public static void getItem(ItemJson itemJson, Item item) {
		item.setAdminItemInfo(itemJson.getAdminItemInfo());
		item.setDescription(itemJson.getDescription());
		item.setDiscount(itemJson.getDiscount());
		// if (itemJson.getImageSrc() != null) {
		// item.setImageSrc(itemJson.getImageSrc().getBytes());
		// }
		item.setMinQuantityToPurchase(itemJson.getMinQuantityToPurchase());
		item.setMrp(itemJson.getMrp());
		item.setName(itemJson.getName());
		item.setSize(itemJson.getSize());
//		item.setSubCategoryId(itemJson.getSubCategoryId());
		item.setIsNameFieldExists(itemJson.getIsNameFieldExists());
		item.setImageSourceLocation(itemJson.getImageSourceLocation());
		getBaseEntity(itemJson, item);
	}

	public static void getItemFieldValue(ItemFieldValue itemFieldValue, ItemFieldValueJson itemFieldValueJson) {
		itemFieldValue.setItemId(itemFieldValueJson.getItemId());
		itemFieldValue.setItemFieldNameId(itemFieldValueJson.getItemFieldNameId());
		itemFieldValue.setItemFieldValue(itemFieldValueJson.getItemFieldValue());

		getBaseEntity(itemFieldValueJson, itemFieldValue);
	}

	public static void getItemCroppedDimension(ItemCroppedDimension itemCroppedDimension, ItemCroppedDimensionJson itemCroppedDimensionJson) {
		itemCroppedDimension.setCroppedHeight(itemCroppedDimensionJson.getHeight());
		itemCroppedDimension.setItemId(itemCroppedDimensionJson.getItemId());
		itemCroppedDimension.setxPosition(itemCroppedDimensionJson.getLeft());
		itemCroppedDimension.setName(itemCroppedDimensionJson.getName());
		itemCroppedDimension.setyPosition(itemCroppedDimensionJson.getTop());
		itemCroppedDimension.setCroppedWidth(itemCroppedDimensionJson.getWidth());

		getBaseEntity(itemCroppedDimensionJson, itemCroppedDimension);
	}

	public static void getBaseEntity(BaseJson baseJson, BaseEntity baseEntity) {
		baseEntity.setIsDeleted(false);
		if (baseEntity.getId() != null) {
			baseEntity.setUpdatedBy(baseJson.getUpdatedBy());
			baseEntity.setUpdatedOn(new Date());
		} else {
			baseEntity.setCreatedBy(baseJson.getCreatedBy());
			baseEntity.setCreatedOn(new Date());
		}
	}

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

	public static void getItemFieldName(ItemFieldNameJson itemFieldNameJson, ItemFieldName itemFieldName) {
		itemFieldName.setFieldName(itemFieldNameJson.getFieldName());
		if (itemFieldName.getId() != null) {
			itemFieldName.setUpdatedBy(itemFieldNameJson.getUpdatedBy());
			itemFieldName.setUpdatedOn(new Date());
		} else {
			itemFieldName.setCreatedBy(itemFieldNameJson.getCreatedBy());
			itemFieldName.setCreatedOn(new Date());
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
		subCategory.setIsUniqueProduct(subCategoryJson.getIsUniqueProduct());
		subCategory.setShowItemsInHomePage(subCategoryJson.getShowItemsInHomePage());
		
		getBaseEntity(subCategoryJson, subCategory);
//		if (subCategory.getId() != null) {
//			subCategory.setUpdatedBy(subCategoryJson.getUpdatedBy());
//			subCategory.setUpdatedOn(new Date());
//		} else {
//			subCategory.setCreatedBy(subCategoryJson.getCreatedBy());
//			subCategory.setCreatedOn(new Date());
//		}
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
				user.setIsDeleted(Boolean.FALSE);
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
	public static void getCustomer(Customer customer,CustomerJson customerJson) {
		customer.setAddress(customerJson.getAddress());
		customer.setCity(customerJson.getCity());
		customer.setFirstName(customerJson.getFirstName());
		customer.setLastName(customerJson.getLastName());
		customer.setEmailId(customerJson.getEmailId());
		customer.setGender(customerJson.getGender());
		customer.setPincode(customerJson.getPincode());
		customer.setState(customerJson.getState());
		customer.setPhoneNumber(customerJson.getPhoneNumber());
		customer.setLastName(customerJson.getLastName());
		getBaseEntity(customerJson, customer);
	}
}
