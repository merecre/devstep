package lv.itsms.web.request.parameter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.menu.CustomerIdRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhoneRequestParameter;
import lv.itsms.web.request.parameter.smspanel.SmsPhonesRequestParameter;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;

public class SmsBuilder {

	public Sms build(HttpServletRequest request) {

		Sms sms = new Sms();

		UserPageRequestParameter smsUserRequest = new SmsSenderUserRequest();
		smsUserRequest.update(request);
		sms.setSender(smsUserRequest.getParameter());		

		smsUserRequest = new SmsGroupMessageUserRequest();
		smsUserRequest.update(request);
		sms.setMessage(smsUserRequest.getParameter());

		smsUserRequest = new SmsPhoneRequestParameter();
		smsUserRequest.update(request);
		String phoneNumber = smsUserRequest.getParameter();
		sms.setPhoneNumber(phoneNumber);

		sms.setCustomerID(getSmsOwnerId(request));

		smsUserRequest = new SmsGroupSendDateUserRequest();
		smsUserRequest.update(request);
		String userSendDate = smsUserRequest.getParameter();

		smsUserRequest = new SmsGroupSendHourUserRequest();
		smsUserRequest.update(request);
		String userSendHour = smsUserRequest.getParameter();

		smsUserRequest = new SmsGroupSendMinutesUserRequest();
		smsUserRequest.update(request);
		String userSendMinutes = smsUserRequest.getParameter();

		String userSendTime = userSendDate+" "+userSendHour+":"+userSendMinutes;

		java.sql.Timestamp sendTime = formatSendTime(userSendTime);
		sms.setSendTime(sendTime);

		return sms;
	}

	private class SmsSenderUserRequest extends UserPageRequestParameter {
		private final static String URL_PARAMETER = "sms_sender";
		public SmsSenderUserRequest() {
			super(URL_PARAMETER);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}

	}

	private class SmsGroupMessageUserRequest extends UserPageRequestParameter {
		private final static String SMS_MESSAGE_KEY = "sms_message";
		public SmsGroupMessageUserRequest() {
			super(SMS_MESSAGE_KEY);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}

	}

	private class SmsGroupSendDateUserRequest extends UserPageRequestParameter {
		private final static String GROUP_DATE_KEY = "sms_send_date";
		public SmsGroupSendDateUserRequest() {
			super(GROUP_DATE_KEY );
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}

	}

	private class SmsGroupSendHourUserRequest extends UserPageRequestParameter {
		private final static String GROUP_HOUR_KEY = "sms_send_time_hour";
		public SmsGroupSendHourUserRequest() {
			super(GROUP_HOUR_KEY );
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}

	}

	private class SmsGroupSendMinutesUserRequest extends UserPageRequestParameter {
		private final static String GROUP_MINUTES_KEY = "sms_send_time_min";
		public SmsGroupSendMinutesUserRequest() {
			super(GROUP_MINUTES_KEY );
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}

	}

	private int getSmsOwnerId (HttpServletRequest request) {
		UserPageRequestParameter loginUserRequest = new CustomerIdRequestParameter();
		loginUserRequest.update(request);
		return Integer.parseInt(loginUserRequest.getParameter());
	}

	private java.sql.Timestamp formatSendTime(String sendtime) {
		final String ERROR_DATE = "Incorrect.Date";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
		java.sql.Timestamp  date = null;
		try {
			java.util.Date innerFormatDate = format.parse(sendtime);
			date = new java.sql.Timestamp(innerFormatDate.getTime());
		} catch (Exception e) {
			throw new RuntimeException(ERROR_DATE);
		}
		return date;
	}
}
