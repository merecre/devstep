package lv.itsms.web.request.validator;

import java.util.ArrayList;
import java.util.List;

import transfer.domain.Customer;
import transfer.validator.Rule;
import transfer.validator.UserRequestValidatorImpl;

public class LoginFieldsValidator extends UserRequestValidatorImpl {

	PasswordValidator passwordValidator;
	LoginnameValidator loginnameValidator;
	CustomerStatusValidator customerStatusValidator;
	List<Rule> rules;

	public LoginFieldsValidator() {
		this.rules = new ArrayList<Rule>();
		passwordValidator = new PasswordValidator();
		loginnameValidator = new LoginnameValidator();
		customerStatusValidator = new CustomerStatusValidator();
	}

	public LoginFieldsValidator(List<Rule> rules) {
		this.rules = rules;
	}

	public void prepareRules() {
		passwordValidator.prepareRules();
		loginnameValidator.prepareRules();
		customerStatusValidator.prepareRules();
	}
	
	@Override
	public boolean validate(Object object) {
		
		Customer customer = (Customer)object;
		
		String password = customer.getPassword();
		passwordValidator.validate(password);
		
		String loginName = customer.getUserLogin(); 
		loginnameValidator.validate(loginName);
		
		String customerStatus = customer.getStatus();
		System.out.println("Status:"+customerStatus);
		customerStatusValidator.validate(customerStatus);
		
		return true;
	}

	
}
