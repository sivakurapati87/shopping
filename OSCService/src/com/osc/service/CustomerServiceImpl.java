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
import com.osc.json.CustomerCartJson;
import com.osc.json.CustomerJson;
import com.osc.json.ItemWithCustomerPhtoJson;
import com.osc.util.Constants;
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
				for (ItemWithCustomerPhtoJson itemWithCustomerPhtoJson : customerCartJson.getCustPhotoJsonList()) {
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

	public void changeCartStatus(String status, Long cartId) {
		try {
			CustomerCart customerCart = null;
			if (cartId != null) {
				customerCart = (CustomerCart) customerDao.getById(CustomerCart.class, cartId);
				if (customerCart != null) {
					customerCart.setStatus(status);
					customerDao.saveOrUpdate(customerCart);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public List<CustomerCartJson> getAllCustomerOrders(Date fromDate, Date toDate, String status) {
		List<CustomerCartJson> customerCartJsonList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder("select c.id,c.item.name,c.total,c.quantity,c.deliveryCharges,c.txnId,c.status,c.customer.firstName,"
					+ "c.customer.lastName,c.customer.emailId,c.customer.phoneNumber,c.customer.pincode,"
					+ "c.customer.address,c.customer.city,c.customer.state,c.divBlobPath" + " from CustomerCart c where c.isDeleted = false ");
			if (fromDate != null) {
				sb.append(" and c.createdOn >= ?1");
				params.put("1", fromDate);
			}
			if (toDate != null) {
				sb.append(" and c.createdOn <= ?2");
				params.put("2", toDate);
			}
			if (status != null && !status.equalsIgnoreCase(Constants.General.NULL)) {
				sb.append(" and c.status = ?3");
				params.put("3", status);
			}
			List<?> customerCartList = customerDao.findByQuery(sb.toString(), params, null, null);
			if (customerCartList != null && customerCartList.size() > 0) {
				customerCartJsonList = new ArrayList<CustomerCartJson>();
				List<Long> cartIds = new ArrayList<Long>();
				for (Object object : customerCartList) {
					Object[] obj = (Object[]) object;
					CustomerCartJson json = new CustomerCartJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setItemName(Util.getStringValueOfObj(obj[1]));
					json.setTotal(Util.getDoubleValueOfObj(obj[2]));
					json.setQuantity(Util.getFloatValueOfObj(obj[3]));
					json.setDeliveryCharges(Util.getDoubleValueOfObj(obj[4]));
					json.setTxnId(Util.getStringValueOfObj(obj[5]));
					json.setStatus(Util.getStringValueOfObj(obj[6]));
					json.setFullName(Util.concatenateTwoStringsWithSpace(obj[7], obj[8]));
					json.setEmailId(Util.getStringValueOfObj(obj[9]));
					json.setPhoneNumber(Util.getIntegerValueOfObj(obj[10]));
					json.setPincode(Util.getIntegerValueOfObj(obj[11]));
					json.setAddress(Util.getStringValueOfObj(obj[12]));
					json.setCity(Util.getStringValueOfObj(obj[13]));
					json.setState(Util.getStringValueOfObj(obj[14]));
					json.setDivBlob(Util.getStringFromLocation(Util.getStringValueOfObj(obj[15])));
					cartIds.add(json.getId());
					customerCartJsonList.add(json);
				}

				sb = new StringBuilder("select i.id,i.uploadedImagePath,i.isUploadedFrame,i.customerCartId from ItemWithCustomerPhto i where i.customerCartId in ?1");
				params.clear();
				params.put("1", cartIds);
				List<?> custPhotoList = customerDao.findByQuery(sb.toString(), params, null, null);

				if (custPhotoList != null && custPhotoList.size() > 0) {
					Map<Long, List<ItemWithCustomerPhtoJson>> map = new HashMap<Long, List<ItemWithCustomerPhtoJson>>();
					for (Object object : custPhotoList) {
						Object[] obj = (Object[]) object;
						ItemWithCustomerPhtoJson json = new ItemWithCustomerPhtoJson();
						json.setId(Util.getIntegerValueOfObj(obj[0]));
						json.setImageBlob(Util.getStringFromLocation(Util.getStringValueOfObj(obj[1])));
						json.setIsUploadedFrame(Util.getBooleanValueOfObj(obj[2]));
						json.setCustomerCartId(Util.getIntegerValueOfObj(obj[3]));

						if (map.get(json.getCustomerCartId()) != null) {
							map.get(json.getCustomerCartId()).add(json);
						} else {
							List<ItemWithCustomerPhtoJson> phtoJsonList = new ArrayList<ItemWithCustomerPhtoJson>();
							phtoJsonList.add(json);
							map.put(json.getCustomerCartId(), phtoJsonList);
						}
					}
					for (CustomerCartJson cartJson : customerCartJsonList) {
						cartJson.setCustPhotoJsonList(map.get(cartJson.getId()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return customerCartJsonList;
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
