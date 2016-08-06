package lv.itsms.web.request.validator;

public interface UserRequestValidator {
	boolean validate(Object object);
	
	void prepareRules();
}
