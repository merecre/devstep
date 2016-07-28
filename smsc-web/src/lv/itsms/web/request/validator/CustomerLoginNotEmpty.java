package lv.itsms.web.request.validator;

public class CustomerLoginNotEmpty implements Rule {
	final static String ERROR_MESSAGE = "Login is mandatory.";

	String userLogin;

	public CustomerLoginNotEmpty(String userLogin) {
		super();
		this.userLogin = userLogin;
	}

	@Override
	public boolean doRule() {
		if (userLogin!=null && !userLogin.isEmpty()) {
			return true;
		}

		throw new RuntimeException(ERROR_MESSAGE);
	}
}
