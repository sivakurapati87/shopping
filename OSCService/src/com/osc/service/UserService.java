package com.osc.service;

import java.util.List;

import com.osc.json.UserJson;

public interface UserService {
	public void saveOrUpdateUser(UserJson userJson);

	public UserJson getUserByCredentials(String userName, String pwd);

	public List<UserJson> getAllUsers();

	public UserJson getUserById(Long id);
	
	public void deleteUserById(Long id,Long userId);
}
