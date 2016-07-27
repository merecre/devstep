package lv.itsms.web.menu;

public class Title {

	int titleID;
	
	int menuID;
	
	String language;
	
	String title;
	
	public int getTitleID() {
		return titleID;
	}
	
	public void setTitleID(int titleID) {
		this.titleID = titleID;
	}
	
	public int getMenuID() {
		return menuID;
	}
	
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public int hashCode() {

		return 31 * this.titleID;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		
        if ((obj == null ) || (obj.getClass() != this.getClass())) {
            return false;
        }
		
        Title title = (Title)obj;
		return this.titleID == title.titleID;
	}
	
	@Override
	public String toString() {
		return this.titleID + "";
	}
	
	
}
