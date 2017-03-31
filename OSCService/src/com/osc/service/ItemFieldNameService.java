package com.osc.service;

import java.util.List;

import com.osc.json.ItemFieldNameJson;

public interface ItemFieldNameService {
	public void saveOrUpdate(ItemFieldNameJson itemSpecificationFieldNameJson);

	public List<ItemFieldNameJson> getAllItemFieldNames();
	
	public ItemFieldNameJson getFieldNameById(Integer id);
	
	public void deleteFieldNameById(Integer id,Integer userId);

}
