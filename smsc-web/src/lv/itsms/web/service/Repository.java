package lv.itsms.web.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.PersistenceException;

import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.page.info.ProfileInfo;
import lv.itsms.web.page.info.RegistrationInfo;
import lv.itsms.web.page.info.SmsPanelInfo;
import transfer.domain.Customer;
import transfer.domain.PhoneGroup;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;
import transfer.service.jpa.JPADAOfactory;
import transfer.service.jpa.PhoneGroupDAO;
import transfer.service.jpa.SmsDAO;
import transfer.service.jpa.SmsGroupDAO;

public class Repository {

	DAOFactory factoryDAO;
	SmsGroupDAO smsGroupDAO;
	PhoneGroupDAO phoneGroupDAO;
	CustomerDAO customerDAO;
	LoginInfoDAO loginInfoDAO;
	RegistrationInfoDAO registrationInfoDAO;
	ProfileInfoDAO profileInfoDAO;
	SmsPanelInfoDAO smsPanelInfoDAO;
	SmsDAO smsDAO;
	
	JPADAOfactory JPAfactoryDAO;
	
	public Repository() {	
		this(DAOFactory.DB_DAO);
	}		

	public Repository(int DAOType) {

		factoryDAO = DAOFactory.getDAOFactory(DAOType);
		this.customerDAO = factoryDAO.getCustomerDAO();
		this.phoneGroupDAO = factoryDAO.getPhoneGroupDAO();		
		this.smsGroupDAO = factoryDAO.getSmsGroupDAO();	
		this.loginInfoDAO = factoryDAO.getLoginInfoDAO();	
		this.registrationInfoDAO = factoryDAO.getRegistrationInfoDAO();	
		this.profileInfoDAO = factoryDAO.getProfileInfoDAO();
		this.smsPanelInfoDAO = factoryDAO.getSmsPanelInfoDAO();
		this.JPAfactoryDAO = new JPADAOfactory();
		this.smsDAO = JPAfactoryDAO.getSmsDAO();
	}	

	public void deleteSmsGroupByUserIdAndGroupName(int userId, String smsGroupName) {

		try {
			smsGroupDAO.deleteByUserIdAndGroupName(userId, smsGroupName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void deleteSmsGroupByGroupId(int groupId) throws Exception {

		factoryDAO.startTransaction();
		try {
			smsGroupDAO.deleteByGroupId(groupId);
			phoneGroupDAO.deleteByGroupId(groupId);
			factoryDAO.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			factoryDAO.rollbackTransaction();
		} finally {
			factoryDAO.stopTransaction();
		}
	}

	public void deleteSmsGroupsByGroupId(String[] groupIds) throws Exception {

		factoryDAO.startTransaction();
		try {
			for (String smsGroupId : groupIds) {
				int groupId = Integer.parseInt(smsGroupId);
				smsGroupDAO.deleteByGroupId(groupId);
				phoneGroupDAO.deleteByGroupId(groupId);
			}
			factoryDAO.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			factoryDAO.rollbackTransaction();			
		} finally {
			factoryDAO.stopTransaction();			
		}
	}
	
	public void updateSmsGroup(SmsGroup smsGroup, String[] phoneNumbers) throws Exception {
		
		factoryDAO.startTransaction();
		JPAfactoryDAO.getConnection();
		try {			
			saveSmsGroup(smsGroup);
			long smsGroupId = smsGroup.getSmsGroupId();
			
			List<PhoneGroup> phoneGroups = phoneGroupDAO.getPhonesByGroupId(smsGroupId);
			JPAfactoryDAO.startTransaction();		
			phoneGroups
				.stream()
				.forEach(pg-> {	
							smsDAO.deleteBySmsId(pg.getSmsId());
						});
			JPAfactoryDAO.commitTransaction();
			phoneGroupDAO.deleteByGroupId(smsGroupId);
			savePhoneListOfGroup(smsGroupId, phoneNumbers);	
			factoryDAO.commitTransaction();
		} catch (PersistenceException e) {
			e.printStackTrace();
			factoryDAO.rollbackTransaction();
			JPAfactoryDAO.rollback();				
		} catch (Exception e) {
			e.printStackTrace();
			factoryDAO.rollbackTransaction();
			JPAfactoryDAO.rollback();			
		} finally {
			factoryDAO.stopTransaction();
			JPAfactoryDAO.closeConnection();
			JPAfactoryDAO.closeEntityFactory();
		}
	}

	public List<SmsGroup> getSmsGroupByUserId(int userId) throws Exception {

		factoryDAO.startTransaction();
		List<SmsGroup> smsGroups = null;
		smsGroups = smsGroupDAO.getGroupsByUserId(userId);
		factoryDAO.stopTransaction();
		
		return smsGroups;
	}

	public SmsGroup getSmsGroupById(int groupId) throws Exception {

		SmsGroup smsGroup = null;
		factoryDAO.startTransaction();
		smsGroup = smsGroupDAO.getGroupById(groupId);
		factoryDAO.stopTransaction();

		return smsGroup;
	}
	
	public SmsGroup getSmsGroupByIdAndCustomerId(int groupId, int customerId) throws Exception {

		SmsGroup smsGroup = null;
		factoryDAO.startTransaction();
		smsGroup = smsGroupDAO.getGroupByIdAndCustomerId(groupId, customerId);
		factoryDAO.stopTransaction();

		return smsGroup;
	}

	public List<PhoneGroup> getPhonesInGroupByGroupId (int groupId) {

		List<PhoneGroup> phoneGroups = null;
		
		try {
			factoryDAO.startTransaction();
			phoneGroups = phoneGroupDAO.getPhonesByGroupId(groupId);
			factoryDAO.stopTransaction();
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

	public SmsPanelInfo getSmsPanelInfoByLanguage(String language) {
		final String ERROR_MESSAGE = "SmsPanel Info not found";

		SmsPanelInfo smsPanelInfo = null;		

		try {
			smsPanelInfo = smsPanelInfoDAO.getSmsPanelInfoByLanguage(language);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		if (smsPanelInfo == null)
			throw new RuntimeException(ERROR_MESSAGE);

		return smsPanelInfo;	
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

	public List<SmsGroup> findSmsGroupsByDatePeriod(long userId, String startDate, String endDate) {
		final String ERROR_MESSAGE = "Error during sms loading occurred.";

		List<SmsGroup> smsGroup = null;
		try {
			smsGroup = smsGroupDAO.findSmsGroupsByDatePeriodAndUserId(userId, startDate, endDate);
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException(ERROR_MESSAGE);
		} 

		return smsGroup;
	}

	public List<Sms> findSmsByDatePeriod(long userId, Timestamp startDateTimestamp, Timestamp endDateTimestamp) {
		final String ERROR_MESSAGE = "Error during sms loading occurred.";

		List<Sms> smses = null;
		try {
			smses = smsDAO.findSmsByDatePeriodAndUserId(userId, startDateTimestamp, endDateTimestamp);
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException(ERROR_MESSAGE);
		} 

		return smses;
	}
	
	public ProfileInfo getProfileInfoByLanguage(String language) {	
		final String ERROR_MESSAGE = "Error during profile info loading occurred.";

		ProfileInfo profileInfo = null;
		try {
			profileInfo = profileInfoDAO.getProfileInfoByLanguage(language);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(ERROR_MESSAGE);
		}

		return profileInfo;
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
