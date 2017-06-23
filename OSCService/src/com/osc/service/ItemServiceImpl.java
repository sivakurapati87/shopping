package com.osc.service;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.ItemDao;
import com.osc.entity.Item;
import com.osc.entity.ItemCroppedDimension;
import com.osc.entity.ItemFieldValue;
import com.osc.entity.SubCategoryItem;
import com.osc.json.ItemCroppedDimensionJson;
import com.osc.json.ItemFieldValueJson;
import com.osc.json.ItemJson;
import com.osc.json.PageJson;
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

	@SuppressWarnings("unchecked")
	public void saveOrUpdate(ItemJson itemJson) {
		try {
			Item item = null;
			if (itemJson.getSubCategoryIds() != null) {

/*				File folders = new File(Constants.General.main_image_loc);
				if (!folders.exists()) {
					folders.mkdirs();
				}

				String imageSourceLocation = Constants.General.main_image_loc + Util.generateRandomAlphaNumericValues() + ".txt";
				File f = new File(imageSourceLocation);
				if (!f.exists()) {
					f.createNewFile();
				} else {
					while (f.exists()) {
						imageSourceLocation = Constants.General.main_image_loc + Util.generateRandomAlphaNumericValues() + ".txt";
						f = new File(imageSourceLocation);
					}
					f.createNewFile();
				}
				FileUtils.writeByteArrayToFile(new File(imageSourceLocation), itemJson.getImageSrc().getBytes());
*/
				itemJson.setImageSourceLocation(Util.saveImage(itemJson.getImageSrc().getBytes()));

				if (itemJson.getId() != null) {
					item = (Item) itemDao.getById(Item.class, itemJson.getId());

					/*
					 * Map<String, Object> paramMap = new HashMap<String,
					 * Object>(); StringBuilder sb = new StringBuilder(
					 * "select i from Item i where i.itemId = ?1 and i.subCategoryId = ?2 order by name ASC"
					 * ); paramMap.put("1", itemJson.getId()); paramMap.put("2",
					 * subCategoryId); List<Item> list = (List<Item>)
					 * itemDao.findByQuery(sb.toString(), paramMap, null, null);
					 * if(list!=null){ item = list.get(0); }
					 */

					if (item.getImageSourceLocation() != null && item.getImageSourceLocation().trim().length() > 0) {
						try {
							Files.delete(FileSystems.getDefault().getPath(item.getImageSourceLocation()));
						} catch (Exception e) {
							e.printStackTrace();
							LOG.error(e.getMessage(), e);
						}

					}

				} else {
					item = new Item();
				}
				TransformJsonToEntity.getItem(itemJson, item);
				// item.setSubCategoryId(subCategoryId);
				itemDao.saveOrUpdate(item);
				saveOrUpdateCroppedImages(itemJson, item.getId());
				saveOrUpdateFields(itemJson, item.getId());

				Map<String, Object> paramMap = new HashMap<String, Object>();
				StringBuilder sb = new StringBuilder("select cd from SubCategoryItem cd where cd.itemId = ?1");
				paramMap.put("1", itemJson.getId());
				List<SubCategoryItem> list = (List<SubCategoryItem>) itemDao.findByQuery(sb.toString(), paramMap, null, null);

				if (list != null && list.size() > 0) {
					for (SubCategoryItem subCategoryItem : list) {
						itemDao.remove(subCategoryItem);
					}
				}
				for (Long subCategoryId : itemJson.getSubCategoryIds()) {
					SubCategoryItem subCategoryItem = new SubCategoryItem();
					subCategoryItem.setItemId(item.getId());
					subCategoryItem.setSubCategoryId(subCategoryId);
					subCategoryItem.setCreatedOn(new Date());
					subCategoryItem.setCreatedBy(itemJson.getCreatedBy());
					itemDao.saveOrUpdate(subCategoryItem);
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

	public List<ItemJson> getAllItems(PageJson pageJson) {
		List<ItemJson> itemJsons = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder("select i.id,i.name,i.mrp,i.discount,i.imageSourceLocation from Item i where i.isDeleted = false ");
			/*
			 * if(pageJson.getSearchName() != null &&
			 * pageJson.getSearchName().trim().length()>0){
			 * if(pageJson.getSearchOperator().equalsIgnoreCase("like")){
			 * sb.append(" i."+pageJson.getSearchName()+" like ?1");
			 * params.put("1", "%"+pageJson.getSearchValue()+"%");
			 * }if(pageJson.getSearchOperator().equalsIgnoreCase("gt")){
			 * sb.append(" i."+pageJson.getSearchName()+" > ?2");
			 * params.put("2", pageJson.getSearchValue());
			 * }if(pageJson.getSearchOperator().equalsIgnoreCase("ge")){
			 * sb.append(" i."+pageJson.getSearchName()+" >= ?3");
			 * params.put("3", pageJson.getSearchValue());
			 * }if(pageJson.getSearchOperator().equalsIgnoreCase("lt")){
			 * sb.append(" i."+pageJson.getSearchName()+" < ?4");
			 * params.put("4", pageJson.getSearchValue());
			 * }if(pageJson.getSearchOperator().equalsIgnoreCase("le")){
			 * sb.append(" i."+pageJson.getSearchName()+" <= ?5");
			 * params.put("5", pageJson.getSearchValue());
			 * }if(pageJson.getSearchOperator().equalsIgnoreCase("eq")){
			 * sb.append(" i."+pageJson.getSearchName()+" = ?6");
			 * params.put("6", pageJson.getSearchValue()); } }
			 */
			Util.doSearchAction(pageJson, sb, params);
			sb.append(" order by coalesce(i.updatedOn,i.createdOn) DESC ");

			List<?> categories = itemDao.findByQuery(sb.toString(), params, pageJson.getPageFrom(), pageJson.getPageTo());
			if (categories != null && categories.size() > 0) {
				List<Long> itemIds = new ArrayList<Long>();
				itemJsons = new ArrayList<ItemJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					ItemJson json = new ItemJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					itemIds.add(json.getId());
					json.setName(Util.getStringValueOfObj(obj[1]));
					// json.setSubcategory(Util.getStringValueOfObj(obj[2]));
					json.setMrp(Util.getDoubleValueOfObj(obj[2]));
					json.setDiscount(Util.getDoubleValueOfObj(obj[3]));
					json.setImageSourceLocation(Util.getStringValueOfObj(obj[4]));
					json.setImageSrc(Util.getStringFromLocation(json.getImageSourceLocation()));
					itemJsons.add(json);
				}
				Map<String, Object> paramMap = new HashMap<String, Object>();
				sb = new StringBuilder("select s.subCategory.name,s.itemId from SubCategoryItem s where s.itemId in ?1");
				paramMap.put("1", itemIds);
				List<?> subCategoryItems = itemDao.findByQuery(sb.toString(), paramMap, null, null);
				if (subCategoryItems != null && subCategoryItems.size() > 0) {
					Map<Long, List<String>> itemWithSubCategoryNameMap = new HashMap<Long, List<String>>();
					for (Object subCategoryItem : subCategoryItems) {
						Object[] obj = (Object[]) subCategoryItem;
						Long itemId = Util.getIntegerValueOfObj(obj[1]);
						String subCategoryName = Util.getStringValueOfObj(obj[0]);
						if (itemWithSubCategoryNameMap.get(itemId) != null) {
							itemWithSubCategoryNameMap.get(itemId).add(subCategoryName);
						} else {
							List<String> subCategoryList = new ArrayList<String>();
							subCategoryList.add(subCategoryName);
							itemWithSubCategoryNameMap.put(itemId, subCategoryList);
						}
					}

					for (ItemJson json : itemJsons) {
						String subCategories = Util.getStringFromArray(itemWithSubCategoryNameMap.get(json.getId()).toArray());
						json.setSubcategory(subCategories);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return itemJsons;
	}

	@SuppressWarnings("unchecked")
	public ItemJson getItemById(Long id) {
		ItemJson itemJson = null;
		try {
			Item item = (Item) itemDao.getById(Item.class, id);

			if (item != null) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				StringBuilder sb = new StringBuilder("select s.subCategoryId from SubCategoryItem s where s.itemId = ?1");
				paramMap.put("1", id);
				List<Long> subCategoryItems = (List<Long>) itemDao.findByQuery(sb.toString(), paramMap, null, null);

				itemJson = new ItemJson();
				TransformEntityToJson.getItemJson(item, itemJson);
				itemJson.setImageSourceLocation(item.getImageSourceLocation());
				itemJson.setImageSrc(Util.getStringFromLocation(itemJson.getImageSourceLocation()));
				if (subCategoryItems != null) {
					itemJson.setSubCategoryIds(Util.getLongArrayFromLongList(subCategoryItems));
				}
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

	public Long findNoOfItems(PageJson pageJson) {
		Long noOfRecords = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder("select count(i) from Item i where i.isDeleted = false");
			Util.doSearchAction(pageJson, sb, params);
			noOfRecords = (Long) itemDao.findByQuery(sb.toString(), params);
			return noOfRecords;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return noOfRecords;
	}

	public Long getNoOfProductsBySubCategory(Long subCategoryId) {
		Long noOfRecords = null;
		try {
			StringBuilder sb = new StringBuilder("select count(i) from SubCategoryItem i where i.subCategoryId =" + subCategoryId);
//			Util.doSearchAction(pageJson, sb, params);
			noOfRecords = (Long) itemDao.findByQuery(sb.toString(), null);
			return noOfRecords;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return noOfRecords;
	}

	
	@SuppressWarnings("unchecked")
	public void deleteItemById(Long id, Long userId) {
		try {
			Item item = (Item) itemDao.getById(Item.class, id);

			if (item != null) {
				item.setIsDeleted(Boolean.TRUE);
				item.setUpdatedBy(userId);
				item.setUpdatedOn(new Date());
				itemDao.saveOrUpdate(item);

				if (item.getImageSourceLocation() != null && item.getImageSourceLocation().trim().length() > 0) {
					try {
						Files.delete(FileSystems.getDefault().getPath(item.getImageSourceLocation()));
					} catch (Exception e) {
						e.printStackTrace();
						LOG.error(e.getMessage(), e);
					}
				}
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				StringBuilder sb = new StringBuilder("select cd from SubCategoryItem cd where cd.itemId = ?1");
				paramMap.put("1", id);
				List<SubCategoryItem> list = (List<SubCategoryItem>) itemDao.findByQuery(sb.toString(), paramMap, null, null);

				if (list != null && list.size() > 0) {
					for (SubCategoryItem subCategoryItem : list) {
						itemDao.remove(subCategoryItem);
					}
				}

				paramMap = new HashMap<String, Object>();
				sb = new StringBuilder("select cd from ItemCroppedDimension cd where cd.itemId = ?1 order by cd.name ASC");
				paramMap.put("1", id);
				List<ItemCroppedDimension> croppedlist = (List<ItemCroppedDimension>) itemDao.findByQuery(sb.toString(), paramMap, null, null);
				if (croppedlist != null && croppedlist.size() > 0) {
					for (ItemCroppedDimension cropp : croppedlist) {
						itemDao.remove(cropp);
					}
				}

				paramMap = new HashMap<String, Object>();
				sb = new StringBuilder("select ifv from ItemFieldValue ifv where ifv.itemId = ?1");
				paramMap.put("1", id);
				List<ItemFieldValue> itemFieldlist = (List<ItemFieldValue>) itemDao.findByQuery(sb.toString(), paramMap, null, null);
				if (itemFieldlist != null && itemFieldlist.size() > 0) {
					for (ItemFieldValue itemField : itemFieldlist) {
						itemDao.remove(itemField);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public Map<String, List<ItemJson>> getAllHomeProducts() {
		Map<String, List<ItemJson>> homeProductList = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select s.subCategoryId,count(s.subCategoryId) from SubCategoryItem s where s.subCategory.showItemsInHomePage = true and "
							+ " s.item.isDeleted = false group by s.subCategoryId");

			List<?> items = itemDao.findByQuery(sb.toString(), null, null, null);

			if (items != null && items.size() > 0) {
				homeProductList = new TreeMap<String, List<ItemJson>>();
				for (Object item : items) {
					Object[] itemObj = (Object[]) item;
					if (Util.getIntegerValueOfObj(itemObj[1]) >= 4) {
						sb = new StringBuilder("select i.item.id,i.item.name,i.item.mrp,i.item.discount,i.item.imageSourceLocation,i.subCategory.name,"
								+ "i.subCategory.id,i.subCategory.categoryDivision.categoryId from " + "SubCategoryItem i where i.subCategoryId ="
								+ Util.getIntegerValueOfObj(itemObj[0])+" order by coalesce(i.item.updatedOn,i.item.createdOn) DESC");
						items = itemDao.findByQuery(sb.toString(), null, 0, Constants.General.MAX_HOME_RECORDS);
						if (items != null && items.size() > 0) {

							for (Object object : items) {
								Object[] obj = (Object[]) object;
								ItemJson json = new ItemJson();
								json.setId(Util.getIntegerValueOfObj(obj[0]));
								json.setName(Util.getStringValueOfObj(obj[1]));
								json.setMrp(Util.getDoubleValueOfObj(obj[2]));
								json.setDiscount(Util.getDoubleValueOfObj(obj[3]));
								json.setImageSourceLocation(Util.getStringValueOfObj(obj[4]));
								json.setImageSrc(Util.getStringFromLocation(json.getImageSourceLocation()));
								json.setSubcategory(Util.getStringValueOfObj(obj[5]));
								json.setSubCategoryId(Util.getIntegerValueOfObj(obj[6]));
								json.setCategoryId(Util.getIntegerValueOfObj(obj[7]));
								if (homeProductList.get(json.getSubcategory()) != null) {
									homeProductList.get(json.getSubcategory()).add(json);
								} else {
									List<ItemJson> jsonList = new ArrayList<ItemJson>();
									jsonList.add(json);
									homeProductList.put(json.getSubcategory(), jsonList);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return homeProductList;
	}
	
	public List<Map<String,String>> getAllSearchableWords(){
		List<Map<String,String>> searchableList = null;
		try {
			StringBuilder sb = new StringBuilder("select i.item.name,i.subCategory.name from SubCategoryItem i");
			List<?> items = itemDao.findByQuery(sb.toString(), null, null, null);
			if (items != null && items.size() > 0) {
				searchableList = new ArrayList<Map<String,String>>();
				Set<String> searchNames = new TreeSet<String>();
				for (Object object : items) {
					Object[] obj = (Object[]) object;
					if(obj[0]!=null){
					searchNames.add(Util.getStringValueOfObj(obj[0]));
					}if(obj[1]!=null){
					searchNames.add(Util.getStringValueOfObj(obj[1]));
					}
					/*Map<String,String> map1 = new HashMap<String,String>();
					map1.put(Constants.General.NAME, Util.getStringValueOfObj(obj[0]));
					Map<String,String> map2 = new HashMap<String,String>();
					map2.put(Constants.General.NAME, Util.getStringValueOfObj(obj[1]));
					searchableList.add(map1);
					searchableList.add(map2);*/
				}
				for(String s:searchNames){
					Map<String,String> map = new HashMap<String,String>();
					map.put(Constants.General.NAME, s);
					searchableList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return searchableList;
	}

	public Map<String,Long> getPageBySearchValue(String searchValue){
		Map<String,Long> map = new HashMap<String,Long>();
		try {
			Map<String,Object> param =new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder("select i.id from Item i where i.name = ?1");
			param.put("1", searchValue);
			List<?> items = itemDao.findByQuery(sb.toString(), param, null, null);
			if (items != null && items.size() > 0) {
				for (Object obj : items) {
					map.put(Constants.General.ITEM_ID, Util.getIntegerValueOfObj(obj));
				}
			}else{
				param.clear();
				sb = new StringBuilder("select s.id from SubCategory s where s.name = ?1");
				param.put("1", searchValue);
				items = itemDao.findByQuery(sb.toString(), param, null, null);
				if (items != null && items.size() > 0) {
					for (Object obj : items) {
						map.put(Constants.General.SUBCATEGORY_ID, Util.getIntegerValueOfObj(obj));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return map;
	}

	
	public List<ItemJson> getItemsBySubCategoryId(Long subCategoryId, Integer firstResult) {
		List<ItemJson> itemList = null;
		try {
			StringBuilder sb = new StringBuilder("select i.item.id,i.item.name,i.item.mrp,i.item.discount,i.item.imageSourceLocation,i.subCategory.name"
					+ " from " + "SubCategoryItem i where i.subCategoryId =" + subCategoryId);
			List<?> items = itemDao.findByQuery(sb.toString(), null, firstResult, Constants.General.MAX_ITEMS_RECORDS);
			if (items != null && items.size() > 0) {
				itemList = new ArrayList<ItemJson>();
				for (Object object : items) {
					Object[] obj = (Object[]) object;
					ItemJson json = new ItemJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setName(Util.getStringValueOfObj(obj[1]));
					json.setMrp(Util.getDoubleValueOfObj(obj[2]));
					json.setDiscount(Util.getDoubleValueOfObj(obj[3]));
					json.setImageSourceLocation(Util.getStringValueOfObj(obj[4]));
					json.setImageSrc(Util.getStringFromLocation(json.getImageSourceLocation()));
					json.setSubcategory(Util.getStringValueOfObj(obj[5]));
					itemList.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return itemList;
	}
}
