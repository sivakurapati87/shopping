package com.osc.entity;

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
	@Lob
	private byte[] description;
	@Lob
	private byte[] imageSrc;
	private String barCodeNo;
	@Lob
	private byte[] adminItemInfo;
	private Integer minQuantityToPurchase;
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

	public byte[] getDescription() {
		return description;
	}

	public void setDescription(byte[] description) {
		this.description = description;
	}

	public byte[] getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(byte[] imageSrc) {
		this.imageSrc = imageSrc;
	}

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

	public byte[] getAdminItemInfo() {
		return adminItemInfo;
	}

	public void setAdminItemInfo(byte[] adminItemInfo) {
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

}
