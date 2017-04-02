package com.osc.service;

import java.util.List;

import com.osc.json.ItemJson;

public interface ItemService {
	public void saveOrUpdate(ItemJson ItemJson);

	public List<ItemJson> getAllItems();
	
	public ItemJson getItemById(Long id);
	
	public void deleteItemById(Long id,Long userId);

}
