package com.osc.service;

import java.util.List;
import java.util.Map;

import com.osc.json.PromoCodeJson;

public interface PromoCodeService {
	public void saveOrUpdate(PromoCodeJson promoCodeJson);

	public List<PromoCodeJson> getAllPromoCodes();
	
	public List<String> getAllPromoCodeImages();
	
	public PromoCodeJson getPromoCodeById(Long id);
	
	public void deletePromoCodeById(Long id,Long userId);
	
	public Map<String,Object> applyPromoCode(String promoCode,Long customerId,Double totalAmount);

}
