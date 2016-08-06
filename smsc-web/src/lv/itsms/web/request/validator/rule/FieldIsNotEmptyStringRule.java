package lv.itsms.web.request.validator.rule;

public class FieldIsNotEmptyStringRule implements Rule {

	String errorMessage;

	public FieldIsNotEmptyStringRule(String field, String erorrMessage) {
		this.errorMessage = erorrMessage;
	}

	public FieldIsNotEmptyStringRule(String erorrMessage) {
		this.errorMessage = erorrMessage;
	}

	@Override
	public boolean doRule(Object object) {
		String field = (String) object;
		if (field != null && !field.isEmpty()) {
			return true;
		}

		throw new RuntimeException(errorMessage);
	}
}
