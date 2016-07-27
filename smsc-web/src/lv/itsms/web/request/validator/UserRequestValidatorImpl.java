package lv.itsms.web.request.validator;

import java.util.List;

public class UserRequestValidatorImpl implements UserRequestValidator {

	List<Rule> rules;

	@Override
	public boolean validate() {
		for (Rule rule : rules) {
			if (!rule.doRule()) {
				return false;
			}
		}
		
		return true;
	}
	
	public void addRule(Rule rule) {
		rules.add(rule);
	}

}
