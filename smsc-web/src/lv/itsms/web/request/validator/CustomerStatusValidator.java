package lv.itsms.web.request.validator;

import lv.itsms.web.request.validator.rule.CustomerStatusIsActiveRule;
import transfer.validator.Rule;
import transfer.validator.UserRequestValidatorImpl;
import transfer.validator.rule.FieldIsNotEmptyStringRule;

public class CustomerStatusValidator extends UserRequestValidatorImpl {
	
	final static String ERROR_MESSAGE = "Customer is not active";
	
	@Override
	public void prepareRules() {
		super.prepareRules();
		
		Rule rule = new CustomerStatusIsActiveRule(ERROR_MESSAGE);
		rules.add(rule);
	}
}
