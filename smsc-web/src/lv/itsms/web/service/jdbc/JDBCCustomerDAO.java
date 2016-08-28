package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lv.itsms.web.service.DBDAOFactory;
import transfer.domain.Customer;
import transfer.service.jpa.CustomerDAO;

public class JDBCCustomerDAO implements CustomerDAO {

	private final static String DB_TABLE = "test_schema.customer";

	private Connection connection;

	private DBDAOFactory factory;

	public JDBCCustomerDAO(DBDAOFactory factory) {
		this.factory = factory;
	}

	public Customer getCustomerByLoginAndPassword(String login, String password) throws SQLException, RuntimeException {

		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE login='" + login + "'"
				+ " AND password='" + password + "'";

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		Customer customer = null;

		if (resultSet.next()) {
			customer = populateData(resultSet);
		}

		resultSet.close();
		statement.close();
		factory.closeConnection(connection);

		return customer;
	}

	public void insert(Customer customer) throws SQLException {
		final String ERROR_INSERT = "Customer registration failed.";

		connection = factory.createConnection();

		String sql = "INSERT INTO " + DB_TABLE 
				+ " (name, surname, login, password, email)"
				+ " VALUES (" 
				+ "'"+ customer.getName() + "',"
				+ "'"+ customer.getSurname() + "',"
				+ "'"+ customer.getUserLogin() + "',"
				+ "'"+ customer.getPassword() + "',"
				+ "'"+ customer.getEmail() + "'"
				+ ")";       		
		System.out.println("insert "+ sql);		
		PreparedStatement statement = connection.prepareStatement(sql);				

		int updatedRows = statement.executeUpdate();

		statement.close();
		factory.closeConnection(connection);

		if (updatedRows == 0) {
			throw new SQLException(ERROR_INSERT);
		}
	}

	@Override
	public Customer getCustomerByLogin(String customerUserName) throws SQLException, RuntimeException {

		String sql = "SELECT * FROM " + DB_TABLE 
				+ " WHERE login='" + customerUserName + "'";

		connection = factory.createConnection();

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		Customer customer = null;

		if (resultSet.next()) {
			customer = populateData(resultSet);
		}
		resultSet.close();
		statement.close();
		factory.closeConnection(connection);
		return customer;
	}

	private Customer populateData(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer();
		customer.setApiKey(resultSet.getString("apikey"));
		customer.setId(resultSet.getLong("customerId"));
		customer.setName(resultSet.getString("name"));
		customer.setSurname(resultSet.getString("surname"));
		customer.setPassword(resultSet.getString("password"));
		customer.setUserLogin(resultSet.getString("login"));
		customer.setEmail(resultSet.getString("email"));
		customer.setStatus(resultSet.getString("status"));

		return customer;
	}

	@Override
	public boolean isCustomerByLogin(String customerUserName) throws Exception {
		Customer customer = getCustomerByLogin(customerUserName);

		if (customer==null) {
			return false;
		}

		return true;
	}

	@Override
	public Customer findCustomerByApiKey(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer createCustomer(String apiKey, String name, String surname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findCustomer(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCustomer(long id) {
		// TODO Auto-generated method stub
		
	}
}
