package lv.itsms.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import lv.itsms.web.service.jdbc.JDBCCustomerDAO;
import lv.itsms.web.service.jdbc.JDBCLoginInfoDAO;
import lv.itsms.web.service.jdbc.JDBCPhoneGroupDAO;
import lv.itsms.web.service.jdbc.JDBCRegistrationInfoDAO;
import lv.itsms.web.service.jdbc.JDBCSmsDAO;
import lv.itsms.web.service.jdbc.JDBCSmsGroupDAO;
import lv.itsms.web.service.jdbc.ServerContextSingleton;

public class DBDAOFactory extends DAOFactory {

	public static final int NO_RECORDS = -1;

	private DataSource dataSource;

	Connection connection;

	public Connection createConnection() {

		Connection connection = null;
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
	public SmsDAO getSmsDAO() {
		return new JDBCSmsDAO(this);
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
}
