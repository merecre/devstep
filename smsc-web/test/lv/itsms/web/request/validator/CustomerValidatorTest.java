package lv.itsms.web.request.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import lv.itsms.web.request.validator.customer.CustomerRegistrationFieldsValidator;
import lv.itsms.web.service.DAOFactory;
import lv.itsms.web.service.Repository;
import transfer.domain.Customer;
import transfer.validator.Rule;
import transfer.validator.UserRequestValidator;

public class CustomerValidatorTest {

	Repository repository;
	Customer customer;

	@Before
	public void init() {
		repository = new Repository(DAOFactory.TEST_DAO);
		customer = new Customer();
		customer.setName("Name");
		customer.setSurname("Surname");
		customer.setId(1);
		customer.setUserLogin("Test");
		customer.setEmail("email@email");
	}

	@Test(expected=RuntimeException.class)
	public void testUserInputtedNameIsEmpty () {
		String customerName = "";
		customer.setName(customerName);
		UserRequestValidator customerValidator = new CustomerRegistrationFieldsValidator(repository);
		customerValidator.prepareRules();
		boolean result = customerValidator.validate(customer);
		assertFalse(result);
	}

	@Test(expected=RuntimeException.class)
	public void testUserInputtedNameIsNull () {

		Customer customer = new Customer();

		String customerName = null;
		customer.setName(customerName);


		UserRequestValidator customerValidator = new CustomerRegistrationFieldsValidator(repository);
		customerValidator.prepareRules();

		boolean result = customerValidator.validate(customer);
		assertFalse(result);
	}

	@Test
	public void testUserInputtedNameIsValid () {

		Customer customer = new Customer();

		String customerName = "Name";
		customer.setName(customerName);
		UserRequestValidator customerValidator = new CustomerRegistrationFieldsValidator(repository);
		customerValidator.prepareRules();

		boolean result = customerValidator.validate(customer);
		assertTrue(result);
	}
}
