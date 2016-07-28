package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class LanguageRequestParameter extends UserPageRequest {

	public static String DEFAULT_LANGUAGE = "lv";

	public static String[] ALLOWED_LANGUAGES = {"lv", "ru", "en"};

	public final static String LN_PARAMETER = "ln"; 

	public LanguageRequestParameter() {
		super(LN_PARAMETER);
	}

	public void update(HttpServletRequest request) {
		String userLanguage = request.getParameter(parameterKey);

		if (userLanguage!=null) { 
			//if (request.getParameter(parameterKey)!=null) {
			//userLanguage = request.getParameter(parameterKey);
			if (!isValidLanguage(userLanguage)) {
				userLanguage = DEFAULT_LANGUAGE;
			}
		}	

		parameterValue = userLanguage;
	}

	private boolean isValidLanguage(String userLanguage) {	
		for (String allowedLanguage : ALLOWED_LANGUAGES) {
			if (userLanguage.equals(allowedLanguage)) {
				return true;
			}
		}
		return false;
	}
}
