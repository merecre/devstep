package lv.itsms.web.request.validator;

public class CustomerEmailNotEmpty implements Rule {
	final static String ERROR_MESSAGE = "Email is mandatory."; 

	String email;

	public CustomerEmailNotEmpty(String email) {
		this.email = email;
	}

	@Override
	public boolean doRule() throws RuntimeException {

		if (email!=null && !email.isEmpty()) {
			return true;
		}

		throw new RuntimeException(ERROR_MESSAGE);
	}
}
