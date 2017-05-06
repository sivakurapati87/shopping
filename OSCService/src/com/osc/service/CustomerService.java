package com.osc.service;

import java.util.Date;
import java.util.List;

import com.osc.json.CustomerCartJson;
import com.osc.json.CustomerJson;

public interface CustomerService {
	public CustomerJson saveOrUpdate(CustomerJson customerJson);
	
	public void saveCustomerOrders(CustomerCartJson customerCartJson);
	
	public List<CustomerCartJson> getAllCustomerOrders(Date fromDate,Date toDate,String status,Integer from,Integer To);
	
	public Long findNoOfProducts(Date fromDate,Date toDate,String status);
	
	public void changeCartStatus(String status,Long cartId);
	
	public CustomerJson getCustomerInfoByEmail(String email);
	
//	public void deleteCategoryById(Long id,Long userId);

}
