package backend;

/*!
 * @author Ted Michalakes
 * @email tmichalakes@gmail.com
 * 
 * SandSConnector
 * 	-Base connector for the Sofas And Sorcerers database.
 *  -Handles all SQL queries associated with SandS backend
 *  -Handles query cleaning.
 *  -Uses JDBC
 */
public class SandSConnector {
	///Constants
	public static final String default_url = "localhost";
	public static final String default_db_name = "SandS";
	public static final String default_db_user = "db_client";
	public static final String default_db_pword = "beep";
	
	///Class vars
	private String url;
	private String db_name;
	private String db_user;
	private String db_pword;
	
	
	public SandSConnector(){
		initialize();
	}
	
	public SandSConnector(boolean use_defaults){
		initialize();
		if(use_defaults){
			url = default_url;
			db_name = default_db_name;
			db_user = default_db_user;
			db_pword = default_db_pword;
		}
	}
	
	public void initialize(){
		url = new String();
		db_name = new String();
		db_user = new String();
		db_pword = new String();
	}
	
	
	public static void main(String [] args){
		System.out.println("beep");
	}
	
}
