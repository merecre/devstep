package lv.itsms.web.page.login;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class LoginNameRequestParameter extends UserPageRequestParameter {
	
	public final static String LOGIN_NAME_PARAMETER = "uname";
	public LoginNameRequestParameter() {
		super(LOGIN_NAME_PARAMETER);		
	}

	@Override
	public void update(HttpServletRequest request) {
		this.parameterValue = request.getParameter(parameterKey);			
	}
}
