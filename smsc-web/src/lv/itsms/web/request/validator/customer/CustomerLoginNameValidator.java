package lv.itsms.web.request.validator.customer;

import lv.itsms.web.request.validator.UserRequestValidatorImpl;
import lv.itsms.web.request.validator.rule.LoginNameIsNotReserved;
import lv.itsms.web.request.validator.rule.FieldIsNotEmptyStringRule;
import lv.itsms.web.request.validator.rule.MaxLengthRule;
import lv.itsms.web.request.validator.rule.Rule;
import lv.itsms.web.service.Repository;

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
		
		rule = new LoginNameIsNotReserved(repository);
		rules.add(rule);
		
		rule = new MaxLengthRule(MAX_LENGTH, MAX_LEN_ERROR_MESSAGE);
		rules.add(rule);
	}	
}
