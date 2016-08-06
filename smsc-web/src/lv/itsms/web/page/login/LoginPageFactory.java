package lv.itsms.web.page.login;

import lv.itsms.web.command.CommandFactory;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class LoginPageFactory extends CommandFactory {

	Customer customer;
	
	Session session;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	protected Class<? extends CommandFactory> getGlazz() {
		return this.getClass();
	}	
}
