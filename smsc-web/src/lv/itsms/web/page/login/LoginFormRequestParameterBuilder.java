package lv.itsms.web.page.login;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class LoginFormRequestParameterBuilder {
	
	UserPageRequestParameter loginName;

	UserPageRequestParameter password;

	public void build(HttpServletRequest request) {

		loginName = new LoginNameRequestParameter();
		loginName.update(request);

		password = new LoginPasswordRequestParameter();
		password.update(request);	
	}

	public UserPageRequestParameter getLoginName() {
		return loginName;
	}

	public UserPageRequestParameter getPassword() {
		return password;
	}
}
