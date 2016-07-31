package lv.itsms.web.request.parameter.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class CustomerIdRequestParameter extends UserPageRequestParameter {

	final static String USER_ID_PARAMETER = "customerid";

	public CustomerIdRequestParameter() {
		super(USER_ID_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		parameterValue = session.getAttribute(parameterKey).toString();

		if (parameterValue == null) {
			parameterValue = "";
		}
	}

}
