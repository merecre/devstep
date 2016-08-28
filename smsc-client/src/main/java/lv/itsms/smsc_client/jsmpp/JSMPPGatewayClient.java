package lv.itsms.smsc_client.jsmpp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.PropertyConfigurator;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.DataCoding;
import org.jsmpp.bean.DataCodings;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.MessageType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.itsms.smsc_client.DataSender;
import lv.itsms.smsc_client.GatewayClient;
import transfer.Utils;
import transfer.domain.Sms;

public class JSMPPGatewayClient extends GatewayClient {

	/*
	 * CMT: Cellular Messaging
	 * CPT: Cellular Paging
	 * VMN: Voice Mail Notification
	 * VMA: Voice Mail Alerting
	 * WAP: Wireless Application Protocol
	 * USSD: Unstructured Supplementary Services Data
	 */
		
	private static final String DEFAULT_PASSWORD = "jpwd";
	private static final String DEFAULT_SYSID = "j";
	
	private static final Logger logger = LoggerFactory.getLogger(JSMPPGatewayClient.class);
	private static final String DEFAULT_LOG4J_PATH = "stress/client-log4j.properties";
	private static final String DEFAULT_HOST = "localhost";
	private static final Integer DEFAULT_PORT = 8056;
	private static final Long DEFAULT_TRANSACTIONTIMER = 2000L;
	private static final Integer DEFAULT_BULK_SIZE = 100000;
	private static final Integer DEFAULT_PROCESSOR_DEGREE = 3;
	private static final Integer DEFAULT_MAX_OUTSTANDING = 10;
	
	DataSender messageSender;

	private SMPPSession smppSession;
	
	public JSMPPGatewayClient(MessageReceiverListener deliveryMessageListener) {	
		this.smppSession = new SMPPSession();
		this.smppSession.setMessageReceiverListener(deliveryMessageListener); 
		this.messageSender = new MultipartMessageSender(smppSession);
	}

	@Override
	public String send(Object object) throws Exception { 
		Sms sms = (Sms)object;   
		return messageSender.send(sms);
	}
	
	@Override
	public void connect() {	

		String host = System.getProperty("jsmpp.client.host", DEFAULT_HOST);
		String systemId = System.getProperty("jsmpp.client.systemId", DEFAULT_SYSID);
		String password = System.getProperty("jsmpp.client.password", DEFAULT_PASSWORD);

		int port;
		try {
			port = Integer.parseInt(System.getProperty("jsmpp.client.port", DEFAULT_PORT.toString()));
		} catch (NumberFormatException e) {
			port = DEFAULT_PORT;
		}

		long transactionTimer;
		try {
			transactionTimer = Integer.parseInt(System.getProperty("jsmpp.client.transactionTimer", DEFAULT_TRANSACTIONTIMER.toString()));
		} catch (NumberFormatException e) {
			transactionTimer = DEFAULT_TRANSACTIONTIMER;
		}

		int bulkSize;
		try {
			bulkSize = Integer.parseInt(System.getProperty("jsmpp.client.bulkSize", DEFAULT_BULK_SIZE.toString()));
		} catch (NumberFormatException e) {
			bulkSize = DEFAULT_BULK_SIZE;
		}

		int processorDegree;
		try {
			processorDegree = Integer.parseInt(System.getProperty("jsmpp.client.procDegree", DEFAULT_PROCESSOR_DEGREE.toString()));
		} catch (NumberFormatException e) {
			processorDegree = DEFAULT_PROCESSOR_DEGREE;
		}

		int maxOutstanding;
		try {
			maxOutstanding = Integer.parseInt(System.getProperty("jsmpp.client.maxOutstanding", DEFAULT_MAX_OUTSTANDING.toString()));
		} catch (NumberFormatException e) {
			maxOutstanding = DEFAULT_MAX_OUTSTANDING;
		}

		String log4jPath = System.getProperty("jsmpp.client.log4jPath", DEFAULT_LOG4J_PATH);
		PropertyConfigurator.configure(log4jPath);

		logger.info("Target server {}:{}", host, port);
		logger.info("System ID: {}", systemId);
		logger.info("Password: {}", password);
		logger.info("Transaction timer: {}", transactionTimer);
		logger.info("Bulk size: {}", bulkSize);
		logger.info("Max outstanding: {}", maxOutstanding);
		logger.info("Processor degree: {}", processorDegree);

		try {
			smppSession.connectAndBind(host, port, BindType.BIND_TRX, systemId,
					password, "cln", TypeOfNumber.UNKNOWN,
					NumberingPlanIndicator.UNKNOWN, null);
			logger.info("Bound to " + host + ":" + port);
		} catch (IOException e) {
			logger.error("Failed initialize connection or bind", e);
			return;
		}
	}

	@Override
	public void disconnect() {
		logger.info("Done");
		smppSession.unbindAndClose();		
	}
}
