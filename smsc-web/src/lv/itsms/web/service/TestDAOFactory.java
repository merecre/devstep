package lv.itsms.web.service;

import lv.itsms.web.service.console.TestCustomerDAO;
import lv.itsms.web.service.console.TestLoginInfoDAO;
import lv.itsms.web.service.console.TestPhoneGroupDAO;
import lv.itsms.web.service.console.TestProfileInfoDAO;
import lv.itsms.web.service.console.TestSmsDAO;
import lv.itsms.web.service.console.TestSmsGroupDAO;
import lv.itsms.web.service.console.TestSmsPanelInfoDAO;
import transfer.service.jpa.PhoneGroupDAO;
import transfer.service.jpa.SmsDAO;
import transfer.service.jpa.SmsGroupDAO;

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
	public SmsGroupDAO getSmsGroupDAO() {
		return new TestSmsGroupDAO(this);
	}

	@Override
	public LoginInfoDAO getLoginInfoDAO() {
		return new TestLoginInfoDAO();
	}

	@Override
	public RegistrationInfoDAO getRegistrationInfoDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsPanelInfoDAO getSmsPanelInfoDAO() {
		return new TestSmsPanelInfoDAO();
	}
	
	@Override
	public ProfileInfoDAO getProfileInfoDAO() {
		return new TestProfileInfoDAO(this);
	}

	@Override
	public void startTransaction() {
		System.out.println("Transaction started.");		
	}

	@Override
	public void stopTransaction() {
		System.out.println("Transaction stopped.");		
	}

	@Override
	public void commitTransaction() {
		System.out.println("Transaction committed.");		
	}

	@Override
	public void rollbackTransaction() {
		System.out.println("Transaction rollbacked.");		
	}

	@Override
	public SmsDAO getSmsDAO() {
		return new TestSmsDAO(this);
	}
}
