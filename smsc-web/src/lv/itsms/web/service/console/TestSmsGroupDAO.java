package lv.itsms.web.service.console;

import java.util.ArrayList;
import java.util.List;

import lv.itsms.web.service.TestDAOFactory;
import transfer.domain.SmsGroup;
import transfer.service.jpa.SmsGroupDAO;

public class TestSmsGroupDAO implements SmsGroupDAO {

	public TestSmsGroupDAO(TestDAOFactory testDAOFactory) {
		//System.out.println("Test SmsDAO created.");
	}

	@Override
	public void save(SmsGroup smsGroup) throws Exception {
		smsGroup.setSmsGroupId(20);
		System.out.print("SmsGroup saved: " + smsGroup.getSmsGroupId());
	}

	@Override
	public boolean deleteByUserIdAndGroupName(int userId, String groupName) throws Exception {
		System.out.print("SmsGroup deleted: " + userId + " " + groupName);
		return true;
	}

	@Override
	public boolean deleteByGroupId(int groupId) throws Exception {
		System.out.print("SmsGroup deleted by Id:"+ groupId);
		return true;
	}

	@Override
	public SmsGroup insert(SmsGroup smsGroup) throws Exception {
		System.out.print("SmsGroup inserted " + smsGroup);
		return smsGroup;
	}

	@Override
	public boolean update(SmsGroup smsGroup) throws Exception {
		System.out.print("SmsGroup updated: " + smsGroup);
		return false;
	}

	@Override
	public int getIdByUserIdAndGroupName(SmsGroup smsGroup) throws Exception {
		System.out.print("return id by user id and group name " + smsGroup);
		return 12;
	}

	@Override
	public List<SmsGroup> getGroupsByUserId(long userId) throws Exception {
		List<SmsGroup> smsGroups = new ArrayList<>();
		int smsGroupId = 0;
		if (userId == 1) {
			smsGroupId = 1;
		} else {
			throw new Exception("Group not found"+userId);  
		}

		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(smsGroupId);
		smsGroups.add(smsGroup);
		return smsGroups;
	}

	@Override
	public SmsGroup getGroupById(long groupId) throws Exception {
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(12);
		return smsGroup;
	}

	@Override
	public SmsGroup getGroupByIdAndCustomerId(long groupId, int customerId) throws Exception {
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(customerId);
		return smsGroup;
	}

	@Override
	public List<SmsGroup> findSmsGroupsByDatePeriodAndUserId(long userId, String startDate, String endDate)
			throws Exception {
		List<SmsGroup> smsGroups = new ArrayList<>();
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(userId);
		smsGroups.add(smsGroup);
		return smsGroups;
	}

	@Override
	public List<SmsGroup> findAll() throws Exception {
		List<SmsGroup> smsGroups = new ArrayList<>();
		SmsGroup smsGroup = new SmsGroup();
		smsGroup.setCustomerId(1);
		smsGroups.add(smsGroup);
		return smsGroups;
	}

}
