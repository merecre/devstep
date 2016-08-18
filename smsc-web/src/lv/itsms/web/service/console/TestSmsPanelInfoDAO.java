package lv.itsms.web.service.console;

import lv.itsms.web.page.info.SmsPanelInfo;
import lv.itsms.web.service.SmsPanelInfoDAO;

public class TestSmsPanelInfoDAO implements SmsPanelInfoDAO {

	@Override
	public SmsPanelInfo getSmsPanelInfoByLanguage(String language) throws Exception {
		SmsPanelInfo smsPanelInfo = new SmsPanelInfo();
		return smsPanelInfo;
	}

}
