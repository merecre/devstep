package lv.itsms.web.request.validator.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import transfer.validator.Rule;
import transfer.validator.rule.SmsMaxLengthRule;

public class SmsMaxLengthRuleTest {

	@Test
	public void isValidSmsMesageLength() {
			
		Rule smsMaxLengthRule = new SmsMaxLengthRule();
		
		String message = "Life is good";
		boolean result = smsMaxLengthRule.doRule(message);
		assertTrue(result);
	}
	
	@Test
	public void messageIsLatinButEquals140Chars() {
		Rule smsMaxLengthRule = new SmsMaxLengthRule();
		
		String message = new String(
				new char[SmsMaxLengthRule.SMS_MAX_LEN_LATIN])
				.replace('\0', ' ');
		
		boolean result = smsMaxLengthRule.doRule(message);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public void messageIsLatinButMoreThan140Chars() {
		Rule smsMaxLengthRule = new SmsMaxLengthRule();
		
		String message = new String(
				new char[SmsMaxLengthRule.SMS_MAX_LEN_LATIN+1])
				.replace('\0', ' ');
		
		boolean result = smsMaxLengthRule.doRule(message);
		assertFalse(result);
	}
	
	@Test(expected=RuntimeException.class)
	public void messageIsUnicodeButMoreThan70Chars() {
		Rule smsMaxLengthRule = new SmsMaxLengthRule();
		
		String message = new String("יצףךוםדרשפûגאןנמכהÿקסלטעüב‏ז‎תח"
				+ "יצףךוםדרשחזהכמנןאגûפÿקסלטעüנןאגיצףךוםדרפ");
				
		boolean result = smsMaxLengthRule.doRule(message);
		assertFalse(result);
	}
	
	@Test
	public void messageIsUnicodeButEqual70Chars() {
		Rule smsMaxLengthRule = new SmsMaxLengthRule();
		
		String message = new String("יצףךוםדרשפûגאןנמכהÿקסלטעüב‏ז‎תח"
				+ "יצףךוםדרשחזהכמנןאגûפÿקסלטעüנןאגיצףךוםדר");
				
		boolean result = smsMaxLengthRule.doRule(message);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public void messageIsNULL() {
		Rule smsMaxLengthRule = new SmsMaxLengthRule();
		
		String message = null;
		
		boolean result = smsMaxLengthRule.doRule(message);
		assertFalse(result);
	}
}
