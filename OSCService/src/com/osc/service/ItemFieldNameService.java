package com.osc.service;

import java.util.List;

import com.osc.json.ItemFieldNameJson;

public interface ItemFieldNameService {
	public void saveOrUpdate(ItemFieldNameJson itemSpecificationFieldNameJson);

	public List<ItemFieldNameJson> getAllItemFieldNames();
	
	public ItemFieldNameJson getFieldNameById(Long id);
	
	public void deleteFieldNameById(Long id,Long userId);

}
