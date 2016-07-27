package lv.itsms.web.service;

import java.util.List;

import transfer.domain.SmsGroup;

public interface SmsGroupDAO {
	void save(SmsGroup smsGroup) throws Exception;
	boolean deleteByUserIdAndGroupName(int userId, String groupName) throws Exception;
	boolean deleteByGroupId(int groupId) throws Exception;
	boolean insert(SmsGroup smsGroup) throws Exception;
	boolean update(SmsGroup smsGroup) throws Exception;
	int getIdByUserIdAndGroupName(SmsGroup smsGroup) throws Exception;
	List<SmsGroup> getGroupsByUserId(long userId) throws Exception;
	SmsGroup getGroupById(long groupId) throws Exception;

}
