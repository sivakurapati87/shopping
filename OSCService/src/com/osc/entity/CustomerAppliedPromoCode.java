package com.osc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_applied_promocode")
public class CustomerAppliedPromoCode extends BaseEntity {
	private Long customerId;
	private Long promoId;
	@ManyToOne
	@JoinColumn(name = "customerId", insertable = false, updatable = false)
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "promoId", insertable = false, updatable = false)
	private PromoCode promoCode;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public PromoCode getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(PromoCode promoCode) {
		this.promoCode = promoCode;
	}
}
