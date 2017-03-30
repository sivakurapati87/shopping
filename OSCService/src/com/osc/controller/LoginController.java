/**
 * 
 */
package com.osc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osc.config.HttpSessionCollector;
import com.osc.json.UserJson;
import com.osc.service.UserService;
import com.osc.util.Constants;
import com.osc.util.Util;

/**
 * @author skurapati
 *
 */
@RestController
@RequestMapping("/LoginController")
public class LoginController {
	private static final Logger LOG = Logger.getLogger(LoginController.class);
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
	public ResponseEntity<?> loginAction(@RequestBody UserJson userJson, HttpServletRequest httpServletRequest) {
		UserJson json = null;
		try {
			LOG.info("loginAction() started");
			String encodedString = Util.passwordEncryption(userJson.getPassword());
			
			json = userService.getUserByCredentials(userJson.getUserName(), encodedString);
			
			if (json != null) {

				removeDuplicateUserLogin(json);
				HttpSession session = httpServletRequest.getSession();
				session.setAttribute(Constants.General.LOGIN_USER, json);
				 
				LOG.info("loginAction() ended");
				return new ResponseEntity<UserJson>(json, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<?>>(HttpStatus.OK);
			}
		} catch (Exception e) {
 			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	private void removeDuplicateUserLogin(UserJson userJson) {
		// To clear the existing session
		Map<String, HttpSession> sessions = HttpSessionCollector.getSessions();

		HttpSession existingSession = null;
		if (!sessions.isEmpty()) {
			for (Map.Entry<String, HttpSession> mapEntry : sessions.entrySet()) {
				UserJson existedLoggedInUsers = (UserJson) mapEntry.getValue().getAttribute(Constants.General.LOGIN_USER);
				if (existedLoggedInUsers != null) {
					if (existedLoggedInUsers.getId().equals(userJson.getId())) {
						existingSession = mapEntry.getValue();
					}
				}
			}
		}
		if (existingSession != null) {
			LOG.info("User is existed so invalidate()");
			existingSession.invalidate();
		}
	}

	@RequestMapping(value = "/getLoggedInPersonInfo", method = RequestMethod.GET)
	public UserJson getLoggedInPersonInfo(HttpServletRequest httpServletRequest, @RequestParam(value="currentLoginUserId",required=false) String currentLoginUserId) {
		UserJson json = null;
		try {
				HttpSession session = httpServletRequest.getSession();
				json = (UserJson) session.getAttribute(Constants.General.LOGIN_USER);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return json;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
		try {
			UserJson userJson = null;
			HttpSession session = httpServletRequest.getSession();
			userJson = (UserJson) session.getAttribute(Constants.General.LOGIN_USER);
			if (userJson != null) {
				session.removeAttribute(Constants.General.LOGIN_USER);
				session.invalidate();
				return new ResponseEntity<UserJson>(userJson, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

/*	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST)
	public ResponseEntity<?> getRecoveryPassword(@RequestBody UserDto dto, HttpServletRequest httpServletRequest) {

		User user = null;
		StatusMessage status = new StatusMessage();
		if (dto.getUserId() != null){
			user = userService.loadUser(dto.getUserId());
			if(user == null){
				user = indiaUserService.loadUser(dto.getUserId());
				
			}else{
				if(user.getUserRole().name().toUpperCase().startsWith(Constants.IN_PREFIX)){
					user = indiaUserService.loadUser(dto.getUserId());
				}
			}
		}
		else if (dto.getEmail() != null){
			user = userService.loadUserByEmail(dto.getEmail());
			if(user == null){
				user = indiaUserService.loadUserByEmail(dto.getUserId());
			}else{
				if(user.getUserRole().name().toUpperCase().startsWith(Constants.IN_PREFIX)){
					user = indiaUserService.loadUserByEmail(dto.getUserId());
				}
			}
		}
		if (user != null) {
			if(user.getStatus().equals(Constants.ACTIVE)){
			if (user.getEmail() != null && user.getEmail().trim().length() > 0) {
				StringBuffer randomPassword = new StringBuffer();
				for (int i = 0; i < Constants.RANDOM_PASS_LENGTH; i++) {
					int number = getRandomNumber();
					char passChars = Constants.PASS_CHAR_LIST.charAt(number);
					randomPassword.append(passChars);
				}
				try {
					user.setPassword(randomPassword.toString());
					if(user.getUserRole()!=null && user.getUserRole().name().toUpperCase().startsWith(Constants.IN_PREFIX)){
						indiaUserService.updateUser(user);	
					}else{
					userService.updateUser(user);
					}
					StringBuffer msgbody = new StringBuffer("Hello CGI ATS User, ");
					msgbody.append("<br><br>");
					msgbody.append("Your Temporary password is :" + randomPassword);
					msgbody.append("<br>");
					msgbody.append("Please change your password after first login");
					msgbody.append("<br><br>");
					msgbody.append("<b>*** This is an automatically generated email, please do not reply ***</b>");
					String msgSub = "[CGI ATS] Auto generated password";

					commService.sendEmail(null, user.getEmail(), msgSub, msgbody.toString(), new AttachmentInfo[0]);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage(), e);
				}

				status.setStatusCode(String.valueOf(200));
				status.setStatusMessage("Password successfully sent to your mail id");
				return new ResponseEntity<>(status, HttpStatus.OK);
			} else {
				status.setStatusCode(String.valueOf(500));
				status.setStatusMessage("Email does not exist!.. Please contact your administrator ");
				return new ResponseEntity<>(status, HttpStatus.OK);
			}
			}else{
				status.setStatusCode(String.valueOf(500));
				status.setStatusMessage("User is already Inactive.");
				return new ResponseEntity<>(status, HttpStatus.OK);
			}
		} else {
			status.setStatusCode(String.valueOf(500));
			status.setStatusMessage("Unable to get details please provide valid details");
			return new ResponseEntity<>(status, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<?> getupdatedPassword(@RequestBody UserDto newdto, HttpServletRequest httpServletRequest) {
		StatusMessage status = new StatusMessage();
		UserDto user = Utils.getLoginUser(httpServletRequest);
		User entityUser = null;
		if (user.getPassword().equals(encoder.encodePassword(newdto.getPassword(), Constants.SALT))) {
			if(user.getUserRole()!=null && user.getUserRole().name().toUpperCase().startsWith(Constants.IN_PREFIX)){
				entityUser = indiaUserService.loadUser(user.getUserId());
			}else{
				entityUser = userService.loadUser(user.getUserId());
			}
			entityUser.setPassword(newdto.getNewPassword());
			try {
				if(user.getUserRole()!=null && user.getUserRole().name().toUpperCase().startsWith(Constants.IN_PREFIX)){
					indiaUserService.updateUser(entityUser);
				}else{
					userService.updateUser(entityUser);
				}
				
			} catch (ServiceException e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
			}
			status.setStatusCode(String.valueOf(200));
			status.setStatusMessage("password successfully changed");
			return new ResponseEntity<>(status, HttpStatus.OK);
		} else {
			status.setStatusCode(String.valueOf(500));
			status.setStatusMessage("your current password dose not match");
			return new ResponseEntity<>(status, HttpStatus.OK);
		}
	}

	private int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(Constants.PASS_CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}*/
}
