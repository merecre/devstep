package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.LoginInfoDAO;

public class JDBCLoginInfoDAO implements LoginInfoDAO {

	private final static String DB_TABLE = "web_content.content_login";

	private Connection connection;

	private DBDAOFactory factory;

	LoginInfo loginInfo;

	public JDBCLoginInfoDAO(DBDAOFactory factory) {
		this.factory = factory;
	}

	public LoginInfo getLoginInfoByLanguage(String language) throws SQLException {

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(
				"SELECT * "
						+ "FROM " + DB_TABLE 
						+ " WHERE "	+ "lng='" + language + "'");
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			populateData(resultSet);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);


		return loginInfo;
	}

	private void populateData (ResultSet resultSet) throws SQLException {
		loginInfo = new LoginInfo();
		loginInfo.setErrNotReg(resultSet.getString("err_not_reg"));
		loginInfo.setTxtHeader(resultSet.getString("txt_header"));
		loginInfo.setTxtInvalidPassword(resultSet.getString("txt_invalid_password"));
		loginInfo.setTxtLogOut(resultSet.getString("txt_log_out"));
		loginInfo.setTxtPassword(resultSet.getString("txt_password"));
		loginInfo.setTxtRegisterHere(resultSet.getString("txt_register_here"));
		loginInfo.setTxtRepeat(resultSet.getString("txt_try_again"));
		loginInfo.setTxtUsername(resultSet.getString("txt_username"));
		loginInfo.setTxtWelcome(resultSet.getString("txt_welcome"));

	}
}
