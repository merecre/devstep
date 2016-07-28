package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.SmsGroupDAO;
import transfer.domain.PhoneGroup;
import transfer.domain.SmsGroup;

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

		//establishConnection();

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM " + DB_TABLE + " "
						+ "WHERE "	+ "customer_id='" + userId + "' "
						+ "AND group_name='" + groupName + "'");
		System.out.println("Statement:"+ statement.toString());
		statement.executeUpdate();
		//connection.commit();

		statement.close();
		factory.closeConnection(connection);

		return true;
	}

	public boolean deleteByGroupId(int groupId) throws SQLException {

		//establishConnection();

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM " + DB_TABLE + " "
						+ "WHERE "	+ "id='" + groupId + "'");
		System.out.println("Statement:"+ statement.toString());
		statement.executeUpdate();
		//connection.commit();
		statement.close();
		factory.closeConnection(connection);

		return true;
	}

	public boolean insert(SmsGroup smsGroup) throws SQLException {
		final String ERROR_INSERT = "Creating smsGroup failed, no rows affected.";

		//establishConnection();

		//this.smsGroup = smsGroup;
		connection = factory.createConnection();
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO " + DB_TABLE + " "
						+ "(group_message, group_name, sendtime, customer_id)"
						+ " VALUES (" 
						+ "'"+ smsGroup.getGroupMessage() + "',"
						+ "'"+ smsGroup.getSmsGroupName() + "',"
						+ "'"+ smsGroup.getSendTime() + "',"
						+ "'"+ smsGroup.getCustomerId() + "'"
						+ ") "
						+ " ON DUPLICATE KEY UPDATE " 
						+ " group_message = '"+smsGroup.getGroupMessage() + "',"
						+ " group_name = '"+smsGroup.getSmsGroupName() + "',"
						+ " sendtime = '"+smsGroup.getSendTime() + "',"
						+ " customer_id = '"+smsGroup.getCustomerId() + "'"            		
						,Statement.RETURN_GENERATED_KEYS);

		System.out.println("Statement:"+statement.toString());

		int updatedRows = statement.executeUpdate();
		smsGroup.setSmsGroupId(getUpdatedGroupId(statement));

		//connection.setAutoCommit(false);
		//connection.commit();
		statement.close();
		factory.closeConnection(connection);
		System.out.println("Connection in SMS groupDAO" + connection);
		//connection.close();

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

		final String ERROR_UPDATE = "Creating smsGroup failed, no rows affected.";

		//establishConnection();

		//this.smsGroup = smsGroup;
		connection = factory.createConnection();
		String sql = "UPDATE " + DB_TABLE + " SET" 
				+ " group_message = '"+smsGroup.getGroupMessage() + "',"
				+ " group_name = '"+smsGroup.getSmsGroupName() + "',"
				+ " sendtime = '"+smsGroup.getSendTime() + "',"
				+ " customer_id = '"+smsGroup.getCustomerId() + "'"
				+ " WHERE id = '" + smsGroup.getSmsGroupId() +"'";

		PreparedStatement statement = connection.prepareStatement(sql);

		System.out.println("Statement:"+statement.toString());
		int updatedRows = statement.executeUpdate();

		//connection.setAutoCommit(false);
		//connection.commit();
		statement.close();
		factory.closeConnection(connection);
		System.out.println("Connection in SMS groupDAO" + connection);
		//connection.close();
		return (updatedRows != 0);
	}

	public int getIdByUserIdAndGroupName(SmsGroup smsGroup) throws SQLException {

		//establishConnection();

		int groupId = DBDAOFactory.NO_RECORDS;

		String sql = "SELECT id FROM " + DB_TABLE 
				+ " WHERE group_name='" + smsGroup.getSmsGroupName() + "'"
				+ " AND customer_id='" + smsGroup.getCustomerId() + "'";

		connection = factory.createConnection();
		PreparedStatement statement = connection.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			groupId = resultSet.getInt("id");
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		//System.out.println("Select "+statement.toString());
		//System.out.println("Select "+groupId+" "+groupId);

		return groupId;
	}

	public List<SmsGroup> getGroupsByUserId(long userId) throws SQLException {

		//establishConnection();

		List<SmsGroup> smsGroups = new ArrayList<>();

		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE customer_id='" + userId + "'";

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			SmsGroup smsGroup = new SmsGroup();
			smsGroup.setCustomerId(resultSet.getInt("customer_id"));
			smsGroup.setGroupMessage(resultSet.getString("group_message"));
			smsGroup.setSendTime(resultSet.getDate("sendtime"));
			smsGroup.setSmsGroupId(resultSet.getInt("id"));
			smsGroup.setSmsGroupName(resultSet.getString("group_name"));

			smsGroups.add(smsGroup);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		return smsGroups;
	}

	public SmsGroup getGroupById(long groupId) throws SQLException {
		final String ERROR_GET_GROUP = "Sms group record not found ";

		//establishConnection();

		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE id='" + groupId + "'";

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		SmsGroup smsGroup = null;

		if (resultSet.next()) {
			smsGroup = populateSmsGroup(resultSet);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		return smsGroup;
	}

	private SmsGroup populateSmsGroup(ResultSet resultSet) throws SQLException {
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(resultSet.getInt("customer_id"));
		smsGroup.setGroupMessage(resultSet.getString("group_message"));
		smsGroup.setSendTime(resultSet.getDate("sendtime"));
		smsGroup.setSmsGroupId(resultSet.getInt("id"));
		smsGroup.setSmsGroupName(resultSet.getString("group_name"));

		return smsGroup;
	}
}
