package lv.itsms.web.service.console;

import lv.itsms.web.service.CustomerDAO;
import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.LoginInfoDAO;
import lv.itsms.web.service.PhoneGroupDAO;
import lv.itsms.web.service.ProfileInfoDAO;
import lv.itsms.web.service.RegistrationInfoDAO;
import lv.itsms.web.service.SmsDAO;
import lv.itsms.web.service.SmsGroupDAO;

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

	@Override
	public ProfileInfoDAO getProfileInfoDAO() {
		return new TestProfileInfoDAO(this);
	}

}
