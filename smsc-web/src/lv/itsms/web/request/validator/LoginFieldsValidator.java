package lv.itsms.web.request.validator;

import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.validator.rule.Rule;
import transfer.domain.Customer;

public class LoginFieldsValidator extends UserRequestValidatorImpl {

	PasswordValidator passwordValidator;
	LoginnameValidator loginnameValidator;
	
	List<Rule> rules;

	public LoginFieldsValidator() {
		this.rules = new ArrayList<Rule>();
		passwordValidator = new PasswordValidator();
		loginnameValidator = new LoginnameValidator();
	}

	public LoginFieldsValidator(List<Rule> rules) {
		this.rules = rules;
	}

	public void prepareRules() {
		passwordValidator.prepareRules();
		loginnameValidator.prepareRules();
	}
	
	@Override
	public boolean validate(Object object) {
		
		Customer customer = (Customer)object;
		
		String password = customer.getPassword();
		passwordValidator.validate(password);
		
		String loginName = customer.getUserLogin(); 
		loginnameValidator.validate(loginName);
		
		return true;
	}

	
}
