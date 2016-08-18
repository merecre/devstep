package lv.itsms.web.service;

import transfer.domain.Sms;
import transfer.service.jpa.PhoneGroupDAO;
import transfer.service.jpa.SmsDAO;
import transfer.service.jpa.SmsGroupDAO;

public abstract class DAOFactory {

	public static final int DB_DAO = 1;
	public static final int TEST_DAO = 2;

	public abstract CustomerDAO getCustomerDAO();
	public abstract PhoneGroupDAO getPhoneGroupDAO();
	public abstract SmsGroupDAO getSmsGroupDAO();
	public abstract LoginInfoDAO getLoginInfoDAO();
	public abstract RegistrationInfoDAO getRegistrationInfoDAO();
	public abstract ProfileInfoDAO getProfileInfoDAO(); 
	public abstract SmsPanelInfoDAO getSmsPanelInfoDAO();
	public abstract SmsDAO getSmsDAO();

	public abstract void startTransaction() throws Exception;
	public abstract void stopTransaction() throws Exception;
	public abstract void commitTransaction() throws Exception;
	public abstract void rollbackTransaction() throws Exception;
	
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case DB_DAO :
			return new DBDAOFactory();
		default           : 
			return new TestDAOFactory();
		}
	}
}
