package lv.itsms.web.request.validator.rule;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ValidEmailFormatRule implements Rule {

	String errorMessage;

	public ValidEmailFormatRule(String emailErrorMessage) {
		this.errorMessage = emailErrorMessage;
	}

	@Override
	public boolean doRule(Object object) {
		String email = (String)object;
		boolean result = isValidEmailAddress(email);
		if (!result) {
			throw new RuntimeException(errorMessage);
		}
		return true;
	}

	private  boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
}
