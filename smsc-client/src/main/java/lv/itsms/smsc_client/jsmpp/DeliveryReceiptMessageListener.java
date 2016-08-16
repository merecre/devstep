package lv.itsms.smsc_client.jsmpp;

import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.MessageType;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.domain.Sms;
import transfer.service.jpa.JPADAOfactory;
import transfer.service.jpa.SmsDAO;

public class DeliveryReceiptMessageListener implements MessageReceiverListener {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryReceiptMessageListener.class);

	DeliveryStatusManager deliveryManager;

	public DeliveryReceiptMessageListener(DeliveryStatusManager deliveryManager) {
		this.deliveryManager = deliveryManager;
	}

	public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {
		if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {
			try {
				DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
				deliveryManager.updateSmsDeliveryStatusByReport(delReceipt);
			} catch (InvalidDeliveryReceiptException e) {
				logger.error("Delivery Receipt failed:"+ e.getMessage());
			}
		} else {
			logger.info("Receiving message : " + new String(deliverSm.getShortMessage()));
		}	
	}

	public DataSmResult onAcceptDataSm(DataSm deliverSm, Session arg1) throws ProcessRequestException {
		logger.info("onAcceptDataSm: "+deliverSm);
		return null;
	}

	public void onAcceptAlertNotification(AlertNotification arg0) {
		logger.info("AlertNotification:"+arg0);
	}
}
