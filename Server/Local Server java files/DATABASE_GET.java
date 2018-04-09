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
	
	/**
	 * 
	 * @return Returns all information of every restaurant in the database. 
	 */
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
	
	/**
	 * 
	 * @param Order_ID The ID of the order's status being checked. 
	 * @param Rest_ID The ID of the restaurant that the order is being ordered. 
	 * @return Returns the status of the order: cancelled, confirmed, pending, complete. 
	 */
	
	public static String getOrderStatus(String Order_ID, String Rest_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	            String query = "SELECT Order_Status FROM db309sd4.`Order` WHERE Rest_ID = "+ Rest_ID + " AND Order_ID = " +  Order_ID;
	           
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
		 
		  return " {\"Order_Status\":" + json.toString() +  "}";


	}
	
	/**
	 * 
	 * @param Order_ID The ID of the order that is being retrieved for the QR/confirmation code. 
	 * @return Returns the QR/confirmation code of the order. 
	 */
	
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
		 
		  return " {\"Confirmation_Code\":" + json.toString() +  "}";


	}
	
	/**
	 * 
	 * @param Rest_ID The ID of the restaurant that is used to query the database. 
	 * @return Returns every information of the restaurant with the ID same with Rest_ID. 
	 */
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
		 
		  return " {\"Restaurant_Name\":" + json.toString() +  "}";


	}
	
	/**
	 * 
	 * @param User_Email The email of the restaurant owner.
	 * @return Returns all restaurants that is owned by the user with User_Email.
	 */
	
	public static String getRestaurantFromOwnerUserID(String User_ID)
	{
		 JSONArray json = null;
		
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM db309sd4.Restaurant WHERE User_ID = " + '"' + User_ID + '"';
	           
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
	
	
	
	/**
	 * 
	 * @param User_Email The email of a user being retrieved. 
	 * @return Returns every information of the user with email = User_Email. 
	 */
	
	
	public static String getAllUserInfo(String User_Email)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT * FROM db309sd4.User WHERE User_Email = " + '"' + User_Email + '"';
	           
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
	
	/** 
	 * 
	 * @param Restaurant_ID The ID of the restaurant that is being retrieved for the menu.
	 * @return Returns the menu of the restaurant where the ID = Restaurant_ID. 
	 */
	
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
	
	/**
	 * 
	 * @param Restaurant_ID The ID of the restaurant being retrieved. 
	 * @return Returns all pending orders where the order's restaurant ID = Restaurant_ID. 
	 */
	
	public static String getPendingOrderForRestaurant(String Restaurant_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT db309sd4.Order.Order_ID, db309sd4.Order.Order_Status, db309sd4.Order.DummyPK, db309sd4.Order.Comments,"
		        		+ " db309sd4.Order.Order_Confirmation_Code, db309sd4.Order.Quantity, db309sd4.Order.Order_Date_Submitted, db309sd4.Order.Order_Date_Pick_Up, \n" + 
		        		"db309sd4.Restaurant.Rest_Name, db309sd4.Restaurant.Rest_ID, db309sd4.Food.Food_Name, db309sd4.Food.Food_ID, db309sd4.Food.Food_Price,"
		        		+ " db309sd4.Food.Food_Desc, db309sd4.Food.Menu_ID, db309sd4.Food.Food_Tags_Main, db309sd4.Food.Food_Tags_Secondary,  db309sd4.User.User_Email,"
		        		+ "  db309sd4.User.First_Name,  db309sd4.User.Last_Name, db309sd4.User.User_ID\n" + 
		        		" FROM (((db309sd4.Order JOIN db309sd4.Food) JOIN db309sd4.Restaurant) JOIN db309sd4.User) WHERE db309sd4.Order.Order_Status ="
		        		+ " \"Pending\" AND db309sd4.Order.Rest_ID = "+ '"' + Restaurant_ID + '"' + 
		        		"AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID AND db309sd4.Restaurant.Rest_ID = db309sd4.Food.Rest_ID AND db309sd4.Order.User_ID = db309sd4.User.User_ID";


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
	
	/**
	 * 
	 * @param Restaurant_ID The ID of the restaurant being retrieved. 
	 * @return Returns all confirmed orders where the order's restaurant ID = Restaurant_ID. 
	 */
	
	public static String getConfirmedOrderForRestaurant(String Restaurant_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT db309sd4.Order.Order_ID, db309sd4.Order.Order_Status, db309sd4.Order.DummyPK, db309sd4.Order.Comments,"
		        		+ " db309sd4.Order.Order_Confirmation_Code, db309sd4.Order.Quantity, db309sd4.Order.Order_Date_Submitted, db309sd4.Order.Order_Date_Pick_Up, \n" + 
		        		"db309sd4.Restaurant.Rest_Name, db309sd4.Restaurant.Rest_ID, db309sd4.Food.Food_Name, db309sd4.Food.Food_ID, db309sd4.Food.Food_Price,"
		        		+ " db309sd4.Food.Food_Desc, db309sd4.Food.Menu_ID, db309sd4.Food.Food_Tags_Main, db309sd4.Food.Food_Tags_Secondary,  db309sd4.User.User_Email,"
		        		+ "  db309sd4.User.First_Name,  db309sd4.User.Last_Name, db309sd4.User.User_ID\n" + 
		        		" FROM (((db309sd4.Order JOIN db309sd4.Food) JOIN db309sd4.Restaurant) JOIN db309sd4.User) WHERE db309sd4.Order.Order_Status ="
		        		+ " \"Confirmed\" AND db309sd4.Order.Rest_ID = "+ '"' + Restaurant_ID + '"' + 
		        		"AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID AND db309sd4.Restaurant.Rest_ID = db309sd4.Food.Rest_ID AND db309sd4.Order.User_ID = db309sd4.User.User_ID";


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
		 
		  return " {\"All_Confirmed_Orders\":" + json.toString() +  "}";


	}
	
	public static String getCancelledOrderForRestaurant(String Restaurant_ID)
	{
		 JSONArray json = null;
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        String query = "SELECT db309sd4.Order.Order_ID, db309sd4.Order.Order_Status, db309sd4.Order.DummyPK, db309sd4.Order.Comments,"
		        		+ " db309sd4.Order.Order_Confirmation_Code, db309sd4.Order.Quantity, db309sd4.Order.Order_Date_Submitted, db309sd4.Order.Order_Date_Pick_Up, \n" + 
		        		"db309sd4.Restaurant.Rest_Name, db309sd4.Restaurant.Rest_ID, db309sd4.Food.Food_Name, db309sd4.Food.Food_ID, db309sd4.Food.Food_Price,"
		        		+ " db309sd4.Food.Food_Desc, db309sd4.Food.Menu_ID, db309sd4.Food.Food_Tags_Main, db309sd4.Food.Food_Tags_Secondary,  db309sd4.User.User_Email,"
		        		+ "  db309sd4.User.First_Name,  db309sd4.User.Last_Name, db309sd4.User.User_ID\n" + 
		        		" FROM (((db309sd4.Order JOIN db309sd4.Food) JOIN db309sd4.Restaurant) JOIN db309sd4.User) WHERE db309sd4.Order.Order_Status ="
		        		+ " \"Cancelled\" AND db309sd4.Order.Rest_ID = "+ '"' + Restaurant_ID + '"' + 
		        		"AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID AND db309sd4.Restaurant.Rest_ID = db309sd4.Food.Rest_ID AND db309sd4.Order.User_ID = db309sd4.User.User_ID";
	           
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
		        String query = "SELECT db309sd4.Order.Order_ID, db309sd4.Order.Order_Status, db309sd4.Order.DummyPK, db309sd4.Order.Comments,"
		        		+ " db309sd4.Order.Order_Confirmation_Code, db309sd4.Order.Quantity, db309sd4.Order.Order_Date_Submitted, db309sd4.Order.Order_Date_Pick_Up, \n" + 
		        		"db309sd4.Restaurant.Rest_Name, db309sd4.Restaurant.Rest_ID, db309sd4.Food.Food_Name, db309sd4.Food.Food_ID, db309sd4.Food.Food_Price,"
		        		+ " db309sd4.Food.Food_Desc, db309sd4.Food.Menu_ID, db309sd4.Food.Food_Tags_Main, db309sd4.Food.Food_Tags_Secondary,  db309sd4.User.User_Email,"
		        		+ "  db309sd4.User.First_Name,  db309sd4.User.Last_Name, db309sd4.User.User_ID\n" + 
		        		" FROM (((db309sd4.Order JOIN db309sd4.Food) JOIN db309sd4.Restaurant) JOIN db309sd4.User) WHERE db309sd4.Order.Order_Status ="
		        		+ " \"Completed\" AND db309sd4.Order.Rest_ID = "+ '"' + Restaurant_ID + '"' + 
		        		"AND db309sd4.Order.Food_ID = db309sd4.Food.Food_ID AND db309sd4.Restaurant.Rest_ID = db309sd4.Food.Rest_ID AND db309sd4.Order.User_ID = db309sd4.User.User_ID";
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

	//A non JSON returning function that gets the next food number for a restaurant
	//Written by Adam de Gala. 
	public static int getNextFoodID(String rest_ID)
	{
		
		int result = 1;
		try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		        
		        
	            String query = "SELECT Food_ID from db309sd4.Food WHERE Rest_ID = '" + rest_ID + "' order by Food_ID DESC";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            

	            if(rs.next())
	            {
	            		result = Integer.parseInt(rs.getString("Food_ID"));
	            		result++;
	            }

	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  System.out.println("Next ID is " + result);
		  return result;
	
	}
	
		//A non JSON returning function that gets the next order number for a restaurant
		//Written by Adam de Gala. 
		public static int getNextOrderID(String rest_ID)
		{
			
			int result = 1;
			try
			 {  		
			        Class.forName("com.mysql.jdbc.Driver");
			        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
			        
			        
		            String query = "SELECT Order_ID from db309sd4.Order WHERE Rest_ID = '" + rest_ID + "' order by Order_ID DESC";
		           
		            System.out.println(query);
		            Statement stmt=con.createStatement();
		            ResultSet rs = stmt.executeQuery(query);
		            

		            if(rs.next())
		            {
		            		result = Integer.parseInt(rs.getString("Order_ID"));
		            		result++;
		            }

		            con.close();
		          
		        }
		      catch(Exception e)
		      {
		           e.printStackTrace();
		      }
			  System.out.println("Next ID is " + result);
			  return result;
			


		}
	
	
		//A non JSON returning function that gets the next order number for a restaurant
		//Written by Adam de Gala. 
		public static String getRSAKEY()
		{			
			String result = "";
			try
			 {  		
			        Class.forName("com.mysql.jdbc.Driver");			  
			        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);

			        String query = "SELECT * from db309sd4.Server_Keys order by Version DESC ";
			           
			        System.out.println(query);
			        
			        Statement stmt=con.createStatement();
			        ResultSet rs = stmt.executeQuery(query);
			            
		            if(rs.next())
		            {
		            		String publicKey = rs.getString("Public_Key");
		            		String version = rs.getString("Version");
		            		String Encyption_Exponet = rs.getString("Encyption_Exponet");
		            		
		            		result = "{ \"Public_Key\" : \"" + publicKey + "\",";
		            		result += " \"Version\" : \"" + version + "\",";
		            		result += " \"Encyption_Exponet\" : \"" + Encyption_Exponet + "\"}";
		            		
		            		
		            }
		            else
		            {
		            		
		            }

		            con.close();
		          
		        }
		      catch(Exception e)
		      {
		           e.printStackTrace();
		      }
			  System.out.println(result);
			  return result;

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
		            
		           result = searcher.SearchSpellCheck(searching_Keywords_Array);
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


