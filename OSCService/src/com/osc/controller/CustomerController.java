package com.osc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osc.json.CategoryJson;
import com.osc.json.CustomerCartJson;
import com.osc.json.CustomerJson;
import com.osc.service.CustomerService;
import com.osc.util.HashCodeGenerator;
import com.osc.util.Util;

@RestController
@RequestMapping("CustomerController")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustomerController {
	private Logger LOG = Logger.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/getHashKeyWithTransactionNumber", method = RequestMethod.POST)
	public ResponseEntity<CustomerJson> getHashKeyWithTransactionNumber(@RequestBody CustomerJson customerJson, HttpServletRequest request) {
//		if (Util.getLoginUserId(request) != null) {
		try {
			HashCodeGenerator.generatehash(customerJson);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<CustomerJson>(customerJson,HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<CustomerJson>(customerJson,HttpStatus.OK);
//		} else {
//			LOG.error("User must login");
//			return new ResponseEntity(HttpStatus.FORBIDDEN);
//		}
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<CustomerJson> saveOrUpdate(@RequestBody CustomerJson customerJson, HttpServletRequest request) {
//		if (Util.getLoginUserId(request) != null) {
		try {
			customerJson.setCreatedBy(Util.getLoginUserId(request));
			customerJson.setUpdatedBy(Util.getLoginUserId(request));
			customerService.saveOrUpdate(customerJson);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<CustomerJson>(customerJson,HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<CustomerJson>(customerJson,HttpStatus.OK);
//		} else {
//			LOG.error("User must login");
//			return new ResponseEntity(HttpStatus.FORBIDDEN);
//		}
	}

	
	@RequestMapping(value = "/saveCustomerOrders", method = RequestMethod.POST)
	public ResponseEntity<?> saveCustomerOrders(@RequestBody CustomerCartJson customerCartJson, HttpServletRequest request) {
//		if (Util.getLoginUserId(request) != null) {
		try {
//			customerJson.setCreatedBy(Util.getLoginUserId(request));
//			customerJson.setUpdatedBy(Util.getLoginUserId(request));
			customerService.saveCustomerOrders(customerCartJson);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
//		} else {
//			LOG.error("User must login");
//			return new ResponseEntity(HttpStatus.FORBIDDEN);
//		}
	}

	
	@RequestMapping(value="getCustomerInfoByEmail",method=RequestMethod.POST)
	public ResponseEntity<CustomerJson> getCustomerInfoByEmail(@RequestBody Object obj,HttpServletRequest request) {
//		if (Util.getLoginUserId(request) != null) {
			CustomerJson customerJson = null;
			try {
				if(obj!=null){
				customerJson = customerService.getCustomerInfoByEmail((String)PropertyUtils.getProperty(obj, "email"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<CustomerJson>(customerJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<CustomerJson>(customerJson, HttpStatus.OK);
//		} else {
//			LOG.error("User must login");
//			return new ResponseEntity(HttpStatus.FORBIDDEN);
//		}
	}
	
	@RequestMapping("getCategoryById/{id}")
	public ResponseEntity<CategoryJson> getCategoryById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			CategoryJson categoryJson = null;
			try {
//				categoryJson = customerService.getCategoryById(id);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<CategoryJson>(categoryJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<CategoryJson>(categoryJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("deleteCategoryById/{id}")
	public ResponseEntity<CategoryJson> deleteCategoryById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			CategoryJson categoryJson = null;
			try {
//				customerService.deleteCategoryById(id, Util.getLoginUserId(request));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<CategoryJson>(categoryJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<CategoryJson>(categoryJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}


}
