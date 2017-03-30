package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item extends BaseEntity {
	private String name;
	private Integer subCategoryId;
	private Float quantity;
	private Double mrp;
	private Double priceAfterDiscount;
	private Double savedAmount;
	private byte[] description;
	private byte[] imageSrc;
	private String barCodeNo;
	private byte[] about;
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

	public byte[] getAbout() {
		return about;
	}

	public void setAbout(byte[] about) {
		this.about = about;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

}
