package lv.itsms.web.request.validator;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import transfer.domain.Customer;

public class CustomerValidatorTest {

	@Test(expected=RuntimeException.class)
	public void testUserInputtedNameIsEmpty () {
			
		Customer customer = new Customer();
		
		String customerName = "";
		customer.setName(customerName);
		
		List<Rule> rules = new ArrayList<>();
		
		Rule customerRule = new CustomerNameNotEmpty(customerName);	
		rules.add(customerRule);
		
		UserRequestValidator customerValidator = new CustomerFieldFormValidator(rules, customer);
		
	    customerValidator.validate();
	}
	
	@Test(expected=RuntimeException.class)
	public void testUserInputtedNameIsNull () {
			
		Customer customer = new Customer();
		
		String customerName = null;
		customer.setName(customerName);
		
		List<Rule> rules = new ArrayList<>();
		
		Rule customerRule = new CustomerNameNotEmpty(customerName);	
		rules.add(customerRule);
		
		UserRequestValidator customerValidator = new CustomerFieldFormValidator(rules, customer);
		
	    customerValidator.validate();
	}
	
	@Test
	public void testUserInputtedNameIsValid () {
			
		Customer customer = new Customer();
		
		String customerName = "Name";
		customer.setName(customerName);
		
		List<Rule> rules = new ArrayList<>();
		
		Rule customerRule = new CustomerNameNotEmpty(customerName);	
		rules.add(customerRule);
		
		UserRequestValidator customerValidator = new CustomerFieldFormValidator(rules, customer);
		
	    boolean result = customerValidator.validate();
	    assertTrue(result);
	}
}
