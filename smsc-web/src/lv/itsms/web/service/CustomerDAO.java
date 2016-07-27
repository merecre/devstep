package lv.itsms.web.service;

import transfer.domain.Customer;

public interface CustomerDAO {

	Customer getCustomerByLoginAndPassword(String login, String password) throws Exception;
	void insert(Customer customer) throws Exception;
	Customer getCustomerByLogin(String customerUserName) throws Exception;	
	boolean isCustomerByLogin(String customerUserName) throws Exception;
}
