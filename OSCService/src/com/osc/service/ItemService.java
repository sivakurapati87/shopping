package com.osc.service;

import java.util.List;

import com.osc.json.ItemJson;
import com.osc.json.PageJson;

public interface ItemService {
	public void saveOrUpdate(ItemJson ItemJson);

	public List<ItemJson> getAllItems(PageJson pageJson);
	
	public Long findNoOfItems();
	
	public ItemJson getItemById(Long id);
	
	public void deleteItemById(Long id,Long userId);

}
