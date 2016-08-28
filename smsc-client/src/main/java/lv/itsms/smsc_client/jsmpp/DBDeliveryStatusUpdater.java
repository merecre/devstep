package lv.itsms.smsc_client.jsmpp;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.domain.DeliveryStatus;
import transfer.domain.Sms;
import transfer.service.jpa.DeliveryStatusDAO;
import transfer.service.jpa.JPADAOfactory;
import transfer.service.jpa.SmsDAO;

public class DBDeliveryStatusUpdater implements DeliveryStatusUpdater {

	private final static String[] DELIVERY_STATUSES = {
			"NOSTATUS",
			"DELIVERED",
			"EXPIRED",
			"DELETED",
			"UNDELIVERED",
			"ACCEPTED",
			"UNKNOWN",
			"REJECTED"
	}; 

	private static final Logger logger = LoggerFactory.getLogger(DBDeliveryStatusUpdater.class);

	JPADAOfactory factoryDAO;
	SmsDAO smsDAO;
	DeliveryStatusDAO deliveryStatusDAO;

	public DBDeliveryStatusUpdater(JPADAOfactory factoryDAO) {
		this.factoryDAO = factoryDAO;
		this.smsDAO = factoryDAO.getSmsDAO();
		this.deliveryStatusDAO = factoryDAO.getDeliveryStatusDAO();
	}

	@Override
	public void updateSmsDeliveryStatus(long smsId, long messageSMPPIDec) {
		try {
			factoryDAO.startTransaction();
			smsDAO.updateSmsMessageSMPPID(smsId, messageSMPPIDec);
			smsDAO.updateSmsStatus(smsId, Sms.STATUS_SENT);
			factoryDAO.commitTransaction();
		} catch (PersistenceException exception) {
			logger.error("updateSmsDeliveryStatus. Sms: " + smsId + ". Error: "+ exception.getMessage());			
		}
	}

	@Override
	public void updateSmsDeliveryStatusBySMPPID(long SMPPID, int status) {

		try {
			factoryDAO.startTransaction();
			DeliveryStatus deliveryStatus = new DeliveryStatus(SMPPID, status);
			deliveryStatusDAO.insert(deliveryStatus);
			factoryDAO.commitTransaction();
		} catch (PersistenceException exception) {
			logger.error("Failed get delivery Status. Sms not found SMPPID: " + SMPPID + ". Error: "+ exception.getMessage());			
		}	
	}

	public static String getDeliveryStatusMesssage(int status) {
		return DELIVERY_STATUSES[status]; 
	}
}
