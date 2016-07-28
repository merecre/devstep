package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class LoginFormRequestParameterBuilder {

	UserPageRequest loginName;

	UserPageRequest password;

	public void build(HttpServletRequest request) {

		loginName = new LoginNameRequestParameter();
		loginName.update(request);

		password = new LoginPasswordRequestParameter();
		password.update(request);	
	}

	class LoginNameRequestParameter extends UserPageRequest {		
		final static String LOGIN_NAME_PARAMETER = "uname";

		public LoginNameRequestParameter() {
			super(LOGIN_NAME_PARAMETER);		
		}

		@Override
		public void update(HttpServletRequest request) {
			this.parameterValue = request.getParameter(parameterKey);			
		}
	}

	class LoginPasswordRequestParameter extends UserPageRequest {
		final static String LOGIN_PASSWORD_PARAMETER = "pass";

		public LoginPasswordRequestParameter() {
			super(LOGIN_PASSWORD_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			this.parameterValue = request.getParameter(parameterKey);			
		}
	}

	public UserPageRequest getLoginName() {
		return loginName;
	}

	public UserPageRequest getPassword() {
		return password;
	}
}
