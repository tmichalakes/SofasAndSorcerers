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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SandSConnector {
	///Constants
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	
	public static final String default_url = "jdbc:mysql://localhost:3306/";
	public static final String default_db_name = "SandS";
	public static final String default_db_user = "db_client";
	public static final String default_db_pword = "";
	
	///Class vars
	private String url;
	private String db_name;
	private String db_user;
	private String db_pword;
	
	///Connection and statement variables.
	private Connection conn;
	private Statement stmt;
	
	///Table name -> Explicitly for the purpose of connecting to specific tables.
	///Use of this variable is optional, BUT all child classes will have it.
	protected String table_name;
	
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
	
	public void ConnectToDB(){
		conn = null;
		///stmt only necessary for debugging.
		stmt = null;
		try{
			///Register the driver.
			Class.forName(JDBC_DRIVER);
			
			System.out.println("Establishing Connection...");
			conn = DriverManager.getConnection(url, db_user, db_pword);
			stmt = conn.createStatement();
			
			System.out.println("Connection Established");
			
		}catch(SQLException e){
			System.out.println("Unable to establish connection.");
			System.out.println("Check sql instance and retry");
			e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to locate JDBC Driver");
			System.err.println("Please ensure build path correctly configured.");
			System.exit(1);
		}
	}
	
	public void CloseStatement(){
		try {
			stmt.close();
		} catch (SQLException e) {
			System.err.println("Unable to close statement");
			System.exit(1);
		}
	}
	
	public void CloseConnection(){
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Unable to close connection");
			System.exit(1);
		}
	}
	
	public static void main(String [] args){
		SandSConnector c = new SandSConnector(true);
		c.ConnectToDB();
		c.CloseConnection();
		
	}
	
}
