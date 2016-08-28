package lv.itsms.smsc_client.jsmpp;

import java.io.IOException;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.DataCoding;
import org.jsmpp.bean.DataCodings;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.itsms.smsc_client.DataSender;
import transfer.Gsm0338;
import transfer.domain.Sms;

public class SingleMessageSender implements DataSender {

	private static final Logger logger = LoggerFactory.getLogger(SingleMessageSender.class);
	public static final String CELLULAR_MESSAGE = "CMT";
	
	private SMPPSession smppSession;

	//MessageReceiverListener deliveryMessageListener;
		
	public SingleMessageSender(SMPPSession smppSession) {
		this.smppSession = smppSession;
	}

	@Override
	public String send(Object object) {
		Sms sms = (Sms)object;
		return sendSingleMessage(sms) ;
	}

	private String sendSingleMessage(Sms sms) {
		String destinationAddr = sms.getPhoneNumber();
		String sourceAddr = sms.getSender();
		String message = sms.getMessage();
		String messageId = null;
		try {
			if (Gsm0338.isEncodeableInGsm0338(message)) {
				messageId = sendASCIIMessage(destinationAddr, sourceAddr, message);
			} else {
				messageId = sendUnicodeMessage(destinationAddr, sourceAddr, message);				
			}
		} catch (PDUException e) {
			logger.error("Failed submit short message '" + message + "'", e);
		} catch (ResponseTimeoutException e) {
			logger.error("Failed submit short message '" + message + "'", e);
		} catch (InvalidResponseException e) {
			logger.error("Failed submit short message '" + message + "'", e);
		} catch (NegativeResponseException e) {
			logger.error("Failed submit short message '" + message + "'", e);
		} catch (IOException e) {
			logger.error("Failed submit short message '" + message + "'", e);
		}

		return messageId;
	}

	private String sendASCIIMessage(String destinationAddr, String sourceAddr, String message) throws PDUException, ResponseTimeoutException, InvalidResponseException, NegativeResponseException, IOException {		
		logger.info("Send ASCII message '" + message + "'");
		byte[] data = message.getBytes();
		return sendSimpleMessage(destinationAddr, sourceAddr, data, DataCodings.ZERO);
	}
	
	private String sendUnicodeMessage(String destinationAddr, String sourceAddr, String message) throws PDUException, ResponseTimeoutException, InvalidResponseException, NegativeResponseException, IOException {    
		DataCoding dataCoding = new GeneralDataCoding(Alphabet.ALPHA_UCS2, MessageClass.CLASS1, false);
		byte[] data = message.getBytes("UTF-16BE");
		logger.info("Send UTF-8 message '" + data + "'");
		return sendSimpleMessage(destinationAddr, sourceAddr, data, dataCoding);
	}
	
	private String sendSimpleMessage(String destinationAddr, String sourceAddr, byte[] message, DataCoding dataCoding) throws PDUException, ResponseTimeoutException, InvalidResponseException, NegativeResponseException, IOException {
		final RegisteredDelivery registeredDelivery;
		registeredDelivery = new RegisteredDelivery();
		registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);
		String messageId = smppSession.submitShortMessage(CELLULAR_MESSAGE, TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, sourceAddr, 
				TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, destinationAddr, 
				new ESMClass(), (byte)0, (byte)0, 
				null, null, registeredDelivery, 
				(byte)0, 
				dataCoding, 
				(byte)0, message);
		return messageId;
	}
}
