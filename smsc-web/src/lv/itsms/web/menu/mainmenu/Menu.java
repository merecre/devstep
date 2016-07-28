package lv.itsms.web.menu.mainmenu;

public class Menu {

	int menuId;
	int titleId;
	String page;
	String pageIndex;
	String classActive;
	String classDefault;
	String title;

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
	public String getClassActive() {
		return classActive;
	}
	public void setClassActive(String classActive) {
		this.classActive = classActive;
	}
	public String getClassDefault() {
		return classDefault;
	}
	public void setClassDefault(String classDefault) {
		this.classDefault = classDefault;
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

		Menu menu = (Menu) obj;
		return this.menuId == menu.menuId;
	}
}
