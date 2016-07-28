package lv.itsms.web.menu.custmenu;

import lv.itsms.web.menu.mainmenu.Menu;

public class CustomerMenu {

	private int menuId;

	private int titleId;

	private String page;

	private String pageIndex;

	private String title;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return 31 * menuId + titleId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null ) || (obj.getClass() != this.getClass())) {
			return false;
		}

		CustomerMenu menu = (CustomerMenu) obj;
		return this.menuId == menu.menuId;
	}


}
