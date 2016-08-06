package lv.itsms.web.session;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.menu.LanguageRequestParameter;
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
	public final static String SESSION_VIEW_SMSGROUPREC_PARAMETER = "viewsmsgrouprec";
	public final static String SESSION_PHONEGROUPS_PARAMETER = "phonegroups";
	public final static String SESSION_PROFILEINFO_PARAMETER = "profileinfo";

	HttpServletRequest request;

	HttpSession session;

	public Session(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession(true);
	}

	public Session() {
	}

	public void updateSessionLanguage(UserPageRequestParameter userRequest) {

		final Object lock = request.getSession().getId().intern();
		synchronized(lock) {
			userRequest.update(request);
			String userRequestLanguage = userRequest.getParameter();

			Object sessionUserLanguage = request.getSession().getAttribute(SESSION_LANGUAGE_PARAMETER);

			if (userRequestLanguage == null && sessionUserLanguage == null) {
				userRequestLanguage = LanguageRequestParameter.DEFAULT_LANGUAGE;
			}

			if ((sessionUserLanguage==null)||(!sessionUserLanguage.toString().equals(userRequestLanguage))) {
				if (userRequestLanguage != null)
					request.getSession().setAttribute(SESSION_LANGUAGE_PARAMETER, userRequestLanguage);
			}
		}
	}

	public String getSessionLanguage() {
		return request.getSession().getAttribute(SESSION_LANGUAGE_PARAMETER).toString();
	}

	public void updateSessionMenuId(UserPageRequestParameter userRequest) {		

		final Object lock = request.getSession().getId().intern();
		synchronized(lock) {
			userRequest.update(request);
			String userRequestMainMenuId = userRequest.getParameter();
			request.getSession().setAttribute(SESSION_MAIN_MENU_PARAMETER, userRequestMainMenuId);
		}
	}

	public String getSessionMenuId() {
		return request.getSession().getAttribute(SESSION_MAIN_MENU_PARAMETER).toString();
	}

	public void updateSessionSubMenuId(UserPageRequestParameter userRequest) {

		final Object lock = request.getSession().getId().intern();
		synchronized(lock) {
			userRequest.update(request);
			String userRequestSubMenuId = userRequest.getParameter();

			Object sessionMainMenuId = request.getSession().getAttribute(SESSION_MAIN_MENU_PARAMETER);

			Object sessionSubMenuId = request.getSession().getAttribute(SESSION_SUB_MENU_PARAMETER);

			if (userRequestSubMenuId==null) {
				if (sessionSubMenuId==null) {
					userRequestSubMenuId = "";
				} else {
					userRequestSubMenuId = sessionMainMenuId.toString();
				}
			}		
			request.getSession().setAttribute(SESSION_SUB_MENU_PARAMETER, userRequestSubMenuId);
		}
	}

	public String getSessionSubMenuId() {
		return request.getSession().getAttribute(SESSION_SUB_MENU_PARAMETER).toString();
	}

	public void updateSessionCustomerMenuId(UserPageRequestParameter userRequest) {

		final Object lock = request.getSession().getId().intern();
		synchronized(lock) {
			userRequest.update(request);
			String userRequestCustomerMenuId = userRequest.getParameter();
			request.getSession().setAttribute(SESSION_CUSTOMER_MENU_PARAMETER, userRequestCustomerMenuId);
		}
	}

	public String getSessionCustomerMenuId() {
		return request.getSession().getAttribute(SESSION_CUSTOMER_MENU_PARAMETER).toString();
	}

	public void updateSessionAttribute(String attribute, Object value) {
		final Object lock = request.getSession().getId().intern();
		synchronized(lock) {
			request.getSession().setAttribute(attribute, value);
		}
	}

	public String getSessionUserId() {
		String sessionUserId = "";		
		final Object lock = request.getSession().getId().intern();
		synchronized(lock) {
			Object userid = request.getSession().getAttribute(SESSION_CUSTOMER_LOGIN_PARAMETER);
			if (userid != null) {
				sessionUserId = userid.toString();
			}
		}
		return sessionUserId;
	}

	public boolean isUserLoggedIn() {
		return (request.getSession().getAttribute(SESSION_CUSTOMER_LOGIN_PARAMETER)!=null && (!session.getAttribute(SESSION_CUSTOMER_LOGIN_PARAMETER).equals("")));
	}

	public String getSessionCustomerId() {
		String sessionUserId = "";

		Object userid = request.getSession().getAttribute(SESSION_CUSTOMER_ID_PARAMETER);
		if (userid != null) {
			sessionUserId = userid.toString();
		}
		return sessionUserId;
	}

	public void updateParameter(UserPageRequestParameter userRequest) {
		userRequest.update(request);
	}

	public boolean isParameter(UserPageRequestParameter userRequest) {
		return (request.getSession().getAttribute(userRequest.getParameter()) != null);
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
