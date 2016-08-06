package lv.itsms.web.request.validator.rule;

public class MaxLengthRule implements Rule {

	String errorMessage;

	int maxLength;

	public MaxLengthRule(int maxLength, String erorrMessage) {
		this.errorMessage = erorrMessage;
		this.maxLength = maxLength;
	}

	@Override
	public boolean doRule(Object object) {

		String field = (String) object;
		if (field.length() > maxLength) {
			throw new RuntimeException(errorMessage);
		}

		return true;
	}
}
