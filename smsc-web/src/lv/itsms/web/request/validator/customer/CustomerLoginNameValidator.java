package lv.itsms.web.request.validator.customer;

import lv.itsms.web.request.validator.rule.LoginNameIsNotReservedRule;
import lv.itsms.web.service.Repository;
import transfer.validator.Rule;
import transfer.validator.UserRequestValidatorImpl;
import transfer.validator.rule.FieldIsNotEmptyStringRule;
import transfer.validator.rule.MaxLengthRule;

public class CustomerLoginNameValidator extends UserRequestValidatorImpl {
	final static String ERROR_MESSAGE = "Customer.Login.is.mandatory";
	final static String MAX_LEN_ERROR_MESSAGE = "Login.name.maximum.length.exceeded";
	
	final static int MAX_LENGTH = 15;
	Repository repository;
			
	public CustomerLoginNameValidator(Repository repository) {
		this.repository = repository;
	}

	@Override
	public void prepareRules() {
		super.prepareRules();
		Rule rule = new FieldIsNotEmptyStringRule(ERROR_MESSAGE);
		rules.add(rule);
		
		rule = new LoginNameIsNotReservedRule(repository);
		rules.add(rule);
		
		rule = new MaxLengthRule(MAX_LENGTH, MAX_LEN_ERROR_MESSAGE);
		rules.add(rule);
	}	
}
