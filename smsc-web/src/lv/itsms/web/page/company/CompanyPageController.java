package lv.itsms.web.page.company;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lv.itsms.web.page.info.CompanyInfo;
import lv.itsms.web.page.info.CompanyInfoDAO;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class WebController
 */
//@WebServlet("/WebController")
public class CompanyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource(name = "jdbc/ITSMSDBWeb") 
    private DataSource dataSource;
	
	CompanyInfoDAO companyInfoDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyPageController() {
        super();
    }

	@Override
	public void init() {
		companyInfoDAO = new CompanyInfoDAO(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session = new Session(request);
		String language = session.getSessionLanguage();
		
		CompanyInfo companyPageInfo = getCompanyInfoFromDB(language);
		request.setAttribute("companyinfo", companyPageInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private CompanyInfo getCompanyInfoFromDB(String language) {
		CompanyInfo companyPageInfo = null;
		try {
			companyPageInfo = companyInfoDAO.findByLanguage(language);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companyPageInfo;
	}
}
