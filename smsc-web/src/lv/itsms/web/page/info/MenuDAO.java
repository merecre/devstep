package lv.itsms.web.page.info;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lv.itsms.web.mainmenu.Menu;
import lv.itsms.web.service.jdbc.JDBCDataAccessObject;

public class MenuDAO extends JDBCDataAccessObject {

	public MenuDAO(DataSource dataSource) {
		super(dataSource);
	}

	public List<Menu> list() throws SQLException {

		if (connection.isClosed()) {
			establishConnection();
		}
		
		List<Menu> menues = new ArrayList<>();

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM menu");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Menu menu = new Menu();
			menu.setMenuId(resultSet.getInt("idmenu"));
			menu.setTitleId(resultSet.getInt("title_id"));
			menu.setPage(resultSet.getString("page"));
			menu.setPageIndex(resultSet.getString("page_index"));
			menu.setClassActive(resultSet.getString("class_active"));
			menu.setClassDefault(resultSet.getString("class_inactive"));
			menues.add(menu);
		}
		
		resultSet.close();
		statement.close();
		connection.close();

		return menues;
	}
}
