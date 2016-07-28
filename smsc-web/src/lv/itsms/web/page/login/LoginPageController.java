package lv.itsms.web.page.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lv.itsms.web.page.info.CompanyInfo;
import lv.itsms.web.page.info.CompanyInfoDAO;
import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.LoginInfoDAO;
import lv.itsms.web.service.jdbc.JDBCLoginInfoDAO;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class LoginPageController
 */
//@WebServlet("/LoginPageController")
public class LoginPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static String ATTRIBUTE_LOGININFO = "loginpageinfo";

	private LoginInfoDAO loginInfoDAO;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginPageController() {
		super();
	}

	@Override
	public void init() {

		DAOFactory factoryDAO = DAOFactory.getDAOFactory(DAOFactory.DB_DAO);
		loginInfoDAO = factoryDAO.getLoginInfoDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = new Session(request);
		String language = session.getSessionLanguage();

		LoginInfo loginPageInfo = getLoginInfoFromDB(language);
		request.setAttribute(ATTRIBUTE_LOGININFO, loginPageInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private LoginInfo getLoginInfoFromDB(String language) {
		LoginInfo loginPageInfo = null;
		try {
			loginPageInfo = loginInfoDAO.getLoginInfoByLanguage(language);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginPageInfo;
	}
}
