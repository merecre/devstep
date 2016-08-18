package lv.itsms.web.page.info;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import lv.itsms.web.menu.mainmenu.Menu;
import lv.itsms.web.service.jdbc.JDBCDataAccessObject;

public class MenuDAO extends JDBCDataAccessObject {

	final static String DB_TABLE = "menu";

	public MenuDAO(DataSource dataSource) {
		super(dataSource);
	}

	public List<Menu> list() throws SQLException {

		if (connection.isClosed()) {
			establishConnection();
		}

		List<Menu> menues = new ArrayList<>();

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + DB_TABLE);
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
