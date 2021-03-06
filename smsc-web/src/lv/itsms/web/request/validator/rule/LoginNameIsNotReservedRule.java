package lv.itsms.web.request.validator.rule;

import lv.itsms.web.service.Repository;
import transfer.validator.Rule;

public class LoginNameIsNotReservedRule implements Rule {

	Repository repository;

	String customerUserName;

	public LoginNameIsNotReservedRule(Repository repository, String customerUserName) {
		this.repository = repository;
		this.customerUserName = customerUserName;
	}

	public LoginNameIsNotReservedRule(Repository repository) {
		this.repository = repository;
	}

	@Override
	public boolean doRule(Object object) {

		String customerUserName = (String)object;
		final String ERROR_MESSAGE = "Login name " + customerUserName + " is in use";
		boolean isCustomer = repository.isCustomerByLogin(customerUserName);

		if (isCustomer) {
			throw new RuntimeException(ERROR_MESSAGE);
		}

		return true;
	}


}
