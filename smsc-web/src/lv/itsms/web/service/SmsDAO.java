package lv.itsms.web.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import transfer.domain.Sms;

public interface SmsDAO {
	boolean insert(Sms sms) throws Exception;
}
