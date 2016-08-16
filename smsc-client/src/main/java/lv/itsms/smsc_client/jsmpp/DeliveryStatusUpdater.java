package lv.itsms.smsc_client.jsmpp;

public interface DeliveryStatusUpdater {

	void updateSmsDeliveryStatus(long smsId, long messageSMPPIDec);

	void updateSmsDeliveryStatusBySMPPID(long sMPPID, int status);
}
