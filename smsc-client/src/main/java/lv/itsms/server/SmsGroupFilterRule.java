package lv.itsms.server;

import transfer.Utils;
import transfer.domain.SmsGroup;

public class SmsGroupFilterRule {

	boolean filter(SmsGroup smsGroup) {
		java.sql.Timestamp currentTimestamp = Utils.getCurrentDatetime();
		return smsGroup.getStatus().equals(SmsGroup.STATUS_ACTIVE)
		     && smsGroup.getSendTime().before(currentTimestamp);
	}
}
