package lv.itsms.web.page.smspanel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lv.itsms.web.page.smspanel.SmsPanelController;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.parameter.menu.CustomerMenuRequestParameter;
import lv.itsms.web.request.parameter.smspanel.DeleteSmsGroupPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.OpenNewSmsGroupRecRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SaveSmsNewGroupRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdGetRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsGroupIdPostRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhoneRequestParameter;
import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmsControllerTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Mock private HttpSession httpSession;
	HttpServletRequest request;       
	HttpServletResponse response; 
	Session session;
	Repository repository;
	CustomerPanelCommandFactory userRequestCommandFactory;
	CustomerPanelPageManager pageManager;
	//Map<String, UserPageRequestParameter> urlParameters;
	Map<String, Object> attributes;

	@Before
	public void init() {

		//System.setOut(new PrintStream(outContent));
		//System.setErr(new PrintStream(errContent));


		httpSession = Mockito.mock(HttpSession.class);
		request = Mockito.mock(HttpServletRequest.class);       
		response = Mockito.mock(HttpServletResponse.class); 
		session = Mockito.mock(Session.class);
		repository = new Repository(DAOFactory.TEST_DAO);
		attributes = new HashMap<>();

		//urlParameters = prepareURLParameters();
		userRequestCommandFactory = new CustomerPanelCommandFactory (repository);

		//UserRequestCommandLookupFactory userRequestCommandLookupFactory = new UserRequestCommandLookupFactory(urlParameters);		
		//Map<CommandType, UserRequestCommandLookup> userRequestCommandLookups = prepareUserRequestCommandLookups(userRequestCommandLookupFactory);
		
		pageManager = new CustomerPanelPageManager(userRequestCommandFactory);
		Mockito.doAnswer(new Answer<Object>(){
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
				Object value = invocation.getArguments()[1];
				attributes.put(key, value);
				return null;
			}
		}).when(session).updateSessionAttribute(anyString(), any());
		/*
        Mockito.doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
            	 String key = (String) invocation.getArguments()[0];
                 Object value = attributes.get(key);
                 //System.out.println("get attribute value for key="+key+" : "+value);
                 return value;
            }
        }).when(httpSession).getAttribute(anyString());
		 */
	}

	@Test
	public void loadToWebPageSmsGroupList() throws Exception {

		String menuIdURL = "cusMenu";
		String customerSMSPanelMenuURL = "smsPanel";

		when(request.getParameter(menuIdURL)).thenReturn(customerSMSPanelMenuURL);

		String validCustomerId = "1";
		when(session.getSessionCustomerId()).thenReturn(validCustomerId);
		when(httpSession.getAttribute("customerid")).thenReturn(validCustomerId);       

		SmsPanelController smsServlet = new SmsPanelController();

		smsServlet.setSession(session);
		smsServlet.setRepository(repository);
		smsServlet.setCustomerPanelFactory(userRequestCommandFactory);
		smsServlet.setPageManager(pageManager);
		smsServlet.doPost(request, response);       

		List<SmsGroup> smsGroups = (List<SmsGroup>) attributes.get("smsgroups");

		SmsGroup smsGroup = smsGroups.get(0);
		assertEquals(1, smsGroup.getCustomerId());
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void throwExceptionIfSmsGroupNotFound() throws Exception {

		String menuIdURL = "cusMenu";
		String customerSMSPanelMenuURL = "smsPanel";

		when(request.getParameter(menuIdURL)).thenReturn(customerSMSPanelMenuURL);

		String invalidCustomerId = "2";
		when(session.getSessionCustomerId()).thenReturn(invalidCustomerId);
		when(httpSession.getAttribute("customerid")).thenReturn(invalidCustomerId);       

		SmsPanelController smsServlet = new SmsPanelController();

		smsServlet.setSession(session);
		smsServlet.setRepository(repository);
		smsServlet.setCustomerPanelFactory(userRequestCommandFactory);
		smsServlet.setPageManager(pageManager);

		exception.expect(Exception.class);
		exception.expectMessage("Group not found2");
		smsServlet.doPost(request, response);
	}

	@Test 
	public void saveNewSmsGroup () throws ServletException, IOException {

		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		String saveSmsGroupPostRequest = "save"; 
		String phoneNumbersToSave = "phone"; 

		when(request.getParameter(saveSmsGroupPostRequest)).thenReturn(saveSmsGroupPostRequest);

		when(request.getParameter("g_common_name")).thenReturn("SMS group name");
		when(request.getParameter("g_common_message")).thenReturn("Live is good");
		when(request.getParameter("grp_send_date")).thenReturn("2016-07-27");
		when(request.getParameter("grp_send_time_hour")).thenReturn("12");
		when(request.getParameter("grp_send_time_hour")).thenReturn("12");
		when(request.getParameter("grp_send_time_min")).thenReturn("00");

		String[] phoneNumbers = { "371777777", "37177778"};
		when(request.getParameterValues(phoneNumbersToSave)).thenReturn(phoneNumbers);

		String validCustomerId = "1";
		when(session.getSessionCustomerId()).thenReturn(validCustomerId);
		when(request.getSession(false)).thenReturn(httpSession);
		when(httpSession.getAttribute("customerid")).thenReturn(validCustomerId); 

		SmsPanelController smsServlet = new SmsPanelController();

		smsServlet.setSession(session);
		smsServlet.setRepository(repository);
		smsServlet.setCustomerPanelFactory(userRequestCommandFactory);
		smsServlet.setPageManager(pageManager);
		smsServlet.doPost(request, response);  

		assertEquals("SmsGroup saved: 20Insert 371777777Insert 37177778", outContent.toString());

		System.setOut(System.out);
		System.setErr(System.err);     
	}

	@Test
	public void deleteSmsGroup () throws ServletException, IOException {

		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		String deleteSmsGroupPostRequest = "delete"; 
		String groupIdToDelete = "group_name"; 

		when(request.getParameter(deleteSmsGroupPostRequest)).thenReturn(deleteSmsGroupPostRequest);

		String[] deleteGroupIds = {"20"};
		when(request.getParameterValues(groupIdToDelete)).thenReturn(deleteGroupIds);

		String validCustomerId = "1";
		when(session.getSessionCustomerId()).thenReturn(validCustomerId);
		when(request.getSession(false)).thenReturn(httpSession);
		when(httpSession.getAttribute("customerid")).thenReturn(validCustomerId); 

		SmsPanelController smsServlet = new SmsPanelController();

		smsServlet.setSession(session);
		smsServlet.setRepository(repository);
		smsServlet.setCustomerPanelFactory(userRequestCommandFactory);
		smsServlet.setPageManager(pageManager);
		smsServlet.doPost(request, response);  

		assertEquals("SmsGroup deleted by Id:20Phone Group deleted 20", outContent.toString());

		System.setOut(System.out);
		System.setErr(System.err);
	}
/*
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
*/

	@After
	public void cleanUpStreams() {
		//System.setOut(null);
		// System.setErr(null);
	}
}
