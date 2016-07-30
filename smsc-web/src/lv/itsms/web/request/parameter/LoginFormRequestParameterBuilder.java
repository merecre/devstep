package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class LoginFormRequestParameterBuilder {

	UserPageRequestParameter loginName;

	UserPageRequestParameter password;

	public void build(HttpServletRequest request) {

		loginName = new LoginNameRequestParameter();
		loginName.update(request);

		password = new LoginPasswordRequestParameter();
		password.update(request);	
	}

	class LoginNameRequestParameter extends UserPageRequestParameter {		
		final static String LOGIN_NAME_PARAMETER = "uname";

		public LoginNameRequestParameter() {
			super(LOGIN_NAME_PARAMETER);		
		}

		@Override
		public void update(HttpServletRequest request) {
			this.parameterValue = request.getParameter(parameterKey);			
		}
	}

	class LoginPasswordRequestParameter extends UserPageRequestParameter {
		final static String LOGIN_PASSWORD_PARAMETER = "pass";

		public LoginPasswordRequestParameter() {
			super(LOGIN_PASSWORD_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			this.parameterValue = request.getParameter(parameterKey);			
		}
	}

	public UserPageRequestParameter getLoginName() {
		return loginName;
	}

	public UserPageRequestParameter getPassword() {
		return password;
	}
}
