package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DATABASE_POST 
{
	private final static String USERNAME = "dbu309sd4";
	private final static String PASSWORD = "xeft3GXR";
	private final static String URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309sd4";
	
	//Scrubs the string for any invalid characters
	private static String Scrubber(String str)
	{
		System.out.println(str);
		str.replaceAll("'", "\\\'");
		System.out.println(str);
		return str;
	}
	
	
	
	public static boolean Add_Restaurant(String Rest_ID, String Rest_Name, String Rest_Coordinate_X,  String Rest_Coordinate_Y, String Rest_Address,String Rest_Rating)
	{ 
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	           
	            String query = "INSERT INTO Restaurant ";
	            query += " VALUES ( ";
	            query += "'" + Rest_ID + "', ";
	            query += "'" + Scrubber(Rest_Name) + "', ";
	            query += "'" + Rest_Coordinate_X + "', ";
	            query += "'" + Rest_Coordinate_Y + "', ";
	            query += "'" + Rest_Address + "', ";
	            query += "'" + Rest_Rating + "'); ";
	            
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            stmt.executeUpdate(query);
	            

	            con.close();
	            return true;
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	           return false;
	      }


	}
}
