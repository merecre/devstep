package lv.itsms.web.service;

import java.sql.SQLException;

import lv.itsms.web.page.info.RegistrationInfo;

public interface RegistrationInfoDAO {
	RegistrationInfo getInfoByLanguage(String language) throws Exception;
}
