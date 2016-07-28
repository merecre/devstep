package lv.itsms.web.page.smspanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.CustomerMenuRequestParameter;
import lv.itsms.web.request.parameter.CustomerMenuRequestParameterTest;
import lv.itsms.web.request.parameter.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.SmsPhoneRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.Repository;
import lv.itsms.web.service.jdbc.JDBCSmsGroupDAO;
import lv.itsms.web.session.Session;

/**
 * Servlet implementation class SmsController
 */

public class SmsPanelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Repository repository;

	Map<String, UserPageRequest> urlParameters;

	CustomerPanelCommandFactory customerPanelFactory;

	Session session;
	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public SmsPanelController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();

		session = new Session();

		repository = new Repository();

		List<UserPageRequest> urlParameterList = new ArrayList<>();		
		urlParameterList.add(new CustomerMenuRequestParameter());
		urlParameterList.add(new DeleteSmsGroupPostRequestParameter());
		urlParameterList.add(new SmsGroupIdPostRequestParameter());
		urlParameterList.add(new SaveSmsNewGroupRequestParameter());
		urlParameterList.add(new SmsPhoneRequestParameter());
		urlParameterList.add(new SmsGroupIdGetRequestParameter());

		urlParameters = new HashMap<>();

		for (UserPageRequest userRequest : urlParameterList) {
			urlParameters.put(userRequest.getParameterKey(), userRequest);
		}

		customerPanelFactory = new CustomerPanelCommandFactory (repository, urlParameters);	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session.setRequest(request);
		session.setSession(request.getSession());

		customerPanelFactory.setSession(session);
		customerPanelFactory.setRequest(request);

		PageRequestCommand requestCommand = customerPanelFactory.make();
		requestCommand.execute();	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);

		returnToBackPage(request, response);
	}

	private void  returnToBackPage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Map<String, UserPageRequest> getUrlParameters() {
		return urlParameters;
	}

	public void setUrlParameters(Map<String, UserPageRequest> urlParameters) {
		this.urlParameters = urlParameters;
	}

	public CustomerPanelCommandFactory getCustomerPanelFactory() {
		return customerPanelFactory;
	}

	public void setCustomerPanelFactory(CustomerPanelCommandFactory customerPanelFactory) {
		this.customerPanelFactory = customerPanelFactory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
