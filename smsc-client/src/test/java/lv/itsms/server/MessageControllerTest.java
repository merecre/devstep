package lv.itsms.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import lv.itsms.server.MessageController;
import transfer.Utils;
import transfer.domain.SmsGroup;
import transfer.service.jpa.JPADAOfactory;

public class MessageControllerTest {

	JPADAOfactory factoryDAO;
	
	MessageController messageController;
	
	@Before
	public void init() {
		factoryDAO = new JPADAOfactory();
		messageController = new MessageController(factoryDAO);
	}
	
	@Test
	public void smsGroupIsFilteredCorrectly() throws Exception {
					
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(1);
		smsGroup.setGroupMessage("Group Message 1");
		smsGroup.setSmsGroupId(1);
		smsGroup.setSmsGroupName("Group name 1");
		
		Timestamp timestamp = Utils.getCurrentDatetime();
		timestamp = new Timestamp(timestamp.getTime() - (60 * 1000L));
		smsGroup.setSendTime(timestamp);
		smsGroup.setStatus(SmsGroup.STATUS_ACTIVE);
		
		List<SmsGroup> smsGroups = new ArrayList<>();
		smsGroups.add(smsGroup);
		
		List<SmsGroup> filteredSmsGroups = messageController.filterSmsGroups(smsGroups);
		
		Assert.assertEquals(1, filteredSmsGroups.size());
	}
	
	@Test 
	public void noActiveSmsGroups () throws Exception {
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(1);
		smsGroup.setGroupMessage("Group Message 1");
		smsGroup.setSmsGroupId(1);
		smsGroup.setSmsGroupName("Group name 1");
		smsGroup.setSendTime(Utils.getCurrentDatetime());
		smsGroup.setStatus(SmsGroup.STATUS_SENT);
		
		List<SmsGroup> smsGroups = new ArrayList<>();
		smsGroups.add(smsGroup);
		
		List<SmsGroup> filteredSmsGroups = messageController.filterSmsGroups(smsGroups);
		
		Assert.assertEquals(0, filteredSmsGroups.size());
	}

}
