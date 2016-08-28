package lv.itsms.web.request.validator.customer;

import lv.itsms.web.request.validator.rule.ValidEmailFormatRule;
import transfer.validator.Rule;
import transfer.validator.UserRequestValidatorImpl;
import transfer.validator.rule.FieldIsNotEmptyStringRule;

public class CustomerEmailValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Customer.Email.is.mandatory";
	final static String EMAIL_ERROR_MESSAGE = "Email.format.is.not.valid";
	
	@Override
	public void prepareRules() {
		super.prepareRules();
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
		
		rule = new ValidEmailFormatRule(EMAIL_ERROR_MESSAGE);
		rules.add(rule);
	}
}
