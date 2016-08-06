package lv.itsms.web.request.validator;

import lv.itsms.web.request.validator.rule.FieldIsNotEmptyStringRule;
import lv.itsms.web.request.validator.rule.Rule;

public class LoginnameValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Login name is mandatory."; 

	@Override
	public void prepareRules() {
		super.prepareRules();
		
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
	}	
}
