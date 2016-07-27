package lv.itsms.web.mainmenu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.menu.MenuContext;
import lv.itsms.web.menu.MenuContextBuilder;
import lv.itsms.web.menu.Title;
import lv.itsms.web.submenu.SubMenu;
import lv.itsms.web.submenu.SubMenuDAO;

public class MainMenuContextBuilder implements MenuContextBuilder {
	
	private List<Menu> menues;
	
	private List<Title> titles;
	
	public MainMenuContextBuilder(List<Menu> menues, List<Title> titles) {
		this.menues = menues;
		this.titles = titles;
	}
	
	@Override
	public List<MenuContext> buildMenuByLanguage(String menuId, String language) {		
		setMenuTitleByLanguage(language);
		
		List<MenuContext> menuContext = buildMenuPageContext(menuId);
		
		return menuContext;
	}
	
	
	private void setMenuTitleByLanguage(String language) {
		//System.out.println("menues"+menues);
		//System.out.println("titles"+titles);
		for (Menu menu : menues) {
			for (Title title : titles) {
				//System.out.println("menu:"+menu.getTitleId() + ":" + title.getTitleID()+":"+language+ ":"+title.getLanguage());
				if (menu.getMenuId() == title.getMenuID()) {
					if (title.getLanguage().equals(language)) {
						menu.setTitle(title.getTitle());
					}
				}
			}
		}
	}
	
	private List<MenuContext> buildMenuPageContext(String menuId) {
		List<MenuContext> menuContextContainer = new ArrayList<MenuContext>();
		
		
		for (Menu menu : menues) {			
			MenuContext menuContext = new MenuContext();
			
			menuContext.setMainMenuId(menu.getMenuId());
			menuContext.setPageStr(menu.getPage());
			menuContext.setPageIndex(menu.getPageIndex());
			menuContext.setElementSubIndex("");
			menuContext.setTitle(menu.getTitle());
		    if (menuId.equals(menu.getPageIndex())) {
		    	menuContext.setElementClass(menu.getClassActive());
		    } else {
		    	menuContext.setElementClass(menu.getClassDefault());
		    }	
		    //System.out.println("MenuContext:"+ menuContext);
		    menuContextContainer.add(menuContext);
		    //System.out.println("MenuContext size:"+ menuContextContainer.size());
		}
		
		return menuContextContainer;
	}
}
