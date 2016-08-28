package lv.itsms.server;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.itsms.smsc_client.jsmpp.DBDeliveryStatusUpdater;
import lv.itsms.smsc_client.jsmpp.MultipartMessageSender;
import transfer.Gsm0338;
import transfer.Utils;
import transfer.domain.DeliveryStatus;
import transfer.domain.PhoneGroup;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;
import transfer.service.jpa.DeliveryStatusDAO;
import transfer.service.jpa.JPADAOfactory;
import transfer.service.jpa.ObjectNotFoundException;
import transfer.service.jpa.PhoneGroupDAO;
import transfer.service.jpa.SmsDAO;
import transfer.service.jpa.SmsGroupDAO;

public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	JPADAOfactory factoryDAO; 

	SmsDAO smsDAO;

	SmsGroupDAO smsGroupDAO;

	PhoneGroupDAO phoneGroupDAO;

	DeliveryStatusDAO deliveryStatusDAO;

	public MessageController(JPADAOfactory factoryDAO) {
		this.factoryDAO = factoryDAO;

		smsDAO = factoryDAO.getSmsDAO();

		smsGroupDAO = factoryDAO.getSmsGroupDAO();

		phoneGroupDAO = factoryDAO.getPhoneGroupDAO();

		deliveryStatusDAO = factoryDAO.getDeliveryStatusDAO();
	}

	public void prepareSmsGroupSentMessages() {
		try {		

			List<SmsGroup> allSmsGroups = populateSmsGroupRecords();
			List<SmsGroup> filteredSmsGroupsToSend = filterSmsGroups(allSmsGroups);

			for (SmsGroup smsGroup : filteredSmsGroupsToSend) {				
				factoryDAO.startTransaction();
				buildSmsRecordFromSmsGroup(smsGroup);				
				factoryDAO.commitTransaction();
			}
		} catch (PersistenceException e) {
			factoryDAO.rollback();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("prepareSmsGroupSentMessages error: " + e.getMessage());
		}
	}

	private List<SmsGroup> populateSmsGroupRecords() throws Exception {
		return smsGroupDAO.findAll();	
	}

	List<SmsGroup> filterSmsGroups(List<SmsGroup> smsGroups) throws Exception {

		SmsGroupFilterRule smsGroupFilterRule = new SmsGroupFilterRule();
		List<SmsGroup> smsGroupsToBeSend = smsGroups
				.stream()
				.filter(sg -> smsGroupFilterRule.filter(sg))
				.collect(Collectors.toList());
		return smsGroupsToBeSend;
	}

	private void buildSmsRecordFromSmsGroup(SmsGroup smsGroup) throws Exception {
		long smsGroupId = smsGroup.getSmsGroupId();			
		List<PhoneGroup> phoneGroups = phoneGroupDAO.getPhonesByGroupId(smsGroupId);				

		smsGroup.setStatus(SmsGroup.STATUS_SELECTED);
		smsGroupDAO.update(smsGroup);

		for (PhoneGroup phoneGroup : phoneGroups) {
			Sms sms = prepareAndStoreSmsGroupMessage(phoneGroup, smsGroup);

			phoneGroup.setSmsId(sms.getMSSID());
			phoneGroupDAO.update(phoneGroup);
		}
	}

	private Sms prepareAndStoreSmsGroupMessage(PhoneGroup phoneGroup, SmsGroup smsGroup) {
		Sms sms = new Sms();

		sms.setStatus(Sms.STATUS_NEW);
		sms.setPhoneNumber(phoneGroup.getPhonenumber());
		sms.setMessage(smsGroup.getGroupMessage());
		sms.setCustomerID(smsGroup.getCustomerId());
		sms.setSender(Long.toString(smsGroup.getCustomerId()));
		sms.setSendTime(smsGroup.getSendTime());
		sms.setSmsGroupId(smsGroup.getSmsGroupId());

		return smsDAO.update(sms);
	}

	public void updateSmsGroupStatusIfAllMessagesSent() {
		try {
			List<SmsGroup> smsGroups = smsGroupDAO.findAll();

			List<SmsGroup> smsGroupsToBeUpdated = smsGroups
					.stream()
					.filter(sg -> sg.getStatus().equals(SmsGroup.STATUS_SELECTED))
					.collect(Collectors.toList());

			for (SmsGroup smsGroup : smsGroupsToBeUpdated) {
				long smsGroupId = smsGroup.getSmsGroupId();						
				List<PhoneGroup> phoneGroups = phoneGroupDAO.getPhonesByGroupId(smsGroupId);

				boolean isRecordsToSend = isSmsGroupMessageToSend(phoneGroups); 

				if (!isRecordsToSend) {
					factoryDAO.startTransaction();
					smsGroup.setStatus(SmsGroup.STATUS_COMPLETED);
					smsGroupDAO.update(smsGroup);
					factoryDAO.commitTransaction();
				}
			}			
		} catch (PersistenceException e) {
			e.printStackTrace();
			factoryDAO.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isSmsGroupMessageToSend(List<PhoneGroup> phoneGroups) {
		boolean isRecordsToSend = false;

		for (PhoneGroup phoneGroup : phoneGroups) {
			try { 
				Sms sms = smsDAO.findSms(phoneGroup.getSmsId());
				if (sms.getStatus().equals(Sms.STATUS_NEW)) {
					isRecordsToSend = true;
					break;
				}
			} catch (ObjectNotFoundException exception){
				logger.info("isSmsGroupMessageToSent info: " + exception.getMessage());
			}
		}
		return isRecordsToSend;
	}

	public void updateSmsDeliveryStatus() {
		List<Sms> sentSmses = smsDAO.findSmsByStatus(Sms.STATUS_SENT);

		sentSmses
		.stream()
		.forEach(s->{
			try {
				DeliveryStatus deliveryStatus = deliveryStatusDAO.findBySMPPID(s.getMessageSMPPID());
				logger.info("deliveryStatus"+deliveryStatus.getSmppID()+" "+s.getMessageSMPPID());
				int status = deliveryStatus.getStatus();
				String statusMessage = DBDeliveryStatusUpdater.getDeliveryStatusMesssage(status);
				factoryDAO.startTransaction();
				if (isMultipartMessage(s.getMessage())) {
					s.setConcatenated(Sms.CONCATENATED_TRUE);
				}
				s.setStatus(statusMessage);
				s.setSMPPStatus(status);
				smsDAO.update(s);
				factoryDAO.commitTransaction();
			} catch (Exception exception) {
				logger.error("MessageController error: " + exception.getMessage() + " " + s.getMSSID());
			}
		});		
	}

	private boolean isMultipartMessage(String message) {
		return Gsm0338.isMultipartMessage(message);
	}
	
	public void setupController() {
		factoryDAO.getConnection().clear();
		factoryDAO.getConnection().getEntityManagerFactory().getCache().evictAll();
		//System.out.println("connection opened");
	}

	public void releaseResource() {
		factoryDAO.closeConnection();
		System.out.println("connection closed");
	}

	public void finish() {
		factoryDAO.closeEntityFactory();
	}

	public List<Sms> selectSmsToBeSend() {

		java.sql.Timestamp currentTimestamp = Utils.getCurrentDatetime();
		List<Sms> smsToSend = smsDAO.findAllNewSms();

		List<Sms> filteredMessages = smsToSend
				.stream()
				.filter(sms -> sms.getSendTime().before(currentTimestamp))
				.collect(Collectors.toList());

		return filteredMessages;
	}
}
