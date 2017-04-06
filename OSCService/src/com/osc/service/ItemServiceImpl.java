package com.osc.service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.ItemDao;
import com.osc.entity.Item;
import com.osc.entity.ItemCroppedDimension;
import com.osc.entity.ItemFieldValue;
import com.osc.json.ItemCroppedDimensionJson;
import com.osc.json.ItemFieldValueJson;
import com.osc.json.ItemJson;
import com.osc.util.Constants;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	private Logger LOG = Logger.getLogger(ItemServiceImpl.class);
	@Autowired
	private ItemDao itemDao;

	public void saveOrUpdate(ItemJson itemJson) {
		try {
			Item item = null;
			if (itemJson.getSubCategoryIds() != null) {
				
				File folders = new File(Constants.General.main_image_loc);
				if(!folders.exists()){
					folders.mkdirs();
				}
				
				String imageSourceLocation = Constants.General.main_image_loc+Util.generateRandomAlphaNumericValues()+".txt";
				File f=new File(imageSourceLocation);
				if(!f.exists()){
					f.createNewFile();
				}else{
					while(f.exists()){
						imageSourceLocation = Constants.General.main_image_loc+Util.generateRandomAlphaNumericValues()+".txt";
						f = new File(imageSourceLocation);
					}
					f.createNewFile();
				}
				FileUtils.writeByteArrayToFile(new File(imageSourceLocation), itemJson.getImageSrc().getBytes());
				
				itemJson.setImageSourceLocation(imageSourceLocation);
				
				for (Long subCategoryId : itemJson.getSubCategoryIds()) {
					if (itemJson.getId() != null) {
						item = (Item) itemDao.getById(Item.class, itemJson.getId());
						
						/*Map<String, Object> paramMap = new HashMap<String, Object>();
						StringBuilder sb = new StringBuilder("select i from Item i where i.itemId = ?1 and i.subCategoryId = ?2 order by name ASC");
						paramMap.put("1", itemJson.getId());
						paramMap.put("2", subCategoryId);
						List<Item> list = (List<Item>) itemDao.findByQuery(sb.toString(), paramMap, null, null);
						if(list!=null){
							item = list.get(0);
						}*/
						
						if (item.getImageSourceLocation() != null && item.getImageSourceLocation().trim().length() > 0) {
							try {
								Files.delete(FileSystems.getDefault().getPath(item.getImageSourceLocation()));	
							} catch (Exception e) {
								e.printStackTrace();
								LOG.error(e.getMessage(),e);
							}
							
						}
						
					}else {
						item = new Item();
					}
					TransformJsonToEntity.getItem(itemJson, item);
					item.setSubCategoryId(subCategoryId);
					itemDao.saveOrUpdate(item);
					saveOrUpdateCroppedImages(itemJson, item.getId());
					saveOrUpdateFields(itemJson, item.getId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private void saveOrUpdateCroppedImages(ItemJson itemJson, Long itemId) {
		try {
			if (itemJson.getId() != null) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				StringBuilder sb = new StringBuilder("select cd from ItemCroppedDimension cd where cd.itemId = ?1 order by cd.name ASC");
				paramMap.put("1", itemJson.getId());
				List<ItemCroppedDimension> list = (List<ItemCroppedDimension>) itemDao.findByQuery(sb.toString(), paramMap, null, null);
				if (list != null && list.size() > 0) {
					if (itemJson.getItemCroppedDimensionJsonList() != null && itemJson.getItemCroppedDimensionJsonList().size() > 0) {
						if (list.size() > itemJson.getItemCroppedDimensionJsonList().size()) {
							int i = 0;
							for (ItemCroppedDimensionJson json : itemJson.getItemCroppedDimensionJsonList()) {
								json.setItemId(itemJson.getId());
								TransformJsonToEntity.getItemCroppedDimension(list.get(i), json);
								itemDao.saveOrUpdate(list.get(i++));
							}
							for (; i < list.size(); i++) {
								itemDao.remove(list.get(i));
							}
						} else {
							int i = 0;
							for (ItemCroppedDimension dimension : list) {
								itemJson.getItemCroppedDimensionJsonList().get(i).setItemId(itemJson.getId());
								TransformJsonToEntity.getItemCroppedDimension(dimension, itemJson.getItemCroppedDimensionJsonList().get(i++));
								itemDao.saveOrUpdate(dimension);
							}
							for (; i < itemJson.getItemCroppedDimensionJsonList().size(); i++) {
								ItemCroppedDimension dimension = new ItemCroppedDimension();
								TransformJsonToEntity.getItemCroppedDimension(dimension, itemJson.getItemCroppedDimensionJsonList().get(i));
								dimension.setItemId(itemJson.getId());
								itemDao.saveOrUpdate(dimension);
							}
						}
					} else {
						for (ItemCroppedDimension croppedDimension : list) {
							itemDao.remove(croppedDimension);
						}
					}
				}
			} else {
				if (itemJson.getItemCroppedDimensionJsonList() != null) {
					for (ItemCroppedDimensionJson json : itemJson.getItemCroppedDimensionJsonList()) {
						ItemCroppedDimension dimension = new ItemCroppedDimension();
						TransformJsonToEntity.getItemCroppedDimension(dimension, json);
						dimension.setItemId(itemId);
						itemDao.saveOrUpdate(dimension);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private void saveOrUpdateFields(ItemJson itemJson, Long itemId) {
		try {
			if (itemJson.getId() != null) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				StringBuilder sb = new StringBuilder("select ifv from ItemFieldValue ifv where ifv.itemId = ?1");
				paramMap.put("1", itemJson.getId());
				List<ItemFieldValue> list = (List<ItemFieldValue>) itemDao.findByQuery(sb.toString(), paramMap, null, null);
				if (list != null && list.size() > 0) {
					if (itemJson.getItemFieldValueJsonList() != null && itemJson.getItemFieldValueJsonList().size() > 0) {
						if (list.size() > itemJson.getItemFieldValueJsonList().size()) {
							int i = 0;
							for (ItemFieldValueJson json : itemJson.getItemFieldValueJsonList()) {
								json.setItemId(itemJson.getId());
								TransformJsonToEntity.getItemFieldValue(list.get(i), json);
								itemDao.saveOrUpdate(list.get(i++));
							}
							for (; i < list.size(); i++) {
								itemDao.remove(list.get(i));
							}
						} else {
							int i = 0;
							for (ItemFieldValue fieldValue : list) {
								itemJson.getItemFieldValueJsonList().get(i).setItemId(itemJson.getId());
								TransformJsonToEntity.getItemFieldValue(fieldValue, itemJson.getItemFieldValueJsonList().get(i++));
								itemDao.saveOrUpdate(fieldValue);
							}
							for (; i < itemJson.getItemFieldValueJsonList().size(); i++) {
								ItemFieldValue itemFieldValue = new ItemFieldValue();
								TransformJsonToEntity.getItemFieldValue(itemFieldValue, itemJson.getItemFieldValueJsonList().get(i));
								itemFieldValue.setItemId(itemJson.getId());
								itemDao.saveOrUpdate(itemFieldValue);
							}
						}
					} else {
						for (ItemFieldValue itemFieldValue : list) {
							itemDao.remove(itemFieldValue);
						}
					}
				}
			} else {
				if (itemJson.getItemCroppedDimensionJsonList() != null) {
					for (ItemFieldValueJson json : itemJson.getItemFieldValueJsonList()) {
						ItemFieldValue itemFieldValue = new ItemFieldValue();
						TransformJsonToEntity.getItemFieldValue(itemFieldValue, json);
						itemFieldValue.setItemId(itemId);
						itemDao.saveOrUpdate(itemFieldValue);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public List<ItemJson> getAllItems() {
		List<ItemJson> itemJsons = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select i.id,i.name,i.subCategory.name,i.mrp,i.discount,i.imageSourceLocation from Item i where i.isDeleted = false order by coalesce(i.updatedOn,i.createdOn) DESC");
			List<?> categories = itemDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				itemJsons = new ArrayList<ItemJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					ItemJson json = new ItemJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setName(Util.getStringValueOfObj(obj[1]));
					json.setSubcategory(Util.getStringValueOfObj(obj[2]));
					json.setMrp(Util.getDoubleValueOfObj(obj[3]));
					json.setDiscount(Util.getDoubleValueOfObj(obj[4]));
					json.setImageSourceLocation(Util.getStringValueOfObj(obj[5]));
					json.setImageSrc(Util.getStringFromLocation(json.getImageSourceLocation()));
					itemJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return itemJsons;
	}

	public ItemJson getItemById(Long id) {
		ItemJson itemJson = null;
		try {
			Item item = (Item) itemDao.getById(Item.class, id);
			
			if (item != null) {
				itemJson = new ItemJson();
				TransformEntityToJson.getItemJson(item, itemJson);
				itemJson.setImageSourceLocation(item.getImageSourceLocation());
				itemJson.setImageSrc(Util.getStringFromLocation(itemJson.getImageSourceLocation()));
				getItemFieldValueJson(itemJson);
				getCroppedImageJson(itemJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return itemJson;
	}

	@SuppressWarnings("unchecked")
	private void getCroppedImageJson(ItemJson itemJson) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder("select cd from ItemCroppedDimension cd where cd.itemId = ?1 order by cd.name ASC");
			paramMap.put("1", itemJson.getId());
			List<ItemCroppedDimension> list = (List<ItemCroppedDimension>) itemDao.findByQuery(sb.toString(), paramMap, null, null);
			List<ItemCroppedDimensionJson> itemCroppedDimensionJsonList = new ArrayList<ItemCroppedDimensionJson>();
			if (list != null && list.size() > 0) {
				for (ItemCroppedDimension itemCroppedDimension : list) {
					ItemCroppedDimensionJson itemCroppedDimensionJson = new ItemCroppedDimensionJson();
					TransformEntityToJson.getItemCroppedDimensionJson(itemCroppedDimension, itemCroppedDimensionJson);
					itemCroppedDimensionJsonList.add(itemCroppedDimensionJson);
				}
			}
			itemJson.setItemCroppedDimensionJsonList(itemCroppedDimensionJsonList);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private void getItemFieldValueJson(ItemJson itemJson) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder("select ifv from ItemFieldValue ifv where ifv.itemId = ?1");
			paramMap.put("1", itemJson.getId());
			List<ItemFieldValue> list = (List<ItemFieldValue>) itemDao.findByQuery(sb.toString(), paramMap, null, null);
			List<ItemFieldValueJson> itemFieldValueJsonList = new ArrayList<ItemFieldValueJson>();
			if (list != null && list.size() > 0) {
				for (ItemFieldValue itemFieldValue : list) {
					ItemFieldValueJson itemFieldValueJson = new ItemFieldValueJson();
					TransformEntityToJson.getItemFieldValueJson(itemFieldValue, itemFieldValueJson);
					itemFieldValueJsonList.add(itemFieldValueJson);
				}
			}
			itemJson.setItemFieldValueJsonList(itemFieldValueJsonList);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public void deleteItemById(Long id, Long userId) {
		try {
			Item item = (Item) itemDao.getById(Item.class, id);

			if (item != null) {
				item.setIsDeleted(Boolean.TRUE);
				item.setUpdatedBy(userId);
				item.setUpdatedOn(new Date());
			}
			itemDao.saveOrUpdate(item);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

}
