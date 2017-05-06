package com.osc.json;

/**
 * 
 * @author siva kurapati
 *
 */
public class PromoCodeJson extends BaseJson {
	private String code;
	private Double applyOnAmount;
	private Double amountToReduce;
	private String promoImagePath;
	private String promoImageBlob;

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

	public String getPromoImageBlob() {
		return promoImageBlob;
	}

	public void setPromoImageBlob(String promoImageBlob) {
		this.promoImageBlob = promoImageBlob;
	}

	public Double getAmountToReduce() {
		return amountToReduce;
	}

	public void setAmountToReduce(Double amountToReduce) {
		this.amountToReduce = amountToReduce;
	}

}
