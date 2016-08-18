package lv.itsms.web.page.info;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lv.itsms.web.menu.Title;
import lv.itsms.web.service.jdbc.JDBCDataAccessObject;

public class TitleDAO extends JDBCDataAccessObject {

	final static String DB_TABLE = "title";

	List<Title> titles;

	public TitleDAO(DataSource dataSource) {
		super(dataSource);
		this.titles = new ArrayList<Title>();
	}

	public List<Title> list() throws SQLException {

		if (connection.isClosed()) {
			establishConnection();
		}

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + DB_TABLE);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Title title = new Title();
			title.setTitleID(resultSet.getInt("idtitle"));
			title.setMenuID(resultSet.getInt("menu_id"));
			title.setLanguage(resultSet.getString("language"));
			title.setTitle(resultSet.getString("title"));
			titles.add(title);
		}

		resultSet.close();
		statement.close();
		connection.close();

		return titles;
	}

	String getTitleByIDandLanguage(int menuID, String language) {
		for (Title title : titles) {
			if (title.getLanguage().equals(language) && (title.getMenuID()==menuID)) {
				return title.getTitle();
			} 
		}
		return "";
	}
}
