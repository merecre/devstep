package lv.itsms.web.page.registration;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class DoRegistrationFormRequestCommand implements PageRequestCommand {


	private final static String COMMAND_INFO = "Customer.registered.successesfully";
	
	private Customer customer;
	private Repository repository;
	private Session session;

	public DoRegistrationFormRequestCommand(Customer customer, Repository repository, Session session) {
		this.customer = customer;
		this.repository = repository;
		this.session = session;
	}

	@Override
	public void execute() throws RuntimeException {	
		repository.insertCustomer(customer);
		
		session.updateSessionAttribute(Session.SESSION_INFORMATION_PARAMETER, COMMAND_INFO);
	}
}
