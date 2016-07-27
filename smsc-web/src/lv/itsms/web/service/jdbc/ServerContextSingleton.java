package lv.itsms.web.service.jdbc;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServerContextSingleton {

	private static final String CONTEXT_ENV = "java:/comp/env/";
	private static final String DB_SOURCE = "jdbc/ITSMSDBWeb";
	
	private static ServerContextSingleton instance = new ServerContextSingleton();
	
	DataSource dataSource;

	public static ServerContextSingleton getInstance() {
		return instance; 
	}
	
	public DataSource getDataSource () {
	
		if (dataSource!=null) {
			return dataSource;
		}
		
		try {
			Context init = new InitialContext();
			Context env = (Context) init.lookup(CONTEXT_ENV);
			dataSource = (DataSource)env.lookup(DB_SOURCE);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	
}
