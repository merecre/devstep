package lv.itsms.web.menu;

public class MenuContext {

	int mainMenuId;
	
	String elementClass;
	
	String pageStr;
	
	String pageIndex;
	
	String elementSubIndex;
	
	String title;

	String href;
	
	String subElementSubIndex;
	
	public String getElementClass() {
		return elementClass;
	}

	public void setElementClass(String elementClass) {
		this.elementClass = elementClass;
	}

	public String getPageStr() {
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getElementSubIndex() {
		return elementSubIndex;
	}

	public void setElementSubIndex(String elementSubIndex) {
		this.elementSubIndex = elementSubIndex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getMainMenuId() {
		return mainMenuId;
	}

	public void setMainMenuId(int mainMenuId) {
		this.mainMenuId = mainMenuId;
	}

	public String getSubElementSubIndex() {
		return subElementSubIndex;
	}

	public void setSubElementSubIndex(String subElementSubIndex) {
		this.subElementSubIndex = subElementSubIndex;
	}

	@Override
	public String toString() {
		
		return mainMenuId + ":" + elementClass + ":" + pageStr +
				":" + pageIndex + ":" + elementSubIndex + ":" + 
				":" + title + ":" + subElementSubIndex;
	}
	
	
}
