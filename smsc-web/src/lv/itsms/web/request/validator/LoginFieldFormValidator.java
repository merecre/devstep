package lv.itsms.web.request.validator;

import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.request.parameter.UserPageRequest;

public class LoginFieldFormValidator implements UserRequestValidator {

	List<Rule> rules;
	
	public LoginFieldFormValidator() {
		this.rules = new ArrayList<Rule>();
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	public LoginFieldFormValidator(List<Rule> rules) {
		this.rules = rules;
	}

	@Override
	public boolean validate() {
		for (Rule rule : rules) {
			rule.doRule();
		}
		return true;
	}

}
