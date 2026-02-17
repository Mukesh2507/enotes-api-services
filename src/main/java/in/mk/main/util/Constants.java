package in.mk.main.util;

public class Constants {

	public static final String Email_Regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	
	public static String mOBILE_REGEX = "^(\\+91|\\+91\\-|0)?[789]\\d{9}$";
	
	public static final String ROLE_ADMIN="hasRole('ADMIN')";
	public static final String ROLE_ADMIN_USER="hasAnyRole('USER','ADMIN')";
	public static final String ROLE_USER="hasRole('USER')";
	
	public static final String DEFAULT_PAGE_NO ="0";
	public static final String DEFAULT_PAGE_SIZE ="10";
	
	
	

}
