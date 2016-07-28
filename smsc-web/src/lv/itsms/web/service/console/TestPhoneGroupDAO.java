package lv.itsms.web.service.console;

import java.util.List;

import lv.itsms.web.service.PhoneGroupDAO;
import transfer.domain.PhoneGroup;

public class TestPhoneGroupDAO implements PhoneGroupDAO {

	@Override
	public boolean insert(PhoneGroup phoneGroup) throws Exception {
		System.out.print("Insert " + phoneGroup.getPhonenumber());
		return true;
	}

	@Override
	public List<PhoneGroup> getPhonesByGroupId(long groupId) throws Exception {
		List<PhoneGroup> phoneGroups = null;
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
	public boolean deleteByGroupId(int groupId) throws Exception {
		System.out.print("Phone Group deleted " + groupId);
		return false;
	}

}
