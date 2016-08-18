package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lv.itsms.web.service.DBDAOFactory;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;
import transfer.service.jpa.SmsDAO;

public class JDBCSmsDAO implements SmsDAO {

	private final static String DB_TABLE = "test_schema.Sms";

	private Connection connection;

	private DBDAOFactory factory;

	public JDBCSmsDAO(DBDAOFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public Sms findSMSByCustomerId(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sms> findAllNewSms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sms findSms(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSms(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sms createSms(long customerID, String phoneNumber, String message, String sendTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sms createSms(long customerID, String phoneNumber, String message, Date sendTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSmsStatus(long smsId, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSmsMessageSMPPID(long smsId, long messageSmscId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sms findSmsBySMPPID(long SMPPID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSmsSMPPStatus(long smsId, int newStatus) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sms insert(Sms sms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBySmsGroupId(long groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBySmsId(long smsId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Sms> findSmsByDatePeriodAndUserId(long userId, Timestamp startDate, Timestamp endDate) throws Exception {
		String sql = "SELECT * FROM " + DB_TABLE 
		+ " BETWEEN UNIX_TIMESTAMP('"+startDate+"')"
		+ " AND UNIX_TIMESTAMP('"+ endDate+"')";
		
		System.out.println("sql:"+sql);
		connection = factory.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		List<Sms> smses = new ArrayList<>();
		Sms sms = null;
		
		while (resultSet.next()) {
			sms = populateSms(resultSet);
		}
		
		smses.add(sms);
		resultSet.close();
		statement.close();
		
		return smses;
	}

	private Sms populateSms(ResultSet resultSet) throws SQLException {
		Sms sms = new Sms();
		
		sms.setMSSID(resultSet.getLong("id"));
		sms.setPhoneNumber(resultSet.getString("phonenumber"));
		sms.setMessage(resultSet.getString("message"));
		sms.setCustomerID(resultSet.getLong("customerId"));
		sms.setStatus(resultSet.getString("status"));
		sms.setConcatenated(resultSet.getByte("concatenated"));
		sms.setSendTime(resultSet.getTimestamp("sendtime"));
		
		return sms;
	}

}
