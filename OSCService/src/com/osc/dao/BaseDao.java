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
	
	public void remove(Object obj);

	public Object getById(Class<?> clazz,Long id);

	public List<?> findByQuery(String hqlQuery, Map<String, Object> params, Integer firstRecord, Integer maxRecord);
	
	public Object findByQuery(String hqlQuery, Map<String, Object> params);

}
