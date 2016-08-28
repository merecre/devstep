package lv.itsms.web.page.smspanel;



/*
 * Parses URL user requests and
 * returns correspondent request processing command. 
 */

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.command.CommandFactory;
import lv.itsms.web.command.CommandTypeSingleton;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.request.validator.sms.SmsFieldsValidator;
import lv.itsms.web.request.validator.smsgroup.SmsGroupFieldsValidator;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.validator.PhoneNumberValidator;

/**
 * 
 * @author DMC
 *
 * Returns commands which will processed user request.
 */


public class CustomerPanelCommandFactory extends CommandFactory {

	Repository repository;

	HttpServletRequest request;

	Session session;

	UserPageRequestParameter pageRequest;

	SmsGroupFieldsValidator smsGroupValidator;
	
	PhoneNumberValidator phoneNumberValidator;
	
	SmsFieldsValidator smsFieldsValidator;
	
	public CustomerPanelCommandFactory(Repository repository) {
		this.repository = repository;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setUserPageRequest(	UserPageRequestParameter pageRequest) {
		this.pageRequest = pageRequest;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public UserPageRequestParameter getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(UserPageRequestParameter pageRequest) {
		this.pageRequest = pageRequest;
	}

	public Session getSession() {
		return session;
	}

	public UserPageRequestParameter getUserPageRequest(String parametrKey) {
		return CommandTypeSingleton.getInstance().getUserRequestParameters().get(parametrKey);
	}
	
	public SmsGroupFieldsValidator getFieldsValidator() {
		return smsGroupValidator;
	}

	public void setSmsGroupValidator(SmsGroupFieldsValidator fieldsValidator) {
		this.smsGroupValidator = fieldsValidator;
	}
	
	public PhoneNumberValidator getPhoneNumberValidator() {
		return phoneNumberValidator;
	}

	public void setPhoneNumberValidator(PhoneNumberValidator phoneNumberValidator) {
		this.phoneNumberValidator = phoneNumberValidator;
	}
	
	public SmsFieldsValidator getSmsFieldsValidator() {
		return smsFieldsValidator;
	}

	public void setSmsFieldsValidator(SmsFieldsValidator smsFieldsValidator) {
		this.smsFieldsValidator = smsFieldsValidator;
	}

	@Override
	protected Class<? extends CommandFactory> getGlazz() {
		return this.getClass();
	}
}
