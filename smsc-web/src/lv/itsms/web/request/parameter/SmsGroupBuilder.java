package lv.itsms.web.request.parameter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import transfer.domain.SmsGroup;

public class SmsGroupBuilder {

	public SmsGroup build(HttpServletRequest request) {
		
		SmsGroup smsGroup = new SmsGroup();
		
		UserPageRequest smsGroupUserRequest = new SmsGroupNameUserRequest();
		smsGroupUserRequest.update(request);
		smsGroup.setSmsGroupName(smsGroupUserRequest.getParameter());
		
		smsGroupUserRequest = new SmsGroupMessageUserRequest();
		smsGroupUserRequest.update(request);
		smsGroup.setGroupMessage(smsGroupUserRequest.getParameter());
		
		smsGroup.setCustomerId(getSmsOwnerId(request));
		
		smsGroupUserRequest = new SmsGroupSendDateUserRequest();
		smsGroupUserRequest.update(request);
		String userSendDate = smsGroupUserRequest.getParameter();
		
		smsGroupUserRequest = new SmsGroupSendHourUserRequest();
		smsGroupUserRequest.update(request);
		String userSendHour = smsGroupUserRequest.getParameter();
		
		smsGroupUserRequest = new SmsGroupSendMinutesUserRequest();
		smsGroupUserRequest.update(request);
		String userSendMinutes = smsGroupUserRequest.getParameter();
		
		String userSendTime = userSendDate+" "+userSendHour+":"+userSendMinutes;
		
		Date sendTime = formatSendTime(userSendTime);
		smsGroup.setSendTime(sendTime);
		
		return smsGroup;
	}
	
	private class SmsGroupNameUserRequest extends UserPageRequest {
		private final static String GROUP_NAME_KEY = "g_common_name";
		public SmsGroupNameUserRequest() {
			super(GROUP_NAME_KEY);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}
		
	}
	
	private class SmsGroupMessageUserRequest extends UserPageRequest {
		private final static String GROUP_MESSAGE_KEY = "g_common_message";
		public SmsGroupMessageUserRequest() {
			super(GROUP_MESSAGE_KEY);
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}
		
	}
	
	private class SmsGroupSendDateUserRequest extends UserPageRequest {
		private final static String GROUP_DATE_KEY = "grp_send_date";
		public SmsGroupSendDateUserRequest() {
			super(GROUP_DATE_KEY );
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}
		
	}
	
	private class SmsGroupSendHourUserRequest extends UserPageRequest {
		private final static String GROUP_HOUR_KEY = "grp_send_time_hour";
		public SmsGroupSendHourUserRequest() {
			super(GROUP_HOUR_KEY );
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}
		
	}
	
	private class SmsGroupSendMinutesUserRequest extends UserPageRequest {
		private final static String GROUP_MINUTES_KEY = "grp_send_time_min";
		public SmsGroupSendMinutesUserRequest() {
			super(GROUP_MINUTES_KEY );
		}

		@Override
		public void update(HttpServletRequest request) {
			parameterValue = request.getParameter(parameterKey);
		}
		
	}
	
	private int getSmsOwnerId (HttpServletRequest request) {
		UserPageRequest loginUserRequest = new CustomerIdRequestParameter();
		loginUserRequest.update(request);
		return Integer.parseInt(loginUserRequest.getParameter());
	}
	
	private Date formatSendTime(String sendtime) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
		java.sql.Date date = null;
		try {
			java.util.Date innerFormatDate = format.parse(sendtime);
			date = new java.sql.Date(innerFormatDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
