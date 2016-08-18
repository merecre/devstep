package lv.itsms.web.service;

import lv.itsms.web.page.info.LoginInfo;

public interface LoginInfoDAO {

	LoginInfo getLoginInfoByLanguage(String language) throws Exception;
}
