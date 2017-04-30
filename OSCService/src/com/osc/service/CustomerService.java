package com.osc.service;

import com.osc.json.CustomerJson;

public interface CustomerService {
	public void saveOrUpdate(CustomerJson customerJson);

//	public List<CategoryJson> getAllCategories();
	
	public CustomerJson getCustomerInfoByEmail(String email);
	
//	public void deleteCategoryById(Long id,Long userId);

}
