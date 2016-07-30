package lv.itsms.web.utils;

public class Utils {
	
	public static boolean isNumeric(String s) {  
		return s!= null && s.matches("[-+]?\\d*\\.?\\d+");  
	} 
}
