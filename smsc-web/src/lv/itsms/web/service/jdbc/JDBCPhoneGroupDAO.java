package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.PhoneGroupDAO;
import transfer.domain.PhoneGroup;

public class JDBCPhoneGroupDAO implements PhoneGroupDAO {

	private final static String DB_TABLE = "phone_group";

	private Connection connection;

	private DBDAOFactory factory;

	public JDBCPhoneGroupDAO(DBDAOFactory factory) {
		this.factory = factory;
	}

	public boolean save (PhoneGroup phoneGroup) throws SQLException {
		return insert(phoneGroup);
	}

	public boolean insert(PhoneGroup phoneGroup) throws SQLException {
		final String ERROR_INSERT = "Creating PhoneGroup failed, no rows affected.";

		connection = factory.createConnection();
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO phone_group "
						+ "(group_id, phonenumber)"
						+ " VALUES (" 
						+ "'"+ phoneGroup.getGroupId() + "',"
						+ "'"+ phoneGroup.getPhonenumber() + "'"
						+ ") "
						+ " ON DUPLICATE KEY UPDATE " 
						+ " group_id = '"+phoneGroup.getGroupId() + "',"
						+ " phonenumber = '"+phoneGroup.getPhonenumber() + "'"          		
				);

		int updatedRows = statement.executeUpdate();

		statement.close();
		factory.closeConnection(connection);

		return updatedRows != 0;
	}

	public List<PhoneGroup> getPhonesByGroupId(long groupId) throws SQLException {

		List<PhoneGroup> phoneGroups = new LinkedList<>();

		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE group_id='" + groupId + "'"
				+ " ORDER BY id";

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			PhoneGroup phoneGroup = new PhoneGroup();
			phoneGroup.setGroupId(resultSet.getInt("group_id"));
			phoneGroup.setPhonenumber(resultSet.getString("phonenumber"));
			phoneGroups.add(phoneGroup);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		return phoneGroups;
	}

	public boolean deleteByGroupId(int groupId) throws SQLException {

		connection = factory.createConnection();
		PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM " + DB_TABLE + " "
						+ "WHERE "	+ "group_id='" + groupId + "'");
		System.out.println("Statement:"+ statement.toString());
		statement.executeUpdate();

		statement.close();
		factory.closeConnection(connection);

		return true;
	}
}
