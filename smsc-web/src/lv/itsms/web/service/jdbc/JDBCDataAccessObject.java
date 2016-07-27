package lv.itsms.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class JDBCDataAccessObject {

	public static final int NO_RECORDS = -1;
	
	protected DataSource dataSource;

	protected Connection connection;
	
	public JDBCDataAccessObject(Connection connection) {
		this.connection = connection;
	}
	
	public JDBCDataAccessObject(DataSource dataSource) {
		this.dataSource = dataSource;
		this.connection = establishConnection();
	}
	
	protected Connection establishConnection() {
		try {
			if (connection == null || connection.isClosed()) {
			    connection = dataSource.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
