package lv.itsms.web.service.console;

import lv.itsms.web.page.info.ProfileInfo;
import lv.itsms.web.service.DBDAOFactory;
import lv.itsms.web.service.ProfileInfoDAO;

public class TestProfileInfoDAO implements ProfileInfoDAO {

	private TestDAOFactory factory;
			
	public TestProfileInfoDAO(TestDAOFactory factory) {
		this.factory = factory;
	}

	@Override
	public ProfileInfo getProfileInfoByLanguage(String language) throws Exception {
		
		ProfileInfo profileInfo = new ProfileInfo();
		profileInfo.setLng(language);
		return profileInfo;
	}

}
