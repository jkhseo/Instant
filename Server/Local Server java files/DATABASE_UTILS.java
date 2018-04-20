package hello;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DATABASE_UTILS 
{
	private final static String USERNAME = "dbu309sd4";
	private final static String PASSWORD = "xeft3GXR";
	private final static String URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309sd4";
	
	/**
	 * 
	 * @param User_Email The User to be logged in. 
	 * @param Password The password
	 * @return
	 */
	public static boolean Verfiy_Login(String User_Email, String  Password)
	{ 
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);

	            String query = "Select User_Password from db309sd4.User  "; 
	            query += " Where User_Email  = '" + User_Email + "'";

	           
	            System.out.println(query);
	            
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            
	            if(rs.next())
	            {
	            		if(rs.getString("User_Password").equals(Password))
	            		{
	            			return true;
	            		}
	            		return false;
	            }

	            con.close();
	            return false;
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	           return false;
	      }

	}
	
	public static String StringToInt(String message)
	{
		String integer = new BigInteger(message.getBytes()).toString();
		return integer;
	}
	
	public static String IntToString(BigInteger integer)
	{
		String message = new String(integer.toByteArray());
		return message;
	}
}
