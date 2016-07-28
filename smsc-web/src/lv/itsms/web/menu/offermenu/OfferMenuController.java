package lv.itsms.web.menu.offermenu;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.page.info.OfferInfo;
import lv.itsms.web.page.info.OfferInfoDAO;
import lv.itsms.web.service.jdbc.JDBCLoginInfoDAO;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class OfferPageController
 */
//@WebServlet("/OfferPageController")
public class OfferMenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	final static String ATTRIBUTE_OFFER = "offerpageinfo";
	
	@Resource(name = "jdbc/ITSMSDBWebInfo") 
    private DataSource dataSource;
	
	private OfferInfoDAO offerInfoDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OfferMenuController() {
        super();
    }

	@Override
	public void init() {
		offerInfoDAO = new OfferInfoDAO(dataSource);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = new Session(request);
		String language = session.getSessionLanguage();
		String pageOfferId = session.getSessionSubMenuId();
		
		OfferInfo offerPageInfo = getOfferInfoFromDB(language, pageOfferId);
		request.setAttribute(ATTRIBUTE_OFFER, offerPageInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private OfferInfo getOfferInfoFromDB(String language, String offerId) {
		OfferInfo offerPageInfo = null;
		try {
			offerPageInfo = offerInfoDAO.selectByLanguageAndOfferId(language, offerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return offerPageInfo;
	}
	
}
