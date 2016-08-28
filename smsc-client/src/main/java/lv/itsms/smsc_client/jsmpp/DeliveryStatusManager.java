package lv.itsms.smsc_client.jsmpp;

import org.jsmpp.bean.DeliveryReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.domain.Sms;
import transfer.service.jpa.JPADAOfactory;
import transfer.service.jpa.SmsDAO;
/**
 * 
 * @author DMC
 *
 */

public class DeliveryStatusManager {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryStatusManager.class);

	DeliveryStatusUpdater statusUpdater;

	public DeliveryStatusManager(DeliveryStatusUpdater statusUpdater) {
		this.statusUpdater = statusUpdater;
	}

	public void updateSmsDeliveryStatus(Sms sms, String hexMessageSmscId) {
		long smsId = sms.getMSSID();
		if (hexMessageSmscId != null) {
			try {
				long messageSMPPIDec = Long.parseLong(hexMessageSmscId, 16);
				statusUpdater.updateSmsDeliveryStatus(smsId, messageSMPPIDec);
			} catch (NumberFormatException exception) {
				logger.error("updateSmsDeliveryStatus error :" + smsId + " " + exception.getMessage());				
			}
		} else {
			logger.error("updateSmsDeliveryStatus error :" + smsId);
		}
	}

	public void updateSmsDeliveryStatusByReport(DeliveryReceipt delReceipt) {
		long SMPPID = Long.parseLong(delReceipt.getId());
		int status = delReceipt.getFinalStatus().value();
		updateStatus(SMPPID, status);

		long id = SMPPID & 0xffffffff;
		String messageId = Long.toString(id, 16).toUpperCase();		
	}

	private void updateStatus(long SMPPID, int status) {		
		statusUpdater.updateSmsDeliveryStatusBySMPPID(SMPPID, status);
	}
}
