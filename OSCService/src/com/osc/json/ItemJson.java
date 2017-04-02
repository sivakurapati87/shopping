package com.osc.json;

import java.util.List;

public class ItemJson extends BaseJson {
	private String name;
	private String size;
	private Long subCategoryId;
	private String subcategory;
	private Float quantity;
	private Double mrp;
	private Double priceAfterDiscount;
	private Double savedAmount;
	private String description;
	private Double discount;
	private String imageSrc;
	private String barCodeNo;
	private Boolean isNameFieldExists;
	private String about;
	private String adminItemInfo;
	private Long[] subCategoryIds;
	private Integer minQuantityToPurchase;
	private List<ItemCroppedDimensionJson> itemCroppedDimensionJsonList;
	private List<ItemFieldValueJson> itemFieldValueJsonList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public Double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(Double priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	public Double getSavedAmount() {
		return savedAmount;
	}

	public void setSavedAmount(Double savedAmount) {
		this.savedAmount = savedAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getBarCodeNo() {
		return barCodeNo;
	}

	public void setBarCodeNo(String barCodeNo) {
		this.barCodeNo = barCodeNo;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getAdminItemInfo() {
		return adminItemInfo;
	}

	public void setAdminItemInfo(String adminItemInfo) {
		this.adminItemInfo = adminItemInfo;
	}

	public Integer getMinQuantityToPurchase() {
		return minQuantityToPurchase;
	}

	public void setMinQuantityToPurchase(Integer minQuantityToPurchase) {
		this.minQuantityToPurchase = minQuantityToPurchase;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public List<ItemCroppedDimensionJson> getItemCroppedDimensionJsonList() {
		return itemCroppedDimensionJsonList;
	}

	public void setItemCroppedDimensionJsonList(List<ItemCroppedDimensionJson> itemCroppedDimensionJsonList) {
		this.itemCroppedDimensionJsonList = itemCroppedDimensionJsonList;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public List<ItemFieldValueJson> getItemFieldValueJsonList() {
		return itemFieldValueJsonList;
	}

	public void setItemFieldValueJsonList(List<ItemFieldValueJson> itemFieldValueJsonList) {
		this.itemFieldValueJsonList = itemFieldValueJsonList;
	}

	public Long[] getSubCategoryIds() {
		return subCategoryIds;
	}

	public void setSubCategoryIds(Long[] subCategoryIds) {
		this.subCategoryIds = subCategoryIds;
	}

	public Boolean getIsNameFieldExists() {
		return isNameFieldExists == null ? false : isNameFieldExists;
	}

	public void setIsNameFieldExists(Boolean isNameFieldExists) {
		this.isNameFieldExists = isNameFieldExists;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

}
