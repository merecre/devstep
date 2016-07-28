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

	final static String SESSION_LANGUAGE_PARAMETER = "language";
	final static String SESSION_MAIN_MENU_PARAMETER = "id";
	final static String SESSION_SUB_MENU_PARAMETER = "subid";
	final static String SESSION_CUSTOMER_MENU_PARAMETER = "cusMenu";
	public final static String SESSION_CUSTOMER_LOGIN_PARAMETER = "userid";
	public final static String SESSION_CUSTOMER_ID_PARAMETER = "customerid";
	public final static String SESSION_ERROR_PARAMETER = "error";
	public final static String SESSION_PROFILE_PARAMETER = "profile";
	public final static String SESSION_REGISTRATION_PARAMETER = "registrationinfo";
	public final static String SESSION_CHARTLINE_PARAMETER = "chartLines";
	public final static String SESSION_SMSGROUPS_PARAMETER = "smsgroups";
	public final static String SESSION_SMSGROUPREC_PARAMETER = "smsgrouprec";
	public final static String SESSION_PHONEGROUPS_PARAMETER = "phonegroups";

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

		Object sessionUserLanguage = session.getAttribute(SESSION_LANGUAGE_PARAMETER);

		if (userRequestLanguage == null && sessionUserLanguage == null) {
			userRequestLanguage = LanguageRequestParameter.DEFAULT_LANGUAGE;
		}

		if ((sessionUserLanguage==null)||(!sessionUserLanguage.toString().equals(userRequestLanguage))) {
			if (userRequestLanguage != null)
				session.setAttribute(SESSION_LANGUAGE_PARAMETER, userRequestLanguage);
		}
	}

	public String getSessionLanguage() {
		return session.getAttribute(SESSION_LANGUAGE_PARAMETER).toString();
	}

	public void updateSessionMenuId(UserPageRequest userRequest) {		

		userRequest.update(request);
		String userRequestMainMenuId = userRequest.getParameter();

		session.setAttribute(SESSION_MAIN_MENU_PARAMETER, userRequestMainMenuId);
	}

	public String getSessionMenuId() {
		return session.getAttribute(SESSION_MAIN_MENU_PARAMETER).toString();
	}

	public void updateSessionSubMenuId(UserPageRequest userRequest) {

		userRequest.update(request);
		String userRequestSubMenuId = userRequest.getParameter();

		Object sessionMainMenuId = session.getAttribute(SESSION_MAIN_MENU_PARAMETER);

		Object sessionSubMenuId = session.getAttribute(SESSION_SUB_MENU_PARAMETER);

		if (userRequestSubMenuId==null) {
			if (sessionSubMenuId==null) {
				userRequestSubMenuId = "";
			} else {
				userRequestSubMenuId = sessionMainMenuId.toString();
			}
		}

		session.setAttribute(SESSION_SUB_MENU_PARAMETER, userRequestSubMenuId);
	}

	public String getSessionSubMenuId() {
		return session.getAttribute(SESSION_SUB_MENU_PARAMETER).toString();
	}

	public void updateSessionCustomerMenuId(UserPageRequest userRequest) {

		userRequest.update(request);
		String userRequestCustomerMenuId = userRequest.getParameter();

		session.setAttribute(SESSION_CUSTOMER_MENU_PARAMETER, userRequestCustomerMenuId);
	}

	public String getSessionCustomerMenuId() {
		return session.getAttribute(SESSION_CUSTOMER_MENU_PARAMETER).toString();
	}

	public void updateSessionAttribute(String attribute, Object value) {
		session.setAttribute(attribute, value);
	}

	public String getSessionUserId() {
		Object userid = session.getAttribute(SESSION_CUSTOMER_LOGIN_PARAMETER);
		String sessionUserId = "";
		if (userid != null) {
			sessionUserId = userid.toString();
		}
		return sessionUserId;
	}

	public boolean isUserLoggedIn() {
		return (session.getAttribute(SESSION_CUSTOMER_LOGIN_PARAMETER)!=null && (!session.getAttribute(SESSION_CUSTOMER_LOGIN_PARAMETER).equals("")));
	}

	public String getSessionCustomerId() {
		Object userid = session.getAttribute(SESSION_CUSTOMER_ID_PARAMETER);
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
