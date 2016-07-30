package lv.itsms.web.page.smspanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.page.smspanel.request.UserRequestCommandLookupFactory;
import lv.itsms.web.page.smspanel.request.DeleteSmsGroupRecordCommandLookup;
import lv.itsms.web.page.smspanel.request.OpenNewSmsGroupRecordCommandLookup;
import lv.itsms.web.page.smspanel.request.SaveSmsGroupRecordCommandLookup;
import lv.itsms.web.page.smspanel.request.UserRequestCommandLookup;
import lv.itsms.web.page.smspanel.request.ViewSmsGroupNameCommandLookup;
import lv.itsms.web.page.smspanel.request.ViewSmsGroupRecordCommandLookup;
import lv.itsms.web.request.parameter.CustomerMenuRequestParameter;
import lv.itsms.web.request.parameter.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.SmsPhoneRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.ViewSmsGroupRecRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

/**
 * Parses URL and executes User SMS panel page requests
 */

public class SmsPanelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static String smsPanelPageURL = "&" +
			CustomerMenuRequestParameter.MENU_URL_PARAMETER 
			+ "=" +
			CustomerMenuRequestParameter.SMS_REPORT_GROUP_LIST_URL_VALUE;

	final static String errorPageURL = "/WEB-INF/smspanelerror.jsp";

	Repository repository;

	CustomerPanelCommandFactory userRequestCommandFactory;

	Session session;

	CustomerPanelPageManager pageManager;

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

		Map<String, UserPageRequestParameter> urlParameters = prepareURLParameters();

		UserRequestCommandLookupFactory userRequestCommandLookupFactory = new UserRequestCommandLookupFactory(urlParameters);		
		Map<CommandType, UserRequestCommandLookup> userRequestCommandLookups = prepareUserRequestCommandLookups(userRequestCommandLookupFactory);

		userRequestCommandFactory = new CustomerPanelCommandFactory (repository, urlParameters);	

		pageManager = new CustomerPanelPageManager(userRequestCommandFactory, userRequestCommandLookups);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session.setRequest(request);
		session.setSession(request.getSession());

		userRequestCommandFactory.setSession(session);
		userRequestCommandFactory.setRequest(request);

		try {		
			List<PageRequestCommand> commandsToBeExecuted = pageManager.selectUserRequestCommand(request);
			executeUserRequestCommand(commandsToBeExecuted);
			redirectToPage(request, response);
		} catch (Exception exception) {
			exception.printStackTrace();
			forwardToPage(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

	private Map<String, UserPageRequestParameter>  prepareURLParameters() {

		List<UserPageRequestParameter> urlParameterList = new ArrayList<>();		

		urlParameterList.add(new CustomerMenuRequestParameter());
		urlParameterList.add(new DeleteSmsGroupPostRequestParameter());
		urlParameterList.add(new SmsGroupIdPostRequestParameter());
		urlParameterList.add(new SaveSmsNewGroupRequestParameter());
		urlParameterList.add(new SmsPhoneRequestParameter());
		urlParameterList.add(new SmsGroupIdGetRequestParameter());
		urlParameterList.add(new OpenNewSmsGroupRecRequestParameter());
		urlParameterList.add(new ViewSmsGroupRecRequestParameter());

		Map<String, UserPageRequestParameter>  urlParameters = new HashMap<>();
		for (UserPageRequestParameter userRequest : urlParameterList) {
			urlParameters.put(userRequest.getParameterKey(), userRequest);
		}	

		return urlParameters;
	}

	private Map<CommandType, UserRequestCommandLookup> prepareUserRequestCommandLookups(UserRequestCommandLookupFactory commandLookupFactory) {

		Map<CommandType, UserRequestCommandLookup> commandLookups = new EnumMap<>(CommandType.class);	

		UserRequestCommandLookup commandLookup = commandLookupFactory.make(SaveSmsNewGroupRequestParameter.SAVE_COMMAND_URL_PARAMETER);
		commandLookups.put(CommandType.CMD_SAVE_SMS_GROUP_REC, commandLookup);		

		commandLookup = commandLookupFactory.make(DeleteSmsGroupPostRequestParameter.DELETE_COMMAND_URL_PARAMETER);
		commandLookups.put(CommandType.CMD_DELETE_SMS_GROUP_REC, commandLookup);

		commandLookup = commandLookupFactory.make(OpenNewSmsGroupRecRequestParameter.OPEN_NEW_SMS_GROUP_URL_PARAMETER);
		commandLookups.put(CommandType.CMD_OPEN_NEW_SMS_REC, commandLookup);

		commandLookup = commandLookupFactory.make(ViewSmsGroupRecRequestParameter.VIEW_SMS_GROUP_REV_URL);
		commandLookups.put(CommandType.CMD_LOAD_SMS_GROUP_REC, commandLookup);

		commandLookup = commandLookupFactory.make(CustomerMenuRequestParameter.MENU_URL_PARAMETER);
		commandLookups.put(CommandType.CMD_LOAD_SMS_GROUP_NAMES, commandLookup);

		return commandLookups;
	}

	private void  executeUserRequestCommand(List<PageRequestCommand> commandExecutionSequence) {
		for (PageRequestCommand command : commandExecutionSequence) {
			command.execute();
		}		
	}

	private void redirectToPage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
	}

	private void forwardToPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String forwardJSP = errorPageURL;

		try {
			ServletContext context = getServletContext();
			if (context != null) {
				RequestDispatcher dispatcher = context.getRequestDispatcher(forwardJSP);
				dispatcher.forward(request,response);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public CustomerPanelCommandFactory getCustomerPanelFactory() {
		return userRequestCommandFactory;
	}

	public void setCustomerPanelFactory(CustomerPanelCommandFactory customerPanelFactory) {
		this.userRequestCommandFactory = customerPanelFactory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public CustomerPanelPageManager getPageManager() {
		return pageManager;
	}

	public void setPageManager(CustomerPanelPageManager pageManager) {
		this.pageManager = pageManager;
	}


}
