package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.SmsDAO;
import transfer.domain.Sms;

public class JDBCSmsDAO implements SmsDAO {

	private Connection connection;
	
	private DBDAOFactory factory;
	
	public JDBCSmsDAO(DBDAOFactory factory) {
		this.factory = factory;
	}
	
	private Sms sms;
	
	public boolean insert(Sms sms) throws Exception {
		
		//establishConnection();
		
		final String ERROR_INSERT = "Creating SMS failed, no rows affected.";
		
		connection = factory.createConnection();
		
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO sms "
						+ "(phonenumber, message, customerId, sendtime)"
						+ " VALUES (" 
						+ "'"+ sms.getPhoneNumber() + "',"
						+ "'"+ sms.getMessage() + "',"
						+ "'"+ sms.getCustomerID() + "',"
						+ "'"+ sms.getSendTime() + "'"
						+ ") "
						+ " ON DUPLICATE KEY UPDATE " 
						+ " phonenumber = '"+sms.getPhoneNumber() + "',"
						+ " message = '"+sms.getMessage() + "',"
						+ " customerId = '"+sms.getCustomerID() + "',"
						+ " sendtime = '"+sms.getSendTime() + "'"     		
						, Statement.RETURN_GENERATED_KEYS);

		int updatedRows = statement.executeUpdate();
		statement.close();		
		factory.closeConnection(connection);
		
		if (updatedRows == 0) {
			throw new SQLException(ERROR_INSERT);
		}
		this.sms = sms;
		this.sms.setMSSID(getGeneratedGroupId(statement));
		
		return true;
	}
	
	private int getGeneratedGroupId (PreparedStatement statement) throws SQLException {
		final String ERROR_NO_ID = "Creating smsGroup failed, no ID obtained.";
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
            	return generatedKeys.getInt(1);
            }
        }
        throw new SQLException(ERROR_NO_ID);
	}
}
