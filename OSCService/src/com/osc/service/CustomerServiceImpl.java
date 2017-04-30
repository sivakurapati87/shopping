package com.osc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.CustomerDao;
import com.osc.entity.Category;
import com.osc.entity.Customer;
import com.osc.entity.CustomerCart;
import com.osc.entity.ItemWithCustomerPhto;
import com.osc.json.CategoryJson;
import com.osc.json.CustomerCartJson;
import com.osc.json.CustomerJson;
import com.osc.json.ItemWithCustomerPhtoJson;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	private Logger LOG = Logger.getLogger(CustomerServiceImpl.class);
	@Autowired
	private CustomerDao customerDao;

	public CustomerJson saveOrUpdate(CustomerJson customerJson) {
		try {
			Customer customer = null;
			if (customerJson.getId() != null) {
				customer = (Customer) customerDao.getById(Customer.class, customerJson.getId());
			} else {
				customer = new Customer();
			}
			TransformJsonToEntity.getCustomer(customer, customerJson);
			customerDao.saveOrUpdate(customer);
			customerJson.setId(customer.getId());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return customerJson;
	}

	public void saveCustomerOrders(CustomerCartJson customerCartJson) {
		try {
			CustomerCart customerCart = new CustomerCart();
			customerCartJson.setDivBlobPath(Util.saveImage(customerCartJson.getDivBlob().getBytes()));
			TransformJsonToEntity.getCustomerCart(customerCart, customerCartJson);
			customerDao.saveOrUpdate(customerCart);

			if (customerCartJson.getCustPhotoJsonList() != null) {
				for(ItemWithCustomerPhtoJson itemWithCustomerPhtoJson:customerCartJson.getCustPhotoJsonList()){
					ItemWithCustomerPhto itemWithCustomerPhto = new ItemWithCustomerPhto();
					itemWithCustomerPhto.setCustomerCartId(customerCart.getId());
					itemWithCustomerPhtoJson.setUploadedImagePath(Util.saveImage(itemWithCustomerPhtoJson.getImageBlob().getBytes()));
					TransformJsonToEntity.getItemWithCustomerPhto(itemWithCustomerPhto, itemWithCustomerPhtoJson);
					customerDao.saveOrUpdate(itemWithCustomerPhto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public List<CategoryJson> getAllCategories() {
		List<CategoryJson> categoryJsons = null;
		try {
			StringBuilder sb = new StringBuilder("select c.id,c.name,c.user.userName from Category c where c.isDeleted = false order by c.name ASC");
			List<?> categories = customerDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				categoryJsons = new ArrayList<CategoryJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					CategoryJson json = new CategoryJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setName(Util.getStringValueOfObj(obj[1]));
					json.setStrCreatedBy(Util.getStringValueOfObj(obj[2]));
					categoryJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return categoryJsons;
	}

	public CustomerJson getCustomerInfoByEmail(String email) {
		CustomerJson customerJson = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder("select c from Customer c where c.isDeleted = false and c.emailId like ?1");
			paramMap.put("1", email + "%");
			Customer customer = (Customer) customerDao.findByQuery(sb.toString(), paramMap);

			if (customer != null) {
				customerJson = new CustomerJson();
				TransformEntityToJson.getCustomerJson(customerJson, customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return customerJson;
	}

	public void deleteCategoryById(Long id, Long userId) {
		try {
			Category category = (Category) customerDao.getById(Category.class, id);

			if (category != null) {
				category.setIsDeleted(Boolean.TRUE);
				category.setUpdatedBy(userId);
				category.setUpdatedOn(new Date());
			}
			customerDao.saveOrUpdate(category);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

}
