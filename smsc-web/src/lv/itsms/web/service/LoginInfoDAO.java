package lv.itsms.web.service;

import java.sql.SQLException;

import lv.itsms.web.page.info.LoginInfo;

public interface LoginInfoDAO {

	LoginInfo getLoginInfoByLanguage(String language) throws Exception;
}
