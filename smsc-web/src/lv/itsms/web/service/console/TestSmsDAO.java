package lv.itsms.web.service.console;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lv.itsms.web.service.TestDAOFactory;
import transfer.domain.Sms;
import transfer.service.jpa.SmsDAO;

public class TestSmsDAO implements SmsDAO {

	public TestSmsDAO(TestDAOFactory testDAOFactory) {
		//System.out.println("Test SmsDAO created.");
	}
	
	@Override
	public Sms findSMSByCustomerId(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sms> findAllNewSms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sms findSms(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSms(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sms createSms(long customerID, String phoneNumber, String message, String sendTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sms createSms(long customerID, String phoneNumber, String message, Date sendTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSmsStatus(long smsId, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSmsMessageSMPPID(long smsId, long messageSmscId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sms findSmsBySMPPID(long SMPPID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSmsSMPPStatus(long smsId, int newStatus) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sms insert(Sms sms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBySmsGroupId(long groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBySmsId(long smsId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Sms> findSmsByDatePeriodAndUserId(long userId, Timestamp startDate, Timestamp endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
