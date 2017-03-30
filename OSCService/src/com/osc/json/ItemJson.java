package com.osc.json;

public class ItemJson extends BaseJson {
	private String name;
	private Integer subCategoryId;
	private Float quantity;
	private Double mrp;
	private Double priceAfterDiscount;
	private Double savedAmount;
	private String description;
	private String imageSrc;
	private String barCodeNo;
	private String about;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
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

}
