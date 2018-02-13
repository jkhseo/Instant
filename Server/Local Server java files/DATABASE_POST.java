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
		//System.out.println(str);
		str.replaceAll("'", "\\\'");
		//System.out.println(str);
		return str;
	}
	
			//Adds an order to the database. 
			public static boolean Add_Order(String Rest_ID, String User_ID, String Food,  String Order_Data_Submitted, String Order_Data_Complied)
			{ 
				 try
				 {  		
				        Class.forName("com.mysql.jdbc.Driver");
				        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
			           
			            String query = "INSERT INTO Food ";
			            query += " VALUES ( ";
			            query += "'" + Rest_ID + "', ";
			            query += "'" + User_ID + "', ";
			            query += "'" + Food + "', ";
			            query += "'" + Order_Data_Submitted + "', ";
			            query += "'" + Order_Data_Complied + "'); ";
			            
			           
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
	
		//Adds a Food to the base. 
		public static boolean Add_Food(String Rest_ID, String Food_Name, String Food_Price,  String Food_Desc, String Menu_ID,String Food_Tags_Main, String Food_Tags_Secondary)
		{ 
			 try
			 {  		
			        Class.forName("com.mysql.jdbc.Driver");
			        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		           
		            String query = "INSERT INTO Food ";
		            query += " VALUES ( ";
		            query += "'" + Rest_ID + "', ";
		            query += "'" + Food_Name + "', ";
		            query += "'" + Food_Price + "', ";
		            query += "'" + Food_Desc + "', ";
		            query += "'" + Menu_ID + "', ";
		            query += "'" + Food_Tags_Main + "', ";
		            query += "'" + Food_Tags_Secondary + "'); ";
		            
		           
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
	
	
	//Adds a User to the database. 
	public static boolean Add_User(String User_ID, String User_Type, String First_Name,  String Last_Name, String User_Address,String User_Birthday, String User_Email, String User_Password)
	{ 
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	           
	            String query = "INSERT INTO User ";
	            query += " VALUES ( ";
	            query += "'" + User_ID + "', ";
	            query += "'" + User_Type + "', ";
	            query += "'" + First_Name + "', ";
	            query += "'" + Last_Name + "', ";
	            query += "'" + User_Address + "', ";
	            query += "'" + User_Birthday + "', ";
	            query += "'" + User_Email + "', ";
	            query += "'" + User_Password + "'); ";
	            
	           
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
	
	//Adds a Restaurant to the database
	//Written by Adam de Gala. Feel free to yell at me. 
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
