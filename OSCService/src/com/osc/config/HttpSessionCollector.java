/**
 * 
 */
package com.osc.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author skurapati
 *
 */
public class HttpSessionCollector implements HttpSessionListener {
	private static final Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();
	/*@Autowired
	private LoginDetailsService loginDetailService;
*/
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		sessions.put(session.getId(), session);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		/*UserDto userDto = null;
		LoginAttempts attempts = null;
		HttpSession session = event.getSession();*/
		try {
			/*userDto = (UserDto) session.getAttribute(Constants.LOGIN_USER);
			if (userDto != null && userDto.getLoginAttemptId() != null) {
				ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
				 LoginDetailsService loginDetailService = (LoginDetailsService) ctx.getBean("loginDetailsServiceImpl");
				attempts = loginDetailService.loadLoginAttempts(Integer.parseInt(userDto.getLoginAttemptId()));
				TransformDtoToEntity.getuserloginAttempt(userDto, attempts);
				loginDetailService.updateLoginDetails(attempts);
			}*/
			sessions.remove(event.getSession().getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static HttpSession find(String sessionId) {
		return sessions.get(sessionId);
	}

	public static Map<String, HttpSession> getSessions() {
		return sessions;
	}
}