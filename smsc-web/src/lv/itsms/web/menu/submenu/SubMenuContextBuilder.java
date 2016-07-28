package lv.itsms.web.menu.submenu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.menu.MenuContext;
import lv.itsms.web.menu.MenuContextBuilder;
import lv.itsms.web.menu.Title;
import lv.itsms.web.menu.mainmenu.Menu;
import lv.itsms.web.menu.submenu.SubMenu;
import lv.itsms.web.menu.submenu.SubMenuDAO;
import lv.itsms.web.page.info.TitleDAO;

public class SubMenuContextBuilder implements MenuContextBuilder {
	
	private List<SubMenu> subMenues;
	
	private List<Title> titles;
	
	public SubMenuContextBuilder(List<SubMenu> subMenues, List<Title> titles) {
		this.subMenues = subMenues;
		this.titles = titles;
	}
	
	@Override
	public List<MenuContext> buildMenuByLanguage(String menuId, String language) {
		
		setMenuTitleByLanguage(language);
		
		List<MenuContext> menuContext = buildMenuPageContext(menuId);
		
		return menuContext;
	}

	private void setMenuTitleByLanguage(String language) {
		for (SubMenu menu : subMenues) {
			for (Title title : titles) {
				if (menu.getSubTitleId() == title.getMenuID()) {
					if (title.getLanguage().equals(language)) {
						menu.setTitle(title.getTitle());
					}
				}
			}
		}
	}
	
	private List<MenuContext> buildMenuPageContext(String menuId) {
		List<MenuContext> subMenuContextContainer = new ArrayList<MenuContext>();
		for (SubMenu subMenu : subMenues) {			
			MenuContext menuContext = new MenuContext();
			menuContext.setMainMenuId(subMenu.getMainMenuId());
			menuContext.setPageStr(subMenu.getSubPageStr());
			menuContext.setSubElementSubIndex(subMenu.getSubPageSubIndex());
			menuContext.setTitle(subMenu.getTitle());
		
			subMenuContextContainer.add(menuContext);
		}
		return subMenuContextContainer;
	}
}
