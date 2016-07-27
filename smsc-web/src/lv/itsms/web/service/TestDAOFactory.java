package lv.itsms.web.service;

import lv.itsms.web.service.console.TestCustomerDAO;
import lv.itsms.web.service.console.TestPhoneGroupDAO;
import lv.itsms.web.service.console.TestSmsGroupDAO;

public class TestDAOFactory extends DAOFactory {

	@Override
	public CustomerDAO getCustomerDAO() {
		return new TestCustomerDAO(this);
	}

	@Override
	public PhoneGroupDAO getPhoneGroupDAO() {
		return new TestPhoneGroupDAO();
	}

	@Override
	public SmsDAO getSmsDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsGroupDAO getSmsGroupDAO() {
		return new TestSmsGroupDAO(this);
	}

	@Override
	public LoginInfoDAO getLoginInfoDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationInfoDAO getRegistrationInfoDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
