package com.osc.service;

import com.osc.json.CustomerCartJson;
import com.osc.json.CustomerJson;

public interface CustomerService {
	public CustomerJson saveOrUpdate(CustomerJson customerJson);
	
	public void saveCustomerOrders(CustomerCartJson customerCartJson);
	
//	public List<CategoryJson> getAllCategories();
	
	public CustomerJson getCustomerInfoByEmail(String email);
	
//	public void deleteCategoryById(Long id,Long userId);

}
