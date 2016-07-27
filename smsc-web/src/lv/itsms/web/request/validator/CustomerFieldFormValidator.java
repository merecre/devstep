package lv.itsms.web.request.validator;

import java.util.List;

import transfer.domain.Customer;

public class CustomerFieldFormValidator extends UserRequestValidatorImpl {

	Customer customer;

	public CustomerFieldFormValidator(List<Rule> rules, Customer customer) {
		this.customer = customer;
		this.rules = rules;
	}	
}
