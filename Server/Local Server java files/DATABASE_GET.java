package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DATABASE_GET
{
	private final static String USERNAME = "dbu309sd4";
	private final static String PASSWORD = "xeft3GXR";
	private final static String URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309sd4";
	private static final int FUZZY_SEARCH_RESULTS_MAX = 5;
	


	private static JSONArray convertToJSON(ResultSet resultSet)
	        throws Exception {
	    JSONArray jsonArray = new JSONArray();

	    while (resultSet.next()) {
	        int total_rows = resultSet.getMetaData().getColumnCount();
	        JSONObject obj = new JSONObject();
	        for (int i = 0; i < total_rows; i++) {
	            obj.put(resultSet.getMetaData().getColumnLabel(i+1), resultSet.getObject(i + 1));
	        }
	        jsonArray.put(obj);
	    }

	    return jsonArray;
	}
	
	
	public static String getAllRestaurant()
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	            String query = "Select Rest_Name from Restaurant ";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_Restaurant\":" + json.toString() +  "}";


	}
	
	public static String getConfirmationCode(String Order_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	            String query = "SELECT Order_Confirmation_Code FROM db309sd4.Order WHERE Order_ID= " + Order_ID; 
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_Restaurant\":" + json.toString() +  "}";


	}
	
	public static String getRestaurantFromID(String Rest_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	            String query = "SELECT * FROM db309sd4.Restaurant WHERE Rest_ID= " + Rest_ID; 
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_Restaurant\":" + json.toString() +  "}";


	}
	
	
	public static String getRestaurantFromOwnerUserEmail(String User_Email)
	{
		 JSONArray json = null;
		
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM db309sd4.Restaurant WHERE User_Email = " + '"' + User_Email + '"';
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"Restaurant_From_OwnerUserEmail\":" + json.toString() +  "}";


	}
	
	
	
	
	
	
	public static String getAllUserInfo(String email)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM db309sd4.User WHERE User_Email = " + '"' + email + '"';
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_User_Info\":" + json.toString() +  "}";


	}
	
	public static String getMenu(String Restaurant_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM db309sd4.Food WHERE Rest_ID = " + '"' + Restaurant_ID + '"';
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"Menu\":" + json.toString() +  "}";


	}
	
	public static String getPendingOrderForRestaurant(String Restaurant_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM (db309sd4.Order JOIN db309sd4.Food) WHERE db309sd4.Order.Order_Status ="
		        		+ " \"Pending\" AND db309sd4.Order.Rest_ID = "+ '"' + Restaurant_ID + '"' + 
		        		"AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID";


	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_Pending_Orders\":" + json.toString() +  "}";


	}
	
	public static String getCancelledOrderForRestaurant(String Restaurant_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM (db309sd4.Order JOIN db309sd4.Food) WHERE db309sd4.Order.Order_Status ="
		        		+ " \"Cancelled\" AND db309sd4.Order.Rest_ID = "+ '"' + Restaurant_ID + '"' + 
		        		"AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_Canceled_Orders\":" + json.toString() +  "}";


	}
	
	public static String getCompletedOrderForRestaurant(String Restaurant_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM (db309sd4.Order JOIN db309sd4.Food) WHERE db309sd4.Order.Order_Status ="
		        		+ " \"Completed\" AND db309sd4.Order.Rest_ID = "+ '"' + Restaurant_ID + '"' + 
		        		"AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_Completed_Orders\":" + json.toString() +  "}";


	}
	
	public static String getAllOrdersForUser(String User_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT db309sd4.Order.Rest_ID, Rest_Name, db309sd4.Order.Food_ID, Quantity, Food_Name, Food_Price, Comments, Order_Status, Order_ID, Order_Date_Pick_Up "
		        		+ "FROM (db309sd4.Order JOIN db309sd4.Food JOIN db309sd4.Restaurant) "
		        		+ "WHERE db309sd4.Order.User_ID = " + User_ID + " AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID "
		        		+ "AND db309sd4.Order.Rest_ID = db309sd4.Restaurant.Rest_ID";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return " {\"All_Orders_For_User\":" + json.toString() +  "}";


	}	
	
	public static String getPassword(String User_Email)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	           
	            String query = "SELECT User_Password FROM db309sd4.User WHERE User_Email = " + '"' + User_Email + '"';
	        
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  return " {\"Get_Password\":" + json.toString() + "}";


	}
	
	
	public static String getRestaurantsInView(String max_Lat, String max_Long, String min_Lat, String min_Long)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	           
	            String query = "SELECT * FROM db309sd4.Restaurant WHERE Rest_Coordinate_Lat > " + min_Lat + " AND Rest_Coordinate_Lat < " + max_Lat + " AND Rest_Coordinate_Long > " + min_Long + " AND Rest_Coordinate_Long < " + max_Long;
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  return " {\"Restaurant_In_View\":" + json.toString() + "}";


	}
	
	public static String getNearestRestaurants(float latitude, float longitude, String numOfRestaurants, String rangeInKm)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	           
		        



		        
		        
		        
	            String query = "		        SELECT * FROM(SELECT z.Rest_Name,\n" + 
	            		"		        p.distance_unit\n" + 
	            		"		                 * DEGREES(ACOS(COS(RADIANS(p.latpoint))\n" + 
	            		"		                 * COS(RADIANS(Rest_Coordinate_Lat))\n" + 
	            		"		                 * COS(RADIANS(p.longpoint) - RADIANS(Rest_Coordinate_Long))\n" + 
	            		"		                 + SIN(RADIANS(p.latpoint))\n" + 
	            		"		                 * SIN(RADIANS(Rest_Coordinate_Lat)))) AS distance_in_km\n" + 
	            		"		  FROM db309sd4.Restaurant AS z\n" + 
	            		"		  JOIN ( \n" + 
	            		"		        SELECT " + latitude + " AS latpoint, " + longitude + " AS longpoint,\n" + 
	            		"		                .01 AS radius,      111.045 AS distance_unit\n" + 
	            		"		    ) AS p ON 1=1\n" + 
	            		"		  ORDER BY distance_in_km\n" + 
	            		"		  LIMIT " + numOfRestaurants +")" + "AS final WHERE distance_in_km < " + rangeInKm ;
	        
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            json = convertToJSON(rs);
	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  return " {\"Nearest_Restaurants\":" + json.toString() + "}";

	}
	
	
	//Method to FuzzySearch for Restaurant 
	//Written by Adam de Gala
	public static String fuzzySearchRestaurant(String stringStart)
	{
		 String result = "";
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        
		        
	            stringStart = stringStart.toLowerCase();
	            String query = "SELECT * from Restaurant WHERE (lower(Rest_Name) LIKE '" + stringStart + "%') order by REST_NAME ";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            
	            result += "{ \"Fuzzy_Search_Results\" : [ ";
	            int count = 0;
	            while(rs.next() && count < FUZZY_SEARCH_RESULTS_MAX)
	            {
	            		result += "{ ";
	            		result += " \"Rest_Name\": \"" + rs.getString("Rest_Name") + "\"";
	            		result += "},";
	            		count++;
	            }
	            result = result.substring(0, result.length()-1);
	            result += " ] }";

	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  System.out.println(result);
		  return result;
	}
	
		//Method to Search for Restaurant
		//Written by Adam de Gala. 
		public static String searchRestaurant_KeyWords(String searching_Keywords)
		{
			 String result = "";
			 try
			 {  		
			        Class.forName("com.mysql.jdbc.Driver");
			        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		           
		            String query = "SELECT * From Restaurant ";
		           
		            System.out.println(query);
		            Statement stmt=con.createStatement();
		            ResultSet rs = stmt.executeQuery(query);
		         
		            ArrayList<Restaurant> tuples = new ArrayList<Restaurant>();
		           
		            while(rs.next())
		            {
		            		String name = rs.getString("Rest_Name");
		            		int id = (Integer.parseInt(rs.getString("Rest_ID")));
		            		String cuisine_main = rs.getString("Rest_Type_Cuisine_Main");;
		            		String cuisine_secondary = rs.getString("Rest_Type_Cuisine_Secondary");
		            		String keywordString = rs.getString("Rest_Keywords");
		            		String Rest_Address = rs.getString("Rest_Address");
		            		String Rest_Coordinate_Lat = rs.getString("Rest_Coordinate_Lat");
		            		String Rest_Coordinate_Long = rs.getString("Rest_Coordinate_Long");
		            		
		            		//Careful Regex going on here. Regular Languages <3
		            		//Split the keywords based on  (Each Regex listed below)
		            		// (\\s+) - 1 or more spaces
		            		// (,+\\s*) - 1 or more comma, followed by 0 or more spaces
		            		// (_+\\s*) - 1 or more underscore, followed by 0 or more space. 
		            		String Keywords[] = {};
		            		if(keywordString != null && keywordString.length() > 0)
		            			Keywords = keywordString.split("\\s+|,+\\s*|_+\\s*");
		            		tuples.add(new Restaurant(name, id, cuisine_main, cuisine_secondary,Rest_Address, Rest_Coordinate_Lat,Rest_Coordinate_Long,  Keywords));
		            	
		            }
		            
		            System.out.println("Tuples num : " + tuples.size());
		            Restaurant_Search_Utils searcher = new Restaurant_Search_Utils(tuples);
		            
		            //Follows the same rules as above for Regex.
		            String[] searching_Keywords_Array = searching_Keywords.split("\\s+|,+\\s*|_+\\s*");
		            
		            result = searcher.Search(searching_Keywords_Array);

		            con.close();
		          
		        }
		      catch(Exception e)
		      {
		           e.printStackTrace();
		      }
			  System.out.println(result);
			  return result;
		}
	
}


