package lv.itsms.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.page.info.RegistrationInfo;
import lv.itsms.web.service.jdbc.JDBCCustomerDAO;
import lv.itsms.web.service.jdbc.JDBCLoginInfoDAO;
import lv.itsms.web.service.jdbc.JDBCPhoneGroupDAO;
import lv.itsms.web.service.jdbc.JDBCRegistrationInfoDAO;
import lv.itsms.web.service.jdbc.JDBCSmsGroupDAO;
import transfer.domain.Customer;
import transfer.domain.PhoneGroup;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;

public class Repository {

	SmsGroupDAO smsGroupDAO;
	PhoneGroupDAO phoneGroupDAO;
	CustomerDAO customerDAO;
	LoginInfoDAO loginInfoDAO;
	RegistrationInfoDAO registrationInfoDAO;
	SmsDAO smsDAO;

	public Repository() {	
		this(DAOFactory.DB_DAO);
	}		

	public Repository(int DAOType) {

		DAOFactory factoryDAO = DAOFactory.getDAOFactory(DAOType);
		this.customerDAO = factoryDAO.getCustomerDAO();
		this.phoneGroupDAO = factoryDAO.getPhoneGroupDAO();		
		this.smsGroupDAO = factoryDAO.getSmsGroupDAO();	
		this.loginInfoDAO = factoryDAO.getLoginInfoDAO();	
		this.registrationInfoDAO = factoryDAO.getRegistrationInfoDAO();	
		this.smsDAO = factoryDAO.getSmsDAO();
	}	

	public void deleteSmsGroupByUserIdAndGroupName(int userId, String smsGroupName) {

		try {
			smsGroupDAO.deleteByUserIdAndGroupName(userId, smsGroupName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void deleteSmsGroupByGroupId(int groupId) {

		try {
			smsGroupDAO.deleteByGroupId(groupId);

			phoneGroupDAO.deleteByGroupId(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void updateSmsGroup(SmsGroup smsGroup, String[] phoneNumbers) {

		try {			
			saveSmsGroup(smsGroup);
			long smsGroupId = smsGroup.getSmsGroupId();
			savePhoneListOfGroup(smsGroupId, phoneNumbers);		

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public List<SmsGroup> getSmsGroupByUserId(int userId) {

		List<SmsGroup> smsGroups = null;
		try {
			smsGroups = smsGroupDAO.getGroupsByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smsGroups;
	}

	public SmsGroup getSmsGroupById(int groupId) {

		SmsGroup smsGroup = null;

		try {
			smsGroup = smsGroupDAO.getGroupById(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return smsGroup;
	}

	public List<PhoneGroup> getPhonesInGroupByGroupId (int groupId) {

		List<PhoneGroup> phoneGroups = null;

		try {
			phoneGroups = phoneGroupDAO.getPhonesByGroupId(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phoneGroups;
	}

	public Customer getCustomerByLoginAndPassword(String login, String password) {
		final String ERROR_MESSAGE = "Customer not found.";

		Customer customer = null;
		try {
			customer = customerDAO.getCustomerByLoginAndPassword(login, password);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (customer == null) {
			throw new RuntimeException(ERROR_MESSAGE);
		}

		return customer;
	}

	public LoginInfo getLoginInfoByLanguage(String language){	
		final String ERROR_MESSAGE = "Login Info not found";

		LoginInfo loginInfo = null;		

		try {
			loginInfo = loginInfoDAO.getLoginInfoByLanguage(language);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		if (loginInfo == null)
			throw new RuntimeException(ERROR_MESSAGE);

		return loginInfo;
	}

	public RegistrationInfo getRegistrationInfoByLanguage(String language) {
		final String ERROR_MESSAGE = "Registration Info DB request error";

		RegistrationInfo registrationInfo = null;

		try {
			registrationInfo = registrationInfoDAO.getInfoByLanguage(language);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (registrationInfo == null)
			throw new RuntimeException(ERROR_MESSAGE);

		return registrationInfo;
	}

	public void insertCustomer(Customer customer) {	
		final String ERROR_MESSAGE = "Customer registration failed.";

		try {
			customerDAO.insert(customer);
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException(ERROR_MESSAGE);
		} 
	}

	public Customer getCustomerByLogin(String customerUserName) {
		final String ERROR_MESSAGE = "Customer login is in use.";

		Customer customer = null;
		try {
			customer = customerDAO.getCustomerByLogin(customerUserName);
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException(ERROR_MESSAGE);
		} 	

		return customer;
	}

	public boolean isCustomerByLogin(String customerUserName) {
		final String ERROR_MESSAGE = "Error during login name validation occurred.";
		try {
			return customerDAO.isCustomerByLogin(customerUserName);
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException(ERROR_MESSAGE);
		} 	
	}

	public List<Sms> findSmsGroupsByDatePeriod(long userId, String startDate, String endDate) {
		final String ERROR_MESSAGE = "Error during sms loading occurred.";

		List<Sms> smsGroup = null;
		try {
			smsGroup = smsDAO.findSmsGroupsByDatePeriodAndUserId(userId, startDate, endDate);
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException(ERROR_MESSAGE);
		} 

		return smsGroup;
	}

	private void saveSmsGroup(SmsGroup smsGroup) throws Exception {

		smsGroupDAO.save(smsGroup);
	}

	private void savePhoneListOfGroup(long smsGroupId, String[] phoneNumbers) throws Exception {

		for (String phoneNumber : phoneNumbers) {
			PhoneGroup phoneGroup = new PhoneGroup();
			phoneGroup.setGroupId(smsGroupId);
			phoneGroup.setPhonenumber(phoneNumber);

			phoneGroupDAO.insert(phoneGroup);		
		}
	}
}
