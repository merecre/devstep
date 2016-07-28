package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.SmsDAO;
import transfer.domain.Sms;

public class JDBCSmsDAO implements SmsDAO {

	private final static String DB_TABLE = "sms";

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

	@Override
	public List<Sms> findSmsGroupsByDatePeriodAndUserId(long customerId, String startDate, String endDate) throws Exception {	
		final String FATAL_ERROR_MESSAGE = "Error during loading sms by date period occured";
		final String NO_SMS_MESSAGE = "No sms found";

		List<Sms> smsGroups = new ArrayList<>();

		String sql = "SELECT * from " + DB_TABLE 
				+ " WHERE customerId = '" + customerId 
				+ "' AND sendtime >= '" + startDate 
				+ "' AND sendtime <= '" + endDate+"'";

		connection = factory.createConnection();
		PreparedStatement statement = connection.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			sms = populateData(resultSet);
			smsGroups.add(sms);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		/*
		if (smsGroups.size() == 0) {
			throw new RuntimeException(NO_SMS_MESSAGE);
		}
		 */
		return smsGroups;
	}

	private Sms populateData (ResultSet resultSet) throws SQLException {

		Sms sms = new Sms();

		sms.setMSSID(resultSet.getInt("id"));
		sms.setPhoneNumber(resultSet.getString("phonenumber"));
		sms.setCustomerID(resultSet.getLong("customerId"));
		sms.setStatus(resultSet.getString("status"));
		sms.setConcatenated(resultSet.getByte("concatenated"));
		sms.setSendTime(resultSet.getDate("sendtime"));
		sms.setCountryCode(resultSet.getByte("countrycode"));
		sms.setSender(resultSet.getString("sender"));
		sms.setUnicode(resultSet.getByte("unicode"));
		sms.setLongSms(resultSet.getByte("long_sms"));
		sms.setNetwork(resultSet.getString("network"));

		return sms;
	}
}
