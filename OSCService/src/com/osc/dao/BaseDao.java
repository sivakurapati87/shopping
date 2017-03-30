package com.osc.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author siva kurapati
 *
 */
public interface BaseDao {
	public void saveOrUpdate(Object obj);

	public Object getById(Class<?> clazz,Integer id);

	public List<?> findByQuery(String hqlQuery, Map<String, Object> params, Integer firstRecord, Integer maxRecord);

}
