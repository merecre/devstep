package lv.itsms.web.page.info;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lv.itsms.web.custmenu.CustomerMenu;
import lv.itsms.web.service.jdbc.JDBCDataAccessObject;

public class CustomerMenuDAO extends JDBCDataAccessObject {

	public CustomerMenuDAO(DataSource dataSource) {
		super(dataSource);
	}
		
	public List<CustomerMenu> list() throws SQLException {

		if (connection.isClosed()) {
			establishConnection();
		}
		
		List<CustomerMenu> menues = new ArrayList<>();

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer_menu");
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			CustomerMenu menu = new CustomerMenu();
			menu.setMenuId(resultSet.getInt("idcustomer_menu"));
			menu.setTitleId(resultSet.getInt("title_id"));
			menu.setPage(resultSet.getString("page"));
			menu.setPageIndex(resultSet.getString("link_index"));
			menues.add(menu);
		}
		
		resultSet.close();
		statement.close();
		connection.close(); 

		return menues;
	}
}
