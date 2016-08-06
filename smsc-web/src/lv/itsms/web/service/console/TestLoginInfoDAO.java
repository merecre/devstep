package lv.itsms.web.service.console;

import lv.itsms.web.page.info.LoginInfo;
import lv.itsms.web.service.LoginInfoDAO;

public class TestLoginInfoDAO implements LoginInfoDAO {

	@Override
	public LoginInfo getLoginInfoByLanguage(String language) throws Exception {
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setTxtHeader("Header");
		return loginInfo;
	}

}
