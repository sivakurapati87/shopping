package com.osc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osc.json.UserJson;
import com.osc.service.UserService;
import com.osc.util.Util;

@RestController
@RequestMapping("UserController")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserController {
	private Logger LOG = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/saveOrUpdateUser", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdateUser(@RequestBody UserJson userJson, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			try {
				userJson.setCreatedBy(Util.getLoginUserId(request));
				userJson.setUpdatedBy(Util.getLoginUserId(request));
				userService.saveOrUpdateUser(userJson);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<Object>(HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}

	}

	@RequestMapping(value = "/makeDefaultUser", method = RequestMethod.GET)
	public ResponseEntity<?> makeDefaultUser(HttpServletRequest request) {
		try {
			UserJson userJson= new UserJson();
			userJson.setFullName("Admin");
			userJson.setPassword("1234");
			userJson.setUserName("admin");
			userJson.setEmpType("Admin");
			userService.saveOrUpdateUser(userJson);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping("getAllUsers")
	public ResponseEntity<List<UserJson>> getAllUsers(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			List<UserJson> userJsonList = null;
			try {
				userJsonList = userService.getAllUsers();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<List<UserJson>>(userJsonList, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<List<UserJson>>(userJsonList, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}

	}

	@RequestMapping("getUserById/{id}")
	public ResponseEntity<UserJson> getUserById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			UserJson userJson = null;
			try {
				userJson = userService.getUserById(id);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<UserJson>(userJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<UserJson>(userJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("deleteUserById/{id}")
	public ResponseEntity<UserJson> deleteUserById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			UserJson userJson = null;
			try {
				userService.deleteUserById(id, Util.getLoginUserId(request));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<UserJson>(userJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<UserJson>(userJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

}
