package lv.itsms.web.session;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lv.itsms.web.request.parameter.LanguageRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequest;
import transfer.domain.SmsGroup;

/*
 * URL Session Parameters Facade
 * Stores and controls session parameters
 */

public class Session {

	HttpServletRequest request;
	
	HttpSession session;

	public Session(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession(true);
	}

	public Session() {
	}

	public void updateSessionLanguage(UserPageRequest userRequest) {
		
		userRequest.update(request);
		String userRequestLanguage = userRequest.getParameter();
		
		Object sessionUserLanguage = session.getAttribute("language");
		
		if (userRequestLanguage == null && sessionUserLanguage == null) {
			userRequestLanguage = LanguageRequestParameter.DEFAULT_LANGUAGE;
		}
		
		if ((sessionUserLanguage==null)||(!sessionUserLanguage.toString().equals(userRequestLanguage))) {
			if (userRequestLanguage != null)
				session.setAttribute("language", userRequestLanguage);
		}
	}
	
	public String getSessionLanguage() {
		return session.getAttribute("language").toString();
	}
	
	public void updateSessionMenuId(UserPageRequest userRequest) {		
		
		userRequest.update(request);
		String userRequestMainMenuId = userRequest.getParameter();

		session.setAttribute("id", userRequestMainMenuId);
	}
	
	public String getSessionMenuId() {
		return session.getAttribute("id").toString();
	}
	
	public void updateSessionSubMenuId(UserPageRequest userRequest) {
		
		userRequest.update(request);
		String userRequestSubMenuId = userRequest.getParameter();
		
		Object sessionMainMenuId = session.getAttribute("id");
		
		Object sessionSubMenuId = session.getAttribute("subid");

		if (userRequestSubMenuId==null) {
			if (sessionSubMenuId==null) {
				userRequestSubMenuId = "";
			} else {
				userRequestSubMenuId = sessionMainMenuId.toString();
			}
		}

		session.setAttribute("subid", userRequestSubMenuId);
	}
	
	public String getSessionSubMenuId() {
		return session.getAttribute("subid").toString();
	}
	
	public void updateSessionCustomerMenuId(UserPageRequest userRequest) {
		
		userRequest.update(request);
		String userRequestCustomerMenuId = userRequest.getParameter();
		
		session.setAttribute("cusMenu", userRequestCustomerMenuId);
	}
	
	public String getSessionCustomerMenuId() {
		return session.getAttribute("cusMenu").toString();
	}
	
	public void updateSessionAttribute(String attribute, Object value) {
		session.setAttribute(attribute, value);
	}
	
	public void updateSessionSmsGroupsNames (List<String> smsGroups ) {		
		session.setAttribute("smsgroupnames", smsGroups);
	}
	
	public String getSessionUserId() {
		Object userid = session.getAttribute("userid");
		String sessionUserId = "";
		if (userid != null) {
			sessionUserId = userid.toString();
		}
		return sessionUserId;
	}
	
	public boolean isUserLoggedIn() {
		return (session.getAttribute("userid")!=null && (!session.getAttribute("userid").equals("")));
	}
	
	public String getSessionCustomerId() {
		Object userid = session.getAttribute("customerid");
		String sessionUserId = "";
		if (userid != null) {
			sessionUserId = userid.toString();
		}
		return sessionUserId;
	}
	
	public void updateParameter(UserPageRequest userRequest) {
		userRequest.update(request);
	}
	
	public boolean isParameter(UserPageRequest userRequest) {
		return (session.getAttribute(userRequest.getParameter()) != null);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
}
