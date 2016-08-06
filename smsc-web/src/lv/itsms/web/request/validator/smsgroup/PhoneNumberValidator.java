package lv.itsms.web.request.validator.smsgroup;

import lv.itsms.web.request.validator.UserRequestValidatorImpl;
import lv.itsms.web.request.validator.rule.FieldIsNotEmptyStringRule;
import lv.itsms.web.request.validator.rule.Rule;

public class PhoneNumberValidator extends UserRequestValidatorImpl {

	final static String ERR_PHONENUMBER_IS_EMPTY = "Phone number is mandatory";
	
	String phoneNumber;
	
	public void prepareRules() {
		Rule rule = new FieldIsNotEmptyStringRule(ERR_PHONENUMBER_IS_EMPTY);
		this.addRule(rule);
	}
}
