package lv.itsms.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import lv.itsms.web.service.jdbc.JDBCCustomerDAO;
import lv.itsms.web.service.jdbc.JDBCLoginInfoDAO;
import lv.itsms.web.service.jdbc.JDBCPhoneGroupDAO;
import lv.itsms.web.service.jdbc.JDBCProfileInfoDAO;
import lv.itsms.web.service.jdbc.JDBCRegistrationInfoDAO;
import lv.itsms.web.service.jdbc.JDBCSmsDAO;
import lv.itsms.web.service.jdbc.JDBCSmsGroupDAO;
import lv.itsms.web.service.jdbc.JDBCSmsPanelInfoDAO;
import lv.itsms.web.service.jdbc.ServerContextSingleton;
import transfer.service.jpa.CustomerDAO;
import transfer.service.jpa.DeliveryStatusDAO;
import transfer.service.jpa.PhoneGroupDAO;
import transfer.service.jpa.SmsDAO;
import transfer.service.jpa.SmsGroupDAO;

public class DBDAOFactory extends DAOFactory {

	public static final int NO_RECORDS = -1;

	private DataSource dataSource;

	Connection connection;

	public Connection createConnection() {

		try {
			ServerContextSingleton serverContext = ServerContextSingleton.getInstance();
			dataSource = serverContext.getDataSource();
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public void closeConnection(Connection connection) {

		try {
			if (connection!=null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		return new JDBCCustomerDAO(this);
	}

	@Override
	public PhoneGroupDAO getPhoneGroupDAO() {	
		return new JDBCPhoneGroupDAO(this);
	}

	@Override
	public SmsGroupDAO getSmsGroupDAO() {
		return new JDBCSmsGroupDAO(this);
	}

	@Override
	public LoginInfoDAO getLoginInfoDAO() {
		return new JDBCLoginInfoDAO(this);
	}

	@Override
	public RegistrationInfoDAO getRegistrationInfoDAO() {
		return new JDBCRegistrationInfoDAO(this);
	}

	@Override
	public ProfileInfoDAO getProfileInfoDAO() {		
		return new JDBCProfileInfoDAO(this);
	}

	@Override
	public SmsPanelInfoDAO getSmsPanelInfoDAO() {
		return new JDBCSmsPanelInfoDAO(this);
	}
	
	@Override
	public SmsDAO getSmsDAO() {
		return new JDBCSmsDAO(this);
	}
	
	@Override
	public void startTransaction() throws Exception {
		createConnection() ;		
		connection.setAutoCommit(false);

	}

	@Override
	public void stopTransaction() {
		closeConnection(connection);
	}

	@Override
	public void commitTransaction() throws Exception {
		connection.commit();
	}

	@Override
	public void rollbackTransaction() throws Exception {
		connection.rollback();	
	}

	public static int getNoRecords() {
		return NO_RECORDS;
	}

	public Connection getConnection() {
		if (connection == null ) {
			return createConnection();
		}
		return connection;
	}

	@Override
	public DeliveryStatusDAO getDeliveryStatusDAO() {
		// TODO Auto-generated method stub
		return null;
	}
}
