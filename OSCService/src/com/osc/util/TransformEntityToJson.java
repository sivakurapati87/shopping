package com.osc.util;

import com.osc.entity.BaseEntity;
import com.osc.entity.Category;
import com.osc.entity.CategoryDivision;
import com.osc.entity.Customer;
import com.osc.entity.Item;
import com.osc.entity.ItemCroppedDimension;
import com.osc.entity.ItemFieldName;
import com.osc.entity.ItemFieldValue;
import com.osc.entity.PromoCode;
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
import com.osc.json.PromoCodeJson;
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
	
	public static void getPromoCodeJson(PromoCode promoCode,PromoCodeJson promoCodeJson) {
		promoCodeJson.setCode(promoCode.getCode());
		promoCodeJson.setApplyOnAmount(promoCode.getApplyOnAmount());
		promoCodeJson.setPromoImagePath(promoCode.getPromoImagePath());
		promoCodeJson.setAmountToReduce(promoCode.getAmountToReduce());
		promoCodeJson.setPromoImageBlob(Util.getStringFromLocation(promoCodeJson.getPromoImagePath()));
		getBaseJson(promoCodeJson, promoCode);
	}

	public static void getItemJson(Item item, ItemJson itemJson) {
		itemJson.setAdminItemInfo(new String(item.getAdminItemInfo()));
		itemJson.setDescription(new String(item.getDescription()));
		itemJson.setDiscount(item.getDiscount());
//		itemJson.setImageSrc(new String(item.getImageSrc()));
		itemJson.setMinQuantityToPurchase(item.getMinQuantityToPurchase());
		itemJson.setMrp(item.getMrp());
		itemJson.setName(item.getName());
		itemJson.setIsNameFieldExists(item.getIsNameFieldExists());
		itemJson.setQuantity(item.getQuantity());
		itemJson.setSize(item.getSize());
//		itemJson.setSubCategoryId(item.getSubCategoryId());
//		Long[] subCategories = {item.getSubCategoryId()};
//		itemJson.setSubCategoryIds(subCategories);
		getBaseJson(itemJson, item);
	}
	
	public static void getItemFieldValueJson(ItemFieldValue itemFieldValue, ItemFieldValueJson itemFieldValueJson) {
		itemFieldValueJson.setItemId(itemFieldValue.getItemId());
		itemFieldValueJson.setItemFieldNameId(itemFieldValue.getItemFieldNameId());
		itemFieldValueJson.setItemFieldName(itemFieldValue.getItemFieldName().getFieldName());
		itemFieldValueJson.setItemFieldValue(itemFieldValue.getItemFieldValue());
		
		getBaseJson(itemFieldValueJson, itemFieldValue);
	}
	
	public static void getItemCroppedDimensionJson(ItemCroppedDimension itemCroppedDimension,ItemCroppedDimensionJson itemCroppedDimensionJson) {
		itemCroppedDimensionJson.setHeight(itemCroppedDimension.getCroppedHeight());
		itemCroppedDimensionJson.setItemId(itemCroppedDimension.getItemId());
		itemCroppedDimensionJson.setLeft(itemCroppedDimension.getxPosition());
		itemCroppedDimensionJson.setName(itemCroppedDimension.getName());
		itemCroppedDimensionJson.setTop(itemCroppedDimension.getyPosition());
		itemCroppedDimensionJson.setWidth(itemCroppedDimension.getCroppedWidth());

		getBaseJson(itemCroppedDimensionJson, itemCroppedDimension);
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
	
	public static void getCustomerJson(CustomerJson customerJson,Customer customer) {
		customerJson.setAddress(customer.getAddress());
		customerJson.setCity(customer.getCity());
		customerJson.setFirstName(customer.getFirstName());
		customerJson.setLastName(customer.getLastName());
		customerJson.setEmailId(customer.getEmailId());
		customerJson.setGender(customer.getGender());
		customerJson.setPincode(customer.getPincode());
		customerJson.setState(customer.getState());
		customerJson.setPhoneNumber(customer.getPhoneNumber());
		customerJson.setLastName(customer.getLastName());
		getBaseJson(customerJson, customer);
	}

	public static void getItemFieldNameJson(ItemFieldNameJson itemFieldNameJson, ItemFieldName itemFieldName) {
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
		json.setIsUniqueProduct(subCategory.getIsUniqueProduct());
		json.setShowItemsInHomePage(subCategory.getShowItemsInHomePage());
		getBaseJson(json, subCategory);
	}

}
