package lv.itsms.smsc_client.jsmpp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GSMSpecificFeature;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.MessageMode;
import org.jsmpp.bean.MessageType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.session.SessionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.itsms.smsc_client.DataSender;
import transfer.Gsm0338;
import transfer.domain.Sms;

public class MultipartMessageSender implements DataSender {

	private static final Logger logger = LoggerFactory.getLogger(MultipartMessageSender.class);
		
	private SMPPSession smppSession;
		
	public MultipartMessageSender(SMPPSession smppSession) {
		this.smppSession = smppSession;
		smppSession.addSessionStateListener(new SessionStateListenerImpl());
	}
	
	@Override
	public String send(Object object) throws Exception {
		Sms sms = (Sms)object;
		return sendMultipartMessage(sms);
	}

	String sendMultipartMessage(Sms sms) throws Exception {
		String destinationMsisdn = sms.getPhoneNumber();
		String sourceMsisdn = sms.getSender();
		String messageBody = sms.getMessage();
		String messageId = null;
		MessageClass messageClass = MessageClass.CLASS1;
		
		Alphabet alphabet = null;
		int maximumSingleMessageSize = 0;
		int maximumMultipartMessageSegmentSize = 0;
		byte[] byteSingleMessage = null;
		
		if (Gsm0338.isEncodeableInGsm0338(messageBody)) {
			byteSingleMessage = messageBody.getBytes();
			alphabet = Alphabet.ALPHA_DEFAULT;
			maximumSingleMessageSize = Gsm0338.MAX_SINGLE_MSG_SEGMENT_SIZE_7BIT;
			maximumMultipartMessageSegmentSize = Gsm0338.MAX_MULTIPART_MSG_SEGMENT_SIZE_7BIT;
		} else {
			byteSingleMessage = messageBody.getBytes("UTF-16BE");
			alphabet = Alphabet.ALPHA_UCS2;
			maximumSingleMessageSize = Gsm0338.MAX_SINGLE_MSG_SEGMENT_SIZE_UCS2;
			maximumMultipartMessageSegmentSize = Gsm0338.MAX_MULTIPART_MSG_SEGMENT_SIZE_UCS2;
		}
		
		byte[][] byteMessagesArray = null;
		ESMClass esmClass = null;
		if (messageBody.length() > maximumSingleMessageSize) {
			byteMessagesArray = splitUnicodeMessage(byteSingleMessage, maximumMultipartMessageSegmentSize);
			esmClass = new ESMClass(MessageMode.DEFAULT, MessageType.DEFAULT, GSMSpecificFeature.UDHI);
		} else {
			byteMessagesArray = new byte[][] { byteSingleMessage };
			esmClass = new ESMClass();
		}
		logger.info("Sending message " + messageBody);
		logger.info("Message is %d characters long and will be sent as %d messages with params: %s %s ",
				messageBody.length(), byteMessagesArray.length, alphabet, messageClass);

		for (int i = 0; i < byteMessagesArray.length; i++) {
			 messageId = submitMessage(smppSession, byteMessagesArray[i], sourceMsisdn, destinationMsisdn,
					messageClass, alphabet, esmClass);
			logger.info("Message submitted, message_id is " + messageId);
		}
		
		return messageId;
	}
	
	private String submitMessage(SMPPSession session, byte[] message, String sourceMsisdn, String destinationMsisdn,
			MessageClass messageClass, Alphabet alphabet, ESMClass esmClass) {
		
		final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
		registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);
		String messageId = null;
		try {
			messageId = session.submitShortMessage(SingleMessageSender.CELLULAR_MESSAGE, TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN,
					sourceMsisdn, TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, destinationMsisdn, esmClass,
					(byte) 0, (byte) 1, null, null, registeredDelivery,
					(byte) 0, new GeneralDataCoding(alphabet, esmClass), (byte) 0, message);
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
	
	private byte[][] splitUnicodeMessage(byte[] aMessage, Integer maximumMultipartMessageSegmentSize) {

		final int UDH_HEADER_IDX = 0;
		final int SAR_IDENTIFIER_IDX = 1;
		final int SAR_LENGTH_IDX = 2;
		final int REF_NUMBER_IDX = 3;
		final int TOTAL_SEGMENTS_IDX = 4;
		final int SEGMENT_NUMBER_IDX = 5;
		final int MAX_SEGMENTS = 255;
		
		final byte UDHIE_HEADER_LENGTH = 0x05;
		final byte UDHIE_IDENTIFIER_SAR = 0x00;
		final byte UDHIE_SAR_LENGTH = 0x03;

		int numberOfSegments = aMessage.length / maximumMultipartMessageSegmentSize;
		int messageLength = aMessage.length;
		if (numberOfSegments > MAX_SEGMENTS) {
			numberOfSegments = MAX_SEGMENTS;
			messageLength = numberOfSegments * maximumMultipartMessageSegmentSize;
		}
		if ((messageLength % maximumMultipartMessageSegmentSize) > 0) {
			numberOfSegments++;
		}

		byte[][] segments = new byte[numberOfSegments][];

		int lengthOfData;

		byte[] referenceNumber = new byte[1];
		new Random().nextBytes(referenceNumber);

		for (int i = 0; i < numberOfSegments; i++) {
			if (numberOfSegments - i == 1) {
				lengthOfData = messageLength - i * maximumMultipartMessageSegmentSize;
			} else {
				lengthOfData = maximumMultipartMessageSegmentSize;
			}

			segments[i] = new byte[6 + lengthOfData];
			
			segments[i][UDH_HEADER_IDX] = UDHIE_HEADER_LENGTH;
			segments[i][SAR_IDENTIFIER_IDX] = UDHIE_IDENTIFIER_SAR;
			segments[i][SAR_LENGTH_IDX] = UDHIE_SAR_LENGTH;
			segments[i][REF_NUMBER_IDX] = referenceNumber[0];
			segments[i][TOTAL_SEGMENTS_IDX] = (byte) numberOfSegments;
			segments[i][SEGMENT_NUMBER_IDX] = (byte) (i + 1);
			
			System.arraycopy(aMessage, (i * maximumMultipartMessageSegmentSize), segments[i], 6, lengthOfData);
		}
		
		return segments;
	}
	
	private class SessionStateListenerImpl implements SessionStateListener {
		@Override
		public void onStateChange(SessionState newState, SessionState oldState, Session source) {
			logger.info("Session state changed from " + oldState + " to " + newState);
		}
	}	
}
