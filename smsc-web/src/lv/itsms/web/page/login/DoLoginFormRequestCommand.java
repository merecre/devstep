package lv.itsms.web.page.login;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class DoLoginFormRequestCommand implements PageRequestCommand {

	Customer customer;

	Session session;

	LoginPageFactory factory;
			
	public DoLoginFormRequestCommand(LoginPageFactory factory) {
		this.factory = factory;
	}

	public DoLoginFormRequestCommand(Customer customer, Session session) {
		this.customer = customer;
		this.session = session;
	}

	@Override
	public void execute() throws RuntimeException {
		Customer customer = factory.getCustomer();
		factory.getSession().updateSessionAttribute(Session.SESSION_CUSTOMER_LOGIN_PARAMETER, customer.getUserLogin());
		factory.getSession().updateSessionAttribute(Session.SESSION_CUSTOMER_ID_PARAMETER, customer.getId());
	}
}
