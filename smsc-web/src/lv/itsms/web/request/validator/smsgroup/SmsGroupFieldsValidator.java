package lv.itsms.web.request.validator.smsgroup;

import lv.itsms.web.request.validator.UserRequestValidatorImpl;
import transfer.domain.SmsGroup;

public class SmsGroupFieldsValidator extends UserRequestValidatorImpl {
		
	SmsGroupDescriptionValidator descriptionValidator;
	SmsGroupMessageValidator messageValidator;
	SmsGroupDatetimeValidator datetimeValidator;
	
	public SmsGroupFieldsValidator() {
		this.descriptionValidator = new SmsGroupDescriptionValidator();
		messageValidator = new SmsGroupMessageValidator();
		datetimeValidator = new SmsGroupDatetimeValidator();
	}
	
	@Override
	public void prepareRules() {
		descriptionValidator.prepareRules();
		messageValidator.prepareRules();
		datetimeValidator.prepareRules();
	}
	
	@Override
	public boolean validate(Object object) {
		SmsGroup smsGroup = (SmsGroup)object;

		String smsGroupDescription = smsGroup.getSmsGroupName();
		descriptionValidator.validate(smsGroupDescription);
		
		String smsGroupMessage = smsGroup.getGroupMessage();
		messageValidator.validate(smsGroupMessage);
		
		java.util.Date date = smsGroup.getSendTime();
		datetimeValidator.validate(date);
		return true;
	}	
}
