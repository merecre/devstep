package lv.itsms.web.request.validator;

import lv.itsms.web.service.Repository;

public class CustomerNameIsNotUsed implements Rule {

	Repository repository;

	String customerUserName;
			
	public CustomerNameIsNotUsed(Repository repository, String customerUserName) {
		this.repository = repository;
		this.customerUserName = customerUserName;
	}

	@Override
	public boolean doRule() {

		final String ERROR_MESSAGE = "Login name " + customerUserName + " is in use";
		boolean isCustomer = repository.isCustomerByLogin(customerUserName);

		if (isCustomer) {
			throw new RuntimeException(ERROR_MESSAGE);
		}
			
		return false;
	}
	
	
}
