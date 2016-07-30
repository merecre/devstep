package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.page.info.ProfileInfo;
import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.ProfileInfoDAO;

public class JDBCProfileInfoDAO implements ProfileInfoDAO {

	private final static String DB_TABLE = "web_content.content_profile";

	private Connection connection;

	private DBDAOFactory factory;

	public JDBCProfileInfoDAO(DBDAOFactory factory) {
		this.factory = factory;
	}

	@Override
	public ProfileInfo getProfileInfoByLanguage(String language) throws Exception {

		connection = factory.createConnection();

		ProfileInfo profileInfo = null;
		PreparedStatement statement = connection.prepareStatement(
				"SELECT * "
						+ "FROM " + DB_TABLE 
						+ " WHERE "	+ "lng='" + language + "'");
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			profileInfo = populateData(resultSet);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		return profileInfo;
	}

	private ProfileInfo populateData (ResultSet resultSet) throws SQLException {
		ProfileInfo profileInfo = new ProfileInfo();
		profileInfo.setTxtHeader(resultSet.getString("txt_header"));
		profileInfo.setTxtName(resultSet.getString("txt_name"));
		profileInfo.setTxtSurname(resultSet.getString("txt_surname"));
		profileInfo.setTxtLogin(resultSet.getString("txt_login"));
		profileInfo.setTxtEmail(resultSet.getString("txt_email"));
		profileInfo.setLng(resultSet.getString("lng"));
		profileInfo.setTxtError(resultSet.getString("txt_error"));
		profileInfo.setTxtErrorReturn(resultSet.getString("txt_error_return"));

		return profileInfo;
	}
}
