package lv.itsms.web.service;

import lv.itsms.web.page.info.ProfileInfo;

public interface ProfileInfoDAO {

	ProfileInfo getProfileInfoByLanguage(String language) throws Exception;

}
