package lv.itsms.web.page.login;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class LoginPasswordRequestParameter extends UserPageRequestParameter {
	public final static String LOGIN_PASSWORD_PARAMETER = "pass";
	
	public LoginPasswordRequestParameter() {
		super(LOGIN_PASSWORD_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		this.parameterValue = request.getParameter(parameterKey);			
	}
}
