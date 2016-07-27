package lv.itsms.web.submenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class SubMenuDAO {

	private DataSource dataSource;
	
	public SubMenuDAO(DataSource dataSource) {
		this.dataSource = dataSource;	
	} 
	
	public List<SubMenu> list() throws SQLException {
		
		List<SubMenu> subMenues = new ArrayList<>();
		
		try (
	            Connection connection = dataSource.getConnection();
	            PreparedStatement statement = connection.prepareStatement("SELECT * FROM submenu ORDER BY sort");
	            ResultSet resultSet = statement.executeQuery();
	        ) {
			while (resultSet.next()) {
				SubMenu menu = new SubMenu();
				menu.setSubMenuId(resultSet.getInt("idsubmenu"));
				menu.setMainMenuId(resultSet.getInt("idmainmenu"));
	    		menu.setSubTitleId(resultSet.getInt("description_id"));
	    		menu.setSubPageStr(resultSet.getString("page"));
	    		menu.setSubClassActive(resultSet.getString("class_active"));
	    		menu.setSubClassDefault(resultSet.getString("class_inactive"));
	    		menu.setSubPageSubIndex(resultSet.getString("page_sub_index"));
				subMenues.add(menu);
			}
			connection.close();
			statement.close();
		} 
		
		return subMenues;
	}
}
