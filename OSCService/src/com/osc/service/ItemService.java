package com.osc.service;

import java.util.List;
import java.util.Map;

import com.osc.json.ItemJson;
import com.osc.json.PageJson;

public interface ItemService {
	public void saveOrUpdate(ItemJson ItemJson);

	public List<ItemJson> getAllItems(PageJson pageJson);
	
	public Long findNoOfItems(PageJson pageJson);
	
	public ItemJson getItemById(Long id);
	
	public Map<String,List<ItemJson>> getAllHomeProducts();
	
	public void deleteItemById(Long id,Long userId);
	
	public List<ItemJson> getItemsBySubCategoryId(Long subCategoryId,Integer firstResult);
	
	public Long getNoOfProductsBySubCategory(Long subCategoryId);

}
