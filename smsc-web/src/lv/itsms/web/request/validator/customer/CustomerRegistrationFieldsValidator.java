package lv.itsms.web.request.validator.customer;

import lv.itsms.web.request.validator.PasswordValidator;
import lv.itsms.web.request.validator.UserRequestValidatorImpl;
import lv.itsms.web.service.Repository;
import transfer.domain.Customer;

public class CustomerRegistrationFieldsValidator extends UserRequestValidatorImpl {
	
	CustomerNameValidator customerNameValidator;
	CustomerSurnameValidator customerSurnameValidator;
	CustomerLoginNameValidator customerLoginNameValidator;
	CustomerEmailValidator customerEmailValidator;
	PasswordValidator passwordValidator;
	
	public CustomerRegistrationFieldsValidator(Repository repository) {
		customerNameValidator = new CustomerNameValidator();
		customerSurnameValidator = new CustomerSurnameValidator();
		customerLoginNameValidator = new CustomerLoginNameValidator(repository);
		customerEmailValidator = new CustomerEmailValidator();
		passwordValidator = new PasswordValidator();
	}

	@Override
	public void prepareRules() {
		super.prepareRules();
		customerNameValidator.prepareRules();
		customerSurnameValidator.prepareRules();
		customerLoginNameValidator.prepareRules();
		customerEmailValidator.prepareRules();
		passwordValidator.prepareRules();
	}

	@Override
	public boolean validate(Object object) {
		Customer customer = (Customer)object;
		
		return customerNameValidator.validate(customer.getName())
				&& customerSurnameValidator.validate(customer.getSurname())
				&& 	customerLoginNameValidator.validate(customer.getUserLogin())
				&& 	customerEmailValidator.validate(customer.getEmail())
				&&  passwordValidator.validate(customer.getPassword());
	}	
}
