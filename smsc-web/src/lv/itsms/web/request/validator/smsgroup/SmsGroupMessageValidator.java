package lv.itsms.web.request.validator.smsgroup;

import lv.itsms.web.request.validator.UserRequestValidatorImpl;
import lv.itsms.web.request.validator.rule.FieldIsNotEmptyStringRule;
import lv.itsms.web.request.validator.rule.Rule;

public class SmsGroupMessageValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Sms message is mandatory";
	
	@Override
	public void prepareRules() {
		super.prepareRules();
		
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
	}

	
}
