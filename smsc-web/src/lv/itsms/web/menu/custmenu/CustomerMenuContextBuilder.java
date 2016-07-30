package lv.itsms.web.menu.custmenu;

import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.menu.MenuContext;
import lv.itsms.web.menu.MenuContextBuilder;
import lv.itsms.web.menu.Title;

public class CustomerMenuContextBuilder implements MenuContextBuilder {

	private List<CustomerMenu> menues;

	private List<Title> titles;

	public CustomerMenuContextBuilder(List<CustomerMenu> menues, List<Title> titles) {
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
		for (CustomerMenu menu : menues) {
			for (Title title : titles) {;
				if (menu.getTitleId() == title.getMenuID()) {
					if (title.getLanguage().equals(language)) {
						menu.setTitle(title.getTitle());
					}
				}
			}
		}
	}

	private List<MenuContext> buildMenuPageContext(String menuId) {
		List<MenuContext> menuContextContainer = new ArrayList<MenuContext>();


		for (CustomerMenu menu : menues) {			
			MenuContext menuContext = new MenuContext();

			menuContext.setMainMenuId(menu.getMenuId());
			menuContext.setPageStr(menu.getPage());
			menuContext.setPageIndex(menu.getPageIndex());
			menuContext.setTitle(menu.getTitle());

			menuContextContainer.add(menuContext);
		}

		return menuContextContainer;
	}
}
