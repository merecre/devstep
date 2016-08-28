package lv.itsms.web.request.validator.smsgroup;

import transfer.validator.Rule;
import transfer.validator.UserRequestValidatorImpl;
import transfer.validator.rule.FieldIsNotEmptyStringRule;

public class SmsGroupDescriptionValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Description.is.mandatory";
	
	@Override
	public void prepareRules() {
		super.prepareRules();
		
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
		
	}
}
