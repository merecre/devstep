package lv.itsms.web.request.validator.rule;

import java.io.UnsupportedEncodingException;

import transfer.validator.Rule;

public class StringMaxByteRule implements Rule {

	String errorMessage = "";
	int maxByteLength = 0;
	
	public StringMaxByteRule(int maxByteLength, String errorMessage) {
		this.maxByteLength = maxByteLength;
	}

	@Override
	public boolean doRule(Object object) {
		String field = (String) object;
		try {
			byte[] b = field.getBytes("UTF-8");
			return b.length <= maxByteLength;
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}
}
