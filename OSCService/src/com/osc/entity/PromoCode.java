package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "promocode")
public class PromoCode extends BaseEntity {
	private String code;
	private Double applyOnAmount;
	private Double amountToReduce;
	private String promoImagePath;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getApplyOnAmount() {
		return applyOnAmount;
	}

	public void setApplyOnAmount(Double applyOnAmount) {
		this.applyOnAmount = applyOnAmount;
	}

	public String getPromoImagePath() {
		return promoImagePath;
	}

	public void setPromoImagePath(String promoImagePath) {
		this.promoImagePath = promoImagePath;
	}

	public Double getAmountToReduce() {
		return amountToReduce;
	}

	public void setAmountToReduce(Double amountToReduce) {
		this.amountToReduce = amountToReduce;
	}

}
