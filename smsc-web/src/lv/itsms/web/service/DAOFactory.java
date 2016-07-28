package lv.itsms.web.service;

public abstract class DAOFactory {

	public static final int DB_DAO = 1;
	public static final int TEST_DAO = 2;

	public abstract CustomerDAO getCustomerDAO();
	public abstract PhoneGroupDAO getPhoneGroupDAO();
	public abstract SmsDAO getSmsDAO();
	public abstract SmsGroupDAO getSmsGroupDAO();
	public abstract LoginInfoDAO getLoginInfoDAO();
	public abstract RegistrationInfoDAO getRegistrationInfoDAO();

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case DB_DAO :
			return new DBDAOFactory();
		default           : 
			return new TestDAOFactory();
		}
	}
}
