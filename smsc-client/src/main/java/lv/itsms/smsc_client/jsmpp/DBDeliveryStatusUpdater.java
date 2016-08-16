package lv.itsms.smsc_client.jsmpp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.domain.Sms;
import transfer.service.jpa.JPADAOfactory;
import transfer.service.jpa.SmsDAO;

public class DBDeliveryStatusUpdater implements DeliveryStatusUpdater {
	
	private static final Logger logger = LoggerFactory.getLogger(DeliveryStatusManager.class);
	
	private final static String[] DELIVERY_STATUSES = {
			"UNKNOWN",
			"DELIVERED",
			"EXPIRED",
			"DELETED",
			"UNDELIVERED",
			"ACCEPTED",
			"UNKNOWN",
			"REJECTED"
	}; 
	
	JPADAOfactory factoryDAO;
	SmsDAO smsDAO;
	
	public DBDeliveryStatusUpdater(JPADAOfactory factoryDAO) {
		this.factoryDAO = factoryDAO;
		this.smsDAO = factoryDAO.getSmsDAO();
	}

	@Override
	public void updateSmsDeliveryStatus(long smsId, long messageSMPPIDec) {
		factoryDAO.startTransaction();
		smsDAO.updateSmsMessageSMPPID(smsId, messageSMPPIDec);
		smsDAO.updateSmsStatus(smsId, Sms.STATUS_SENT);
		factoryDAO.commitTransaction();	
	}

	@Override
	public void updateSmsDeliveryStatusBySMPPID(long SMPPID, int status) {
		Sms sms = smsDAO.findSmsBySMPPID(SMPPID);
		if (sms!=null) {
			long smsId = sms.getMSSID();
			String statusMessage = getDeliveryStatusMesssage(status);

			factoryDAO.startTransaction();
			smsDAO.updateSmsStatus(smsId, statusMessage);
			smsDAO.updateSmsSMPPStatus(smsId, status);
			factoryDAO.commitTransaction();
		} else {
			logger.error("Failed get delivery Status. Sms not found SMPPID: " + SMPPID);
		}		
	}
	
	private String getDeliveryStatusMesssage(int status) {
		return DELIVERY_STATUSES[status]; 
	}
}
