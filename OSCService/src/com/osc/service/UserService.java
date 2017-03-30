package com.osc.service;

import java.util.List;

import com.osc.json.UserJson;

public interface UserService {
	public void saveOrUpdateUser(UserJson userJson);

	public UserJson getUserByCredentials(String userName, String pwd);

	public List<UserJson> getAllUsers();

	public UserJson getUserById(Integer id);
	
	public void deleteUserById(Integer id,Integer userId);
}
