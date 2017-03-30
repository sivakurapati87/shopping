package com.osc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.UserDao;
import com.osc.entity.User;
import com.osc.json.UserJson;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private Logger LOG = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;

	public void saveOrUpdateUser(UserJson userJson) {
		try {
			User user = null;
			if (userJson.getId() != null) {
				user = (User) userDao.getById(User.class, userJson.getId());
			} else {
				user = new User();
			}
			TransformJsonToEntity.getUser(userJson, user);
			userDao.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public List<UserJson> getAllUsers() {
		List<UserJson> userJsonList = null;
		try {
			StringBuilder sb = new StringBuilder("select u.id,u.userName,u.fullName,u.email,u.empType from User u where u.isDeleted = false order by u.userName ASC");
			List<?> userList = userDao.findByQuery(sb.toString(), null, null, null);
			if (userList != null && userList.size() > 0) {
				userJsonList = new ArrayList<UserJson>();
				for (Object object : userList) {
					Object[] obj = (Object[]) object;
					UserJson json = new UserJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setUserName(Util.getStringValueOfObj(obj[1]));
					json.setFullName(Util.getStringValueOfObj(obj[2]));
					json.setEmail(Util.getStringValueOfObj(obj[3]));
					json.setEmpType(Util.getStringValueOfObj(obj[4]));
					userJsonList.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return userJsonList;
	}

	@SuppressWarnings("unchecked")
	public UserJson getUserByCredentials(String userName, String pwd) {
		UserJson userJson = null;
		try {
			StringBuilder sb = new StringBuilder("select u from User u where u.userName = ?1 and u.password = ?2 and u.isDeleted = false");
			Map<String, Object> queryParamMap = new HashMap<String, Object>();
			queryParamMap.put("1", userName);
			queryParamMap.put("2", pwd);
			List<User> userList = (List<User>) userDao.findByQuery(sb.toString(), queryParamMap, null, null);
			if (userList != null && userList.size() > 0) {
				userJson = new UserJson();
				for (User user : userList) {
					TransformEntityToJson.getUserJson(user, userJson);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}

		return userJson;
	}

	public UserJson getUserById(Integer id) {
		UserJson json = null;
		try {
			User user = (User) userDao.getById(User.class, id);

			if (user != null) {
				json = new UserJson();
				TransformEntityToJson.getUserJson(user, json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return json;
	}

	public void deleteUserById(Integer id, Integer userId) {
		try {
			User user = (User) userDao.getById(User.class, id);

			if (user != null) {
				user.setIsDeleted(Boolean.TRUE);
				user.setUpdatedBy(userId);
				user.setUpdatedOn(new Date());
			}
			userDao.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

}
