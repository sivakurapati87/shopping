package com.osc.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl implements BaseDao {
	private Logger LOG = Logger.getLogger(BaseDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void saveOrUpdate(Object obj) {
		try {
			if (obj != null) {
				getSession().saveOrUpdate(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}
	
	public void remove(Object obj) {
		try {
			if (obj != null) {
				getSession().delete(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public Object getById(Class<?> clazz, Long id) {
		return getSession().get(clazz, id);
	}

	public List<?> findByQuery(String hqlQuery, Map<String, Object> params, Integer firstRecord, Integer maxRecord) {
		List<?> list = null;
		try {
			Query query = getSession().createQuery(hqlQuery);
			if (params != null) {
				for (Map.Entry<String, Object> mapEntry : params.entrySet()) {
					if(mapEntry.getValue() instanceof List){
						query.setParameterList(mapEntry.getKey(),(List<?>) mapEntry.getValue());
					}else{
					query.setParameter(mapEntry.getKey(), mapEntry.getValue());
					}
				}
			}
			if (firstRecord != null && maxRecord != null) {
				query.setFirstResult(firstRecord);
				query.setMaxResults(maxRecord);
			}
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return list;
	}
	
	public Object findByQuery(String hqlQuery, Map<String, Object> params) {
		Object result = null;
		try {
			Query query = getSession().createQuery(hqlQuery);
			if (params != null) {
				for (Map.Entry<String, Object> mapEntry : params.entrySet()) {
					if(mapEntry.getValue() instanceof List){
						query.setParameterList(mapEntry.getKey(),(List<?>) mapEntry.getValue());
					}else{
					query.setParameter(mapEntry.getKey(), mapEntry.getValue());
					}
				}
			}
			result = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
}
