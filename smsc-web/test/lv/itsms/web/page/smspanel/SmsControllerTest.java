package lv.itsms.web.page.smspanel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lv.itsms.web.command.UserRequestCommandManager;
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
	UserRequestCommandManager pageManager;
	Map<String, Object> attributes;

	@Before
	public void init() {

		httpSession = Mockito.mock(HttpSession.class);
		request = Mockito.mock(HttpServletRequest.class);       
		response = Mockito.mock(HttpServletResponse.class); 
		session = Mockito.mock(Session.class);
		repository = new Repository(DAOFactory.TEST_DAO);
		attributes = new HashMap<>();

		userRequestCommandFactory = new CustomerPanelCommandFactory (repository);
		
		pageManager = new UserRequestCommandManager(userRequestCommandFactory);
		Mockito.doAnswer(new Answer<Object>(){
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
				Object value = invocation.getArguments()[1];
				attributes.put(key, value);
				return null;
			}
		}).when(session).updateSessionAttribute(anyString(), any());
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

	@Test 
	public void saveNewSmsGroup() throws ServletException, IOException {

		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		String saveSmsGroupPostRequest = "save"; 
		String phoneNumbersToSave = "phone"; 

		when(request.getParameter(saveSmsGroupPostRequest)).thenReturn(saveSmsGroupPostRequest);

		when(request.getParameter("g_common_name")).thenReturn("SMS group name");
		when(request.getParameter("g_common_message")).thenReturn("Live is good");
		when(request.getParameter("grp_send_date")).thenReturn("2017-08-06");
		when(request.getParameter("grp_send_time_hour")).thenReturn("17");
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
	public void saveNewSmsGroupButMessageDescriptionNotSpecified() throws ServletException, IOException {

		//System.setOut(new PrintStream(outContent));
		//System.setErr(new PrintStream(errContent));

		String saveSmsGroupPostRequest = "save"; 
		String phoneNumbersToSave = "phone"; 

		when(request.getParameter(saveSmsGroupPostRequest)).thenReturn(saveSmsGroupPostRequest);

		when(request.getParameter("g_common_name")).thenReturn("");
		when(request.getParameter("g_common_message")).thenReturn("Live is good");
		when(request.getParameter("grp_send_date")).thenReturn("2017-08-06");
		when(request.getParameter("grp_send_time_hour")).thenReturn("17");
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

		String errorMessage =  (String) attributes.get("error");

		String errorTestResult = "Description is mandatory";
		assertEquals(errorTestResult, errorMessage);    
	}
	@Test 
	public void saveNewSmsGroupButPhoneNumbersNotSpecified () throws ServletException, IOException {

		String saveSmsGroupPostRequest = "save"; 
		String phoneNumbersToSave = "phone"; 

		when(request.getParameter(saveSmsGroupPostRequest)).thenReturn(saveSmsGroupPostRequest);

		when(request.getParameter("g_common_name")).thenReturn("SMS group name");
		when(request.getParameter("g_common_message")).thenReturn("Live is good");
		when(request.getParameter("grp_send_date")).thenReturn("2017-07-27");
		when(request.getParameter("grp_send_time_hour")).thenReturn("12");
		when(request.getParameter("grp_send_time_min")).thenReturn("00");

		String[] phoneNumbers = { "", ""};
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

		String errorMessage =  (String) attributes.get("error");

		String errorTestResult = "Phone number is mandatory";
		assertEquals(errorTestResult, errorMessage);
	}
	
	@Test 
	public void saveNewSmsGroupButDateIsNotSpecified () throws ServletException, IOException {

		String saveSmsGroupPostRequest = "save"; 
		String phoneNumbersToSave = "phone"; 

		when(request.getParameter(saveSmsGroupPostRequest)).thenReturn(saveSmsGroupPostRequest);

		when(request.getParameter("g_common_name")).thenReturn("SMS group name");
		when(request.getParameter("g_common_message")).thenReturn("Live is good");
		when(request.getParameter("grp_send_date")).thenReturn("");
		when(request.getParameter("grp_send_time_hour")).thenReturn("12");
		when(request.getParameter("grp_send_time_min")).thenReturn("00");

		String[] phoneNumbers = { "455334422"};
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

		String errorMessage =  (String) attributes.get("error");

		String errorTestResult = "Incorrect Date";
		assertEquals(errorTestResult, errorMessage);
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
	@After
	public void cleanUpStreams() {
		//System.setOut(null);
		// System.setErr(null);
	}
}
