package lv.itsms.web.submenu;

import java.util.List;

import lv.itsms.web.mainmenu.Menu;

public class SubMenu {

	int subMenuId;
	int mainMenuId;
	int subTitleId;
	String subPageStr;
	String subClassActive;
	String subClassDefault;
	String subPageSubIndex;
	String title;
	
	private List<Menu> menues;
	
	public int getSubMenuId() {
		return subMenuId;
	}
	public void setSubMenuId(int subMenuId) {
		this.subMenuId = subMenuId;
	}
	public int getSubTitleId() {
		return subTitleId;
	}
	
	public int getMainMenuId() {
		return mainMenuId;
	}
	public void setMainMenuId(int mainMenuId) {
		this.mainMenuId = mainMenuId;
	}
	public void setSubTitleId(int subTitleId) {
		this.subTitleId = subTitleId;
	}
	public String getSubPageStr() {
		return subPageStr;
	}
	public void setSubPageStr(String subPageStr) {
		this.subPageStr = subPageStr;
	}
	public String getSubClassActive() {
		return subClassActive;
	}
	public void setSubClassActive(String subClassActive) {
		this.subClassActive = subClassActive;
	}
	public String getSubClassDefault() {
		return subClassDefault;
	}
	public void setSubClassDefault(String subClassDefault) {
		this.subClassDefault = subClassDefault;
	}
	public String getSubPageSubIndex() {
		return subPageSubIndex;
	}
	public void setSubPageSubIndex(String subPageSubIndex) {
		this.subPageSubIndex = subPageSubIndex;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Menu> getMenues() {
		return menues;
	}
	public void setMenues(List<Menu> menues) {
		this.menues = menues;
	}
	
	@Override
	public int hashCode() {
		
		return 31 * subMenuId + subTitleId;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		
		if ((obj == null ) || (obj.getClass() != this.getClass())) {
            return false;
        }
		
		SubMenu menu = (SubMenu) obj;
		return this.subMenuId == menu.getSubMenuId();
	}
}
