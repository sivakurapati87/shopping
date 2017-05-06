package com.osc.json;


/**
 * 
 * @author siva kurapati
 *
 */
public class CustomerAppliedPromoCodeJson extends BaseJson {
	private Long customerId;
	private Long promoId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

}
