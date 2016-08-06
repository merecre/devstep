package lv.itsms.web.request.validator.customer;

import lv.itsms.web.request.validator.UserRequestValidatorImpl;
import lv.itsms.web.request.validator.rule.FieldIsNotEmptyStringRule;
import lv.itsms.web.request.validator.rule.Rule;
import lv.itsms.web.request.validator.rule.ValidEmailFormatRule;

public class CustomerEmailValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Customer Email is mandatory";
	final static String EMAIL_ERROR_MESSAGE = "Email format is not valid";
	
	@Override
	public void prepareRules() {
		super.prepareRules();
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
		
		rule = new ValidEmailFormatRule(EMAIL_ERROR_MESSAGE);
		rules.add(rule);
	}
}
