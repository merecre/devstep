package lv.itsms.web.service;

import lv.itsms.web.page.info.RegistrationInfo;

public interface RegistrationInfoDAO {
	RegistrationInfo getInfoByLanguage(String language) throws Exception;
}
