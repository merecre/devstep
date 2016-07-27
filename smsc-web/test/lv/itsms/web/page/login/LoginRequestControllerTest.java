package lv.itsms.web.page.login;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.page.smspanel.SmsPanelController;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.SmsGroup;

public class LoginRequestControllerTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Mock private HttpSession httpSession;
	HttpServletRequest request;       
	HttpServletResponse response; 
	Session session;
	Repository repository;
	Map<String, UserPageRequest> urlParameters;
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
	public void userTryToLogInWithValidPassword() throws ServletException, IOException {
		when(request.getParameter("uname")).thenReturn("Test");
		when(request.getParameter("pass")).thenReturn("Test");
		when(session.getSessionLanguage()).thenReturn("en");

		LoginRequestController loginServlet = new LoginRequestController();

		loginServlet.setSession(session);
		loginServlet.setRepository(repository);      
		loginServlet.doPost(request, response); 

		String userName = (String) attributes.get("userid");
		long userId = (Long) attributes.get("customerid");

		assertEquals(1, userId);
		assertEquals("Test", userName);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void ShowErrorIfUserTryLogInWithIncorrectPassword() throws ServletException, IOException {
		when(request.getParameter("uname")).thenReturn("Test");
		when(request.getParameter("pass")).thenReturn("IncorrectPassword");

		LoginRequestController loginServlet = new LoginRequestController();

		loginServlet.setSession(session);
		loginServlet.setRepository(repository);

		loginServlet.doPost(request, response); 

		String errorMessage = (String) attributes.get("error");

		assertEquals("Customer not found.", errorMessage);
	}
}
