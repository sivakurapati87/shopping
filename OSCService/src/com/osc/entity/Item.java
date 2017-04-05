package com.osc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item extends BaseEntity {
	private String name;
	private String size;
	private Long subCategoryId;
	private Float quantity;
	private Double mrp;
	private Double discount;
	private Boolean isNameFieldExists;
//	private Double priceAfterDiscount;
//	private Double savedAmount;
	@Column(length=40000)
	private String description;
//	@Lob
//	private String imageSrc;
	private String barCodeNo;
	@Column(length=40000)
	private String adminItemInfo;
	private Integer minQuantityToPurchase;
	private String imageSourceLocation;
	@ManyToOne
	@JoinColumn(name = "subCategoryId", insertable = false, updatable = false)
	private SubCategory subCategory;

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

/*	public Double getPriceAfterDiscount() {
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
	}*/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public String getImageSrc() {
//		return imageSrc;
//	}
//
//	public void setImageSrc(String imageSrc) {
//		this.imageSrc = imageSrc;
//	}

	public String getBarCodeNo() {
		return barCodeNo;
	}

	public void setBarCodeNo(String barCodeNo) {
		this.barCodeNo = barCodeNo;
	}


	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
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

	public String getAdminItemInfo() {
		return adminItemInfo;
	}

	public void setAdminItemInfo(String adminItemInfo) {
		this.adminItemInfo = adminItemInfo;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Boolean getIsNameFieldExists() {
		return isNameFieldExists;
	}

	public void setIsNameFieldExists(Boolean isNameFieldExists) {
		this.isNameFieldExists = isNameFieldExists;
	}

	public String getImageSourceLocation() {
		return imageSourceLocation;
	}

	public void setImageSourceLocation(String imageSourceLocation) {
		this.imageSourceLocation = imageSourceLocation;
	}

}
