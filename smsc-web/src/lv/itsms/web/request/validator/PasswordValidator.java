package lv.itsms.web.request.validator;

import transfer.validator.Rule;
import transfer.validator.UserRequestValidatorImpl;
import transfer.validator.rule.FieldIsNotEmptyStringRule;

public class PasswordValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Password.is.mandatory."; 

	@Override
	public void prepareRules () {	
		super.prepareRules();
		
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
	}	
}
