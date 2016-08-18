package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lv.itsms.web.page.info.RegistrationInfo;
import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.RegistrationInfoDAO;

public class JDBCRegistrationInfoDAO implements RegistrationInfoDAO {

	private final static String DB_TABLE = "web_content.content_reg_form";

	private Connection connection;

	private DBDAOFactory factory;

	public JDBCRegistrationInfoDAO(DBDAOFactory factory) {
		this.factory = factory;
	}

	public RegistrationInfo getInfoByLanguage(String language) throws SQLException, RuntimeException {		

		RegistrationInfo registrationInfo = null;

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(
				"SELECT * "
						+ "FROM " + DB_TABLE 
						+ " WHERE "	+ "lng='" + language + "'");
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			registrationInfo = populateData(resultSet);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		return registrationInfo;
	}

	private RegistrationInfo populateData (ResultSet resultSet) throws SQLException {

		RegistrationInfo registrationInfo = new RegistrationInfo();
		registrationInfo.setTxtBtnReset(resultSet.getString("txt_btn_reset"));
		registrationInfo.setTxtBtnSubmit(resultSet.getString("txt_btn_submit"));
		registrationInfo.setTxtEmail(resultSet.getString("txt_email"));
		registrationInfo.setTxtFirstname(resultSet.getString("txt_firstname"));
		registrationInfo.setTxtHeader(resultSet.getString("txt_header"));
		registrationInfo.setTxtLastname(resultSet.getString("txt_lastname"));
		registrationInfo.setTxtLogin(resultSet.getString("txt_login"));
		registrationInfo.setTxtLoginDesr(resultSet.getString("txt_login_desr"));
		registrationInfo.setTxtLoginHref(resultSet.getString("txt_login_href"));
		registrationInfo.setTxtPassword(resultSet.getString("txt_password"));

		return registrationInfo;
	}
}
