package lv.itsms.web.service;

import java.util.List;

import transfer.domain.PhoneGroup;

public interface PhoneGroupDAO {
	boolean insert(PhoneGroup phoneGroup) throws Exception;
	List<PhoneGroup> getPhonesByGroupId(long groupId) throws Exception;
	boolean deleteByGroupId(int groupId) throws Exception;
}
