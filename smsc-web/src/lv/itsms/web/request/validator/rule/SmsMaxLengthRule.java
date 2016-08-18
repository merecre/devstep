package lv.itsms.web.request.validator.rule;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class SmsMaxLengthRule implements Rule {

	private static CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
	
	final static String MAX_BYTE_LEN_ERROR = "Maximum.length.of.message.exceeded.";
	public final static int SMS_MAX_LEN_LATIN = 140;
	public final static int SMS_MAX_LEN_UCS2 = 70;
		
	@Override
	public boolean doRule(Object object) {
		String message = (String)object;		
		int maxByteLength = isASCII(message) ? SMS_MAX_LEN_LATIN : SMS_MAX_LEN_UCS2;		

		return new MaxLengthRule(maxByteLength, MAX_BYTE_LEN_ERROR).doRule(message);
	}

	boolean isASCII(String message){
	    return message!=null && encoder.canEncode(message);
	}
}
