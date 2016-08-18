package lv.itsms.web.page.registration;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.service.Repository;
import transfer.domain.Customer;

public class DoRegistrationFormRequestCommand implements PageRequestCommand {

	private Customer customer;
	private Repository repository;

	public DoRegistrationFormRequestCommand(Customer customer, Repository repository) {
		this.customer = customer;
		this.repository = repository;
	}

	@Override
	public void execute() throws RuntimeException {	
		repository.insertCustomer(customer);
	}
}
