package lv.itsms.web.page.smspanel;

import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.page.info.ProfileInfo;
import lv.itsms.web.page.info.SmsPanelInfo;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Customer;

public class DoLoadSmsPanelPageDataCommand implements PageRequestCommand {

	CustomerPanelCommandFactory factory;

	public DoLoadSmsPanelPageDataCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	@Override
	public void execute() {

		Session session = factory.getSession();
		
		String sessionLanguage = session.getSessionLanguage();

		Repository repository = factory.getRepository();
		SmsPanelInfo smsPanelInfo = repository.getSmsPanelInfoByLanguage(sessionLanguage);

		if (smsPanelInfo!=null) {
			session.updateSessionAttribute(Session.SESSION_SMSPANELINFO_PARAMETER, smsPanelInfo);
		}
	}
}
