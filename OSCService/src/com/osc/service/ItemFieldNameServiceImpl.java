package com.osc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.ItemFieldNamesDao;
import com.osc.entity.ItemFieldName;
import com.osc.json.ItemFieldNameJson;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class ItemFieldNameServiceImpl implements ItemFieldNameService {
	private Logger LOG = Logger.getLogger(ItemFieldNameServiceImpl.class);
	@Autowired
	private ItemFieldNamesDao itemFieldNamesDao;

	public void saveOrUpdate(ItemFieldNameJson itemFieldNamesJson) {
		try {
			ItemFieldName itemFieldName = null;
			if (itemFieldNamesJson.getId() != null) {
				itemFieldName = (ItemFieldName) itemFieldNamesDao.getById(ItemFieldName.class, itemFieldNamesJson.getId());
			} else {
				itemFieldName = new ItemFieldName();
			}
			TransformJsonToEntity.getItemFieldName(itemFieldNamesJson, itemFieldName);
			itemFieldNamesDao.saveOrUpdate(itemFieldName);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}

	}

	public List<ItemFieldNameJson> getAllItemFieldNames() {
		List<ItemFieldNameJson> itemFieldNameJsons = null;
		try {
			StringBuilder sb = new StringBuilder("select c.id,c.fieldName,c.user.userName from ItemFieldName c where c.isDeleted = false order by c.fieldName ASC");
			List<?> categories = itemFieldNamesDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				itemFieldNameJsons = new ArrayList<ItemFieldNameJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					ItemFieldNameJson json = new ItemFieldNameJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setFieldName(Util.getStringValueOfObj(obj[1]));
					json.setStrCreatedBy(Util.getStringValueOfObj(obj[2]));
					itemFieldNameJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return itemFieldNameJsons;
	}

	public ItemFieldNameJson getFieldNameById(Integer id) {
		ItemFieldNameJson itemFieldNamesJson = null;
		try {
			ItemFieldName itemFieldName = (ItemFieldName) itemFieldNamesDao.getById(ItemFieldName.class, id);

			if (itemFieldName != null) {
				itemFieldNamesJson = new ItemFieldNameJson();
				TransformEntityToJson.getItemFieldNameJson(itemFieldNamesJson, itemFieldName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return itemFieldNamesJson;
	}

	public void deleteFieldNameById(Integer id, Integer userId) {
		try {
			ItemFieldName itemFieldName = (ItemFieldName) itemFieldNamesDao.getById(ItemFieldName.class, id);

			if (itemFieldName != null) {
				itemFieldName.setIsDeleted(Boolean.TRUE);
				itemFieldName.setUpdatedBy(userId);
				itemFieldName.setUpdatedOn(new Date());
			}
			itemFieldNamesDao.saveOrUpdate(itemFieldName);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

}
