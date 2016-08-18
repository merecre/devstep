package lv.itsms.web.service;

import lv.itsms.web.page.info.SmsPanelInfo;

public interface SmsPanelInfoDAO {
	SmsPanelInfo getSmsPanelInfoByLanguage(String language) throws Exception;
}
