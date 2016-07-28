package lv.itsms.web.page.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import lv.itsms.web.service.jdbc.JDBCDataAccessObject;


public class OfferInfoDAO extends JDBCDataAccessObject {

	final static String DB_TABLE = "sub_product";

	OfferInfo offerInfo;

	public OfferInfoDAO(DataSource dataSource) {
		super(dataSource);
	}

	public OfferInfo selectByLanguageAndOfferId(String language, String offerId) throws SQLException {

		if (connection.isClosed()) {
			establishConnection();
		}

		PreparedStatement statement = connection.prepareStatement(
				"SELECT text, title "
						+ "FROM "+ DB_TABLE  + " "
						+ "WHERE " + "language='" + language + "' "
						+ "AND sub_id='" + offerId + "'");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) { 
			populateData(resultSet);
		}

		resultSet.close();
		statement.close();
		connection.close();

		return offerInfo;
	}

	private void populateData (ResultSet resultSet) throws SQLException {
		offerInfo = new OfferInfo();
		offerInfo.setText(resultSet.getString("text"));
		offerInfo.setTitle(resultSet.getString("title"));
	}
}
