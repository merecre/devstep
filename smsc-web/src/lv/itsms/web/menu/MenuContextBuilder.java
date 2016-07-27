package lv.itsms.web.menu;

import java.util.List;

public interface MenuContextBuilder {
	 List<MenuContext> buildMenuByLanguage(String menuId, String language);
}
