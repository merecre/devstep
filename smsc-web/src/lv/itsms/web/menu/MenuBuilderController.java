package lv.itsms.web.menu;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import lv.itsms.web.menu.custmenu.CustomerMenu;
import lv.itsms.web.menu.custmenu.CustomerMenuContextBuilder;
import lv.itsms.web.menu.mainmenu.MainMenuContextBuilder;
import lv.itsms.web.menu.mainmenu.Menu;
import lv.itsms.web.menu.submenu.SubMenu;
import lv.itsms.web.menu.submenu.SubMenuContextBuilder;
import lv.itsms.web.menu.submenu.SubMenuDAO;
import lv.itsms.web.page.info.CustomerMenuDAO;
import lv.itsms.web.page.info.MenuDAO;
import lv.itsms.web.page.info.TitleDAO;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.menu.CustomerMenuRequestParameter;
import lv.itsms.web.request.parameter.menu.LanguageRequestParameter;
import lv.itsms.web.request.parameter.menu.MainMenuRequestParameter;
import lv.itsms.web.request.parameter.menu.SubMenuRequestParameter;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class MenuServlet
 */
public class MenuBuilderController extends HttpServlet {

	final static String ATTRIBUTE_MENUS = "menues";
	final static String ATTRIBUTE_SUB_MENUS = "submenues";
	final static String ATTRIBUTE_CUSTOMER_MENUS = "customermenues";

	@Resource(name = "jdbc/ITSMSDBMenu") 
	private DataSource dataSource;

	private MenuDAO menuDAO;

	private SubMenuDAO subMenuDAO;

	private CustomerMenuDAO customerMenuDAO;

	private TitleDAO titleDAO;

	private MenuContextBuilder menuBuilder;

	private List<Menu> menues;

	private List<SubMenu> subMenues;

	private List<CustomerMenu> customerMenues;

	private List<Title> titles;

	Session session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MenuBuilderController() {
		super();
	}

	@Override
	public void init() {
		menuDAO = new MenuDAO(dataSource);
		subMenuDAO = new SubMenuDAO(dataSource);
		titleDAO =  new TitleDAO(dataSource);
		customerMenuDAO = new CustomerMenuDAO(dataSource);

		menues = getMenuFromDB();
		titles = getTitleFromDB();
		subMenues = getSubMenuFromDB();
		customerMenues = getCustomerMenuFromDB();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session = new Session(request);	
		updateSessionParameters();

		prepareDataToBuildPageMenu(request);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void updateSessionParameters() {

		UserPageRequestParameter mainMenuUserRequest = new MainMenuRequestParameter();
		session.updateSessionMenuId(mainMenuUserRequest);

		UserPageRequestParameter subMenuUserRequest = new SubMenuRequestParameter();
		session.updateSessionSubMenuId(subMenuUserRequest);

		UserPageRequestParameter languageUserRequest = new LanguageRequestParameter();
		session.updateSessionLanguage(languageUserRequest);

		if (session.isUserLoggedIn()) {
			UserPageRequestParameter customerMenuUserRequest = new CustomerMenuRequestParameter();
			session.updateSessionCustomerMenuId(customerMenuUserRequest);			 
		}
	}

	private void prepareDataToBuildPageMenu (HttpServletRequest request) {
		String language = session.getSessionLanguage(); 

		menuBuilder = new MainMenuContextBuilder(menues, titles);
		String menuId = session.getSessionMenuId();
		List<MenuContext> menues = menuBuilder.buildMenuByLanguage(menuId, language);

		request.setAttribute(ATTRIBUTE_MENUS, menues);

		menuBuilder = new SubMenuContextBuilder(subMenues, titles);
		List<MenuContext> subMenues = menuBuilder.buildMenuByLanguage(menuId, language);
		request.setAttribute(ATTRIBUTE_SUB_MENUS, subMenues);

		if (session.isUserLoggedIn()) {
			menuBuilder = new CustomerMenuContextBuilder(customerMenues, titles);
			String customerMenuId = session.getSessionCustomerMenuId();
			List<MenuContext> customerMenues = menuBuilder.buildMenuByLanguage(customerMenuId, language);
			request.setAttribute(ATTRIBUTE_CUSTOMER_MENUS , customerMenues);
		}
	}

	private List<Menu> getMenuFromDB() {
		List<Menu> menuesDB = null;

		try {
			menuesDB = menuDAO.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menuesDB;
	}

	private List<SubMenu> getSubMenuFromDB() {
		List<SubMenu> subMenuesDB = null;

		try {
			subMenuesDB = subMenuDAO.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subMenuesDB;
	}

	private List<CustomerMenu> getCustomerMenuFromDB() {
		List<CustomerMenu> customerMenuesDB = null;

		try {
			customerMenuesDB = customerMenuDAO.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerMenuesDB;
	}

	private List<Title> getTitleFromDB() {
		List<Title> titleDB = null;
		try {
			titleDB = titleDAO.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return titleDB;
	} 
}
