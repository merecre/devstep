package lv.itsms.web.page.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import lv.itsms.web.service.jdbc.JDBCDataAccessObject;

public class CompanyInfoDAO extends JDBCDataAccessObject {

	private final String DB_TABLE = "test_schema.company";

	public CompanyInfoDAO(DataSource dataSource) {
		super(dataSource);
	}

	public List<CompanyInfo> list() throws SQLException {	
		if (connection.isClosed()) {
			establishConnection();
		}

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + DB_TABLE);
		ResultSet resultSet = statement.executeQuery();

		List<CompanyInfo> companies = new ArrayList<CompanyInfo>();
		while (resultSet.next()) {
			companies.add(populateData(resultSet));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return companies;
	}

	public CompanyInfo findByLanguage(String language) throws SQLException {

		final String ERROR_MESSAGE = "Company info selection failed.";

		if (connection.isClosed()) {
			establishConnection();
		}

		CompanyInfo companyInfo = null;
		try (
				Connection connection = dataSource.getConnection();			
				PreparedStatement statement = connection.prepareStatement(
						"SELECT about, title "
								+ "FROM "+ DB_TABLE +" "
								+ "WHERE "	+ "company_lng='" + language + "'");
				ResultSet resultSet = statement.executeQuery();
				) {

			if (resultSet.next()) {
				companyInfo = populateData(resultSet);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} 

		if (companyInfo == null) {
			throw new RuntimeException(ERROR_MESSAGE);
		}

		return companyInfo;
	}

	private CompanyInfo populateData (ResultSet resultSet) throws SQLException {
		CompanyInfo company = new CompanyInfo();
		company.setTitle(resultSet.getString("title"));
		company.setAbout(resultSet.getString("about"));
		//company.setLanguage(resultSet.getString("company_lng"));
		return company;
	}
}
