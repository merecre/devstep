package lv.itsms.web.request.parameter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;


public class CustomerMenuRequestParameterTest {

	@Test
	public void userSelectCustomerSmsPanelPage() {

		String menuIdURL = "cusMenu";
		String customerSMSPanelMenuURL = "smsPanel";

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getParameter(menuIdURL)).thenReturn(customerSMSPanelMenuURL);

		CustomerMenuRequestParameter menuURLParameter = new CustomerMenuRequestParameter();
		menuURLParameter.update(request);
		String requestValue = menuURLParameter.getParameter();

		assertEquals(customerSMSPanelMenuURL, requestValue);	
	}
}
