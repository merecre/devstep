package lv.itsms.web.request.parameter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

public class MainMenuRequestParameterTest {

	@Test
	public void userSelectMenuHomePage () {
		
		String mainMenuURLId = "id";
		String homePageURLId = "Home";
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getParameter(mainMenuURLId)).thenReturn(homePageURLId);
		
		MainMenuRequestParameter menuURLParameter = new MainMenuRequestParameter();
		menuURLParameter.update(request);
		String requestValue = menuURLParameter.getParameter();
		
		assertEquals(homePageURLId, requestValue);
	}
}
