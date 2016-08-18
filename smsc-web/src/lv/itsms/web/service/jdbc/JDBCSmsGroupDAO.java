package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.service.DBDAOFactory;
import transfer.domain.SmsGroup;
import transfer.service.jpa.SmsGroupDAO;

public class JDBCSmsGroupDAO implements SmsGroupDAO {

	private final static String DB_TABLE = "sms_groups";

	private Connection connection;

	private DBDAOFactory factory;

	public JDBCSmsGroupDAO(DBDAOFactory factory) {
		this.factory = factory;
	}

	public void save(SmsGroup smsGroup) throws SQLException {
		int groupId = getIdByUserIdAndGroupName(smsGroup);

		if (groupId == DBDAOFactory.NO_RECORDS) {
			insert(smsGroup);
		} else {
			smsGroup.setSmsGroupId(groupId);
			update(smsGroup);
		}
	}

	public boolean deleteByUserIdAndGroupName(int userId, String groupName) throws SQLException {

		connection = factory.getConnection();
		PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM " + DB_TABLE + " "
						+ "WHERE "	+ "customer_id='" + userId + "' "
						+ "AND group_name='" + groupName + "'");
		statement.executeUpdate();
		
		statement.close();

		return true;
	}

	public boolean deleteByGroupId(int groupId) throws SQLException {

		connection = factory.getConnection();

		PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM " + DB_TABLE + " "
						+ "WHERE "	+ "id='" + groupId + "'");
		statement.executeUpdate();
		statement.close();

		return true;
	}

	public boolean insert(SmsGroup smsGroup) throws SQLException {

		connection = factory.getConnection();
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO " + DB_TABLE + " "
						+ "(group_message, group_name, sendtime, customer_id, status)"
						+ " VALUES (" 
						+ "'"+ smsGroup.getGroupMessage() + "',"
						+ "'"+ smsGroup.getSmsGroupName() + "',"
						+ "'"+ smsGroup.getSendTime() + "',"
						+ "'"+ smsGroup.getCustomerId() + "',"
						+ "'"+ smsGroup.getStatus() + "'"
						+ ") "
						+ " ON DUPLICATE KEY UPDATE " 
						+ " group_message = '"+smsGroup.getGroupMessage() + "',"
						+ " group_name = '"+smsGroup.getSmsGroupName() + "',"
						+ " sendtime = '"+smsGroup.getSendTime() + "',"
						+ " customer_id = '"+smsGroup.getCustomerId() + "',"
						+ " status = '" + smsGroup.getStatus() + "'"
						,Statement.RETURN_GENERATED_KEYS);

		int updatedRows = statement.executeUpdate();
		smsGroup.setSmsGroupId(getUpdatedGroupId(statement));

		statement.close();

		return updatedRows != 0;
	}

	private int getUpdatedGroupId (PreparedStatement statement) throws SQLException {
		final String ERROR_NO_ID = "Creating smsGroup failed, no ID obtained.";
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			}
		}
		throw new SQLException(ERROR_NO_ID);
	}

	public boolean update(SmsGroup smsGroup) throws SQLException {

		connection = factory.getConnection();
		String sql = "UPDATE " + DB_TABLE + " SET" 
				+ " group_message = '"+smsGroup.getGroupMessage() + "',"
				+ " group_name = '"+smsGroup.getSmsGroupName() + "',"
				+ " sendtime = '"+smsGroup.getSendTime() + "',"
				+ " customer_id = '"+smsGroup.getCustomerId() + "',"
				+ " status = '"+ smsGroup.getStatus() + "'"
				+ " WHERE id = '" + smsGroup.getSmsGroupId() +"'";

		PreparedStatement statement = connection.prepareStatement(sql);

		int updatedRows = statement.executeUpdate();

		statement.close();

		return (updatedRows != 0);
	}

	public int getIdByUserIdAndGroupName(SmsGroup smsGroup) throws SQLException {

		int groupId = DBDAOFactory.NO_RECORDS;

		String sql = "SELECT id FROM " + DB_TABLE 
				+ " WHERE group_name='" + smsGroup.getSmsGroupName() + "'"
				+ " AND customer_id='" + smsGroup.getCustomerId() + "'";


		connection = factory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			groupId = resultSet.getInt("id");
		}

		resultSet.close();
		statement.close();


		return groupId;
	}

	public List<SmsGroup> getGroupsByUserId(long userId) throws SQLException {

		List<SmsGroup> smsGroups = new ArrayList<>();

		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE customer_id='" + userId + "'";

		connection = factory.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			SmsGroup smsGroup = populateSmsGroup(resultSet);
			smsGroups.add(smsGroup);
		}

		resultSet.close();
		statement.close();

		return smsGroups;
	}

	public SmsGroup getGroupById(long groupId) throws SQLException {

		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE id='" + groupId + "'";

		connection = factory.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		SmsGroup smsGroup = null;

		if (resultSet.next()) {
			smsGroup = populateSmsGroup(resultSet);
		}

		resultSet.close();
		statement.close();

		return smsGroup;
	}

	@Override
	public SmsGroup getGroupByIdAndCustomerId(long groupId, int customerId) throws Exception {
		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE id='" + groupId + "'" 
				+ " AND customer_id='" + customerId  + "'";

		connection = factory.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		SmsGroup smsGroup = null;

		if (resultSet.next()) {
			smsGroup = populateSmsGroup(resultSet);
		}

		resultSet.close();
		statement.close();

		return smsGroup;
	}
	
	private SmsGroup populateSmsGroup(ResultSet resultSet) throws SQLException {
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(resultSet.getInt("customer_id"));
		smsGroup.setGroupMessage(resultSet.getString("group_message"));
		smsGroup.setSendTime(resultSet.getTimestamp("sendtime"));
		smsGroup.setSmsGroupId(resultSet.getInt("id"));
		smsGroup.setSmsGroupName(resultSet.getString("group_name"));

		return smsGroup;
	}

	@Override
	public List<SmsGroup> findSmsGroupsByDatePeriodAndUserId(long userId, String startDate, String endDate) throws SQLException {
		
		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE sendTime >='" + startDate + "'" 
				+ " AND sendTime <='" + endDate  + "'";

		connection = factory.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		List<SmsGroup> smsGroups = new ArrayList<>();;
		SmsGroup smsGroup = null;
		
		while (resultSet.next()) {
			smsGroup = populateSmsGroup(resultSet);
		}
		
		smsGroups.add(smsGroup);
		resultSet.close();
		statement.close();
		
		return smsGroups;
	}

	@Override
	public List<SmsGroup> findAll() throws SQLException {
		String sql = "SELECT * FROM " + DB_TABLE; 

		connection = factory.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		List<SmsGroup> smsGroups = new ArrayList<>();;
		SmsGroup smsGroup = null;
		
		while (resultSet.next()) {
			smsGroup = populateSmsGroup(resultSet);
		}
		
		smsGroups.add(smsGroup);
		resultSet.close();
		statement.close();
		
		return smsGroups;
	}
}
