package lv.itsms.web.request.validator;

public class CustomerPasswordNotEmpty implements Rule {

	final static String ERROR_MESSAGE = "Password is mandatory."; 

	String passsword;

	public CustomerPasswordNotEmpty(String passsword) {
		this.passsword = passsword;
	}

	@Override
	public boolean doRule() {
		if (passsword!=null && !passsword.isEmpty()) {
			return true;
		}

		throw new RuntimeException(ERROR_MESSAGE);
	}

}
