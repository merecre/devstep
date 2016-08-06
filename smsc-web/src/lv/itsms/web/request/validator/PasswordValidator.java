package lv.itsms.web.request.validator;

import java.util.ArrayList;

import lv.itsms.web.request.validator.rule.FieldIsNotEmptyStringRule;
import lv.itsms.web.request.validator.rule.Rule;

public class PasswordValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Password is mandatory."; 

	@Override
	public void prepareRules () {	
		super.prepareRules();
		
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
	}	
}
