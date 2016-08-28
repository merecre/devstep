package lv.itsms.web.request.validator.customer;

import transfer.validator.Rule;
import transfer.validator.UserRequestValidatorImpl;
import transfer.validator.rule.FieldIsNotEmptyStringRule;

public class CustomerNameValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Customer.Name.is.mandatory";
	
	@Override
	public void prepareRules() {
		super.prepareRules();
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
	}	
}
