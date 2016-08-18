package lv.itsms.web.service.console;

import lv.itsms.web.service.CustomerDAO;
import lv.itsms.web.service.TestDAOFactory;
import transfer.domain.Customer;

public class TestCustomerDAO implements CustomerDAO {

	public TestCustomerDAO(TestDAOFactory testDAOFactory) {

	}

	@Override
	public Customer getCustomerByLoginAndPassword(String login, String password) throws Exception {
		System.out.println(login+" "+" "+password);
		if (login.equals("Test")&&(password.equals("Test"))) {
			Customer customer = new Customer();
			customer.setCustomerId(1);
			customer.setName("Test");
			customer.setPassword("Test");
			customer.setUserLogin("Test");
			customer.setEmail("emial@email");
			customer.setSurname("Surname");
			return customer;
		}
		return null;
	}

	@Override
	public void insert(Customer customer) throws Exception {
		customer.setId(10);
		System.out.print("Customer added " + customer.getId());
	}

	@Override
	public Customer getCustomerByLogin(String customerUserName) throws Exception {

		Customer customer = null;
		if (customerUserName.equals("TestLogin")) {
			customer = new Customer();
			customer.setCustomerId(1);
			customer.setName("Test");
			customer.setPassword("Test");
		}
		return customer;
	}

	@Override
	public boolean isCustomerByLogin(String customerUserName) throws Exception {
		Customer customer = getCustomerByLogin(customerUserName);
		if (customer == null) {
			return false;
		}

		return true;
	}

}
