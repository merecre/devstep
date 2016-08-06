package lv.itsms.web.request.validator;

import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.request.validator.rule.Rule;

public class UserRequestValidatorImpl implements UserRequestValidator {

	protected List<Rule> rules;

	public UserRequestValidatorImpl() {
		this.rules = new ArrayList<>();
	}

	public void prepareRules() {
		if (rules == null) {
			rules = new ArrayList<>();
		}
	} 

	@Override
	public boolean validate(Object object) {
		for (Rule rule : rules) {
			if (!rule.doRule(object)) {
				return false;
			}
		}

		return true;
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}
}
