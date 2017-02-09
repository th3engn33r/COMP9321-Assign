package common;
import java.sql.*;

public class DBConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			Connection myconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amazon","root", "Mysql");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
