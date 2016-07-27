package lv.itsms.web.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import transfer.domain.Sms;

public interface SmsDAO {
	boolean insert(Sms sms) throws Exception;
	
	List<Sms> findSmsGroupsByDatePeriodAndUserId (long customerId, String startDate, String endDate) throws Exception;
}
