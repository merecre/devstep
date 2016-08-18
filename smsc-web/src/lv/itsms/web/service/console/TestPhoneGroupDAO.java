package lv.itsms.web.service.console;

import java.util.ArrayList;
import java.util.List;

import transfer.domain.PhoneGroup;
import transfer.service.jpa.PhoneGroupDAO;

public class TestPhoneGroupDAO implements PhoneGroupDAO {

	@Override
	public boolean insert(PhoneGroup phoneGroup) throws Exception {
		System.out.print("Insert " + phoneGroup.getPhonenumber());
		return true;
	}

	@Override
	public List<PhoneGroup> getPhonesByGroupId(long groupId) throws Exception {
		List<PhoneGroup> phoneGroups = new ArrayList<>();
		if (groupId == 20) {
			PhoneGroup phoneGroup = new PhoneGroup();
			phoneGroup.setGroupId(groupId);
			phoneGroup.setPhonenumber("371777777");
			phoneGroups.add(phoneGroup);

			phoneGroup = new PhoneGroup();
			phoneGroup.setGroupId(groupId);
			phoneGroup.setPhonenumber("3718888888");
			phoneGroups.add(phoneGroup);
		}
		return phoneGroups;
	}

	@Override
	public boolean deleteByGroupId(long groupId) throws Exception {
		System.out.print("Phone Group deleted " + groupId);
		return false;
	}

	@Override
	public PhoneGroup update(PhoneGroup smsGroup) {
		System.out.print("Phone Group is Updated:"+ smsGroup);
		return smsGroup;
	}

}
