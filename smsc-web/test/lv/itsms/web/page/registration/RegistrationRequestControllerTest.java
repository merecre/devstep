package lv.itsms.web.page.registration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;

public class RegistrationRequestControllerTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Mock private HttpSession httpSession;
	HttpServletRequest request;       
	HttpServletResponse response; 
	Session session;
	Repository repository;
	Map<String, UserPageRequestParameter> urlParameters;
	Map<String, Object> attributes;

	@Before
	public void init() {

		httpSession = Mockito.mock(HttpSession.class);
		request = Mockito.mock(HttpServletRequest.class);       
		response = Mockito.mock(HttpServletResponse.class); 
		session = Mockito.mock(Session.class);
		repository = new Repository(DAOFactory.TEST_DAO);
		attributes = new HashMap<>();

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
	public void userDoRegistrationWithCorrectFilledFormFields() throws ServletException, IOException {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		when(request.getParameter("fname")).thenReturn("FirstName");
		when(request.getParameter("lname")).thenReturn("LastName");
		when(request.getParameter("email")).thenReturn("Test@email.com");
		when(request.getParameter("uname")).thenReturn("Test");
		when(request.getParameter("pass")).thenReturn("Test");

		RegistrationRequestController registrationController = new RegistrationRequestController();

		registrationController.setSession(session);
		registrationController.setRepository(repository);
		registrationController.doPost(request, response);
		assertEquals("Customer added 10", outContent.toString());

		System.setOut(System.out);
		System.setErr(System.err);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void userDoRegistrationWithEmptyPassword() throws ServletException, IOException {
		when(request.getParameter("fname")).thenReturn("FirstName");
		when(request.getParameter("lname")).thenReturn("LastName");
		when(request.getParameter("email")).thenReturn("Test@email.com");
		when(request.getParameter("uname")).thenReturn("Test2");
		when(request.getParameter("pass")).thenReturn("");

		RegistrationRequestController registrationController = new RegistrationRequestController();

		registrationController.setSession(session);
		registrationController.setRepository(repository);
		registrationController.doPost(request, response);

		String errorMessage =  (String) attributes.get("error");
		assertEquals("Password is mandatory.", errorMessage);
	}

	@Test
	public void userDoRegistrationButLoginAlreadyUsed() throws ServletException, IOException {

		String usedLoginName = "TestLogin";
		when(request.getParameter("fname")).thenReturn("FirstName");
		when(request.getParameter("lname")).thenReturn("LastName");
		when(request.getParameter("email")).thenReturn("Test@email.com");
		when(request.getParameter("uname")).thenReturn(usedLoginName);
		when(request.getParameter("pass")).thenReturn("Test");

		RegistrationRequestController registrationController = new RegistrationRequestController();

		registrationController.setSession(session);
		registrationController.setRepository(repository);
		registrationController.doPost(request, response);

		String errorMessage =  (String) attributes.get("error");

		//System.out.println("Error message " + errorMessage);
		String errorTestResult = "Login name " + usedLoginName + " is in use";
		assertEquals(errorTestResult, errorMessage);
	}
	
	@Test
	public void userDoRegistrationWithIncorrectEmailFormat() throws ServletException, IOException {

		when(request.getParameter("fname")).thenReturn("FirstName");
		when(request.getParameter("lname")).thenReturn("LastName");
		when(request.getParameter("email")).thenReturn("Tesemail.com");
		when(request.getParameter("uname")).thenReturn("Test");
		when(request.getParameter("pass")).thenReturn("Test");

		RegistrationRequestController registrationController = new RegistrationRequestController();

		registrationController.setSession(session);
		registrationController.setRepository(repository);
		registrationController.doPost(request, response);

		String errorMessage =  (String) attributes.get("error");		
		String errorTestResult = "Email format is not valid";
		assertEquals(errorTestResult, errorMessage);
	}
	
	@Test
	public void userDoRegistrationWithLoginLengthExceedMax15() throws ServletException, IOException {

		when(request.getParameter("fname")).thenReturn("FirstName");
		when(request.getParameter("lname")).thenReturn("LastName");
		when(request.getParameter("email")).thenReturn("Test@email.com");
		when(request.getParameter("uname")).thenReturn("Testexceedleng16");
		when(request.getParameter("pass")).thenReturn("Test");

		RegistrationRequestController registrationController = new RegistrationRequestController();

		registrationController.setSession(session);
		registrationController.setRepository(repository);
		registrationController.doPost(request, response);

		String errorMessage =  (String) attributes.get("error");		
		String errorTestResult = "Login name maximum length exceeded";
		assertEquals(errorTestResult, errorMessage);
	}

	@After
	public void cleanUpStreams() {

	}

}
