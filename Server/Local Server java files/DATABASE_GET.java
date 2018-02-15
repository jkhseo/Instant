package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;



public class DATABASE_GET
{
	private final static String USERNAME = "dbu309sd4";
	private final static String PASSWORD = "xeft3GXR";
	private final static String URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309sd4";
	
	
	//Variables for method
	private final static int FUZZY_SEARCH_RESULTS_MAX = 5;
	
	private JSONArray convertToJSON(ResultSet resultSet)
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
		 DATABASE_GET dbg = new DATABASE_GET();
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	            String query = "Select Rest_Name from Restaurant ";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	    
	            ResultSet rs = stmt.executeQuery(query);
	            json = dbg.convertToJSON(rs);
	            con.close(); 
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		 
		  return json.toString();


	}
	
	
	public static String getPassword(String User_Email)
	{
		 JSONArray json = null;
		 DATABASE_GET dbg = new DATABASE_GET();
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	           
	            String query = "SELECT User_Password FROM db309sd4.User WHERE User_Email = " + '"' + User_Email + '"';
	        
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            json = dbg.convertToJSON(rs);
	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  return json.toString();


	}
	
	public static String getRestaurantsInView(String max_Lat, String max_Long, String min_Lat, String min_Long)
	{
		 JSONArray json = null;
		 DATABASE_GET dbg = new DATABASE_GET();
		 try
		 {  		
		        Class.forName("com.mysql.jdbc.Driver");
		        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
	           
	            String query = "SELECT Rest_Name, Rest_Coordinate_Lat, Rest_Coordinate_Long FROM db309sd4.Restaurant WHERE Rest_Coordinate_Lat > " + min_Lat + " AND Rest_Coordinate_Lat < " + max_Lat + " AND Rest_Coordinate_Long > " + min_Long + " AND Rest_Coordinate_Long < " + max_Long;
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            json = dbg.convertToJSON(rs);
	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  return json.toString();


	}
	
	public static String getNearestRestaurants(float latitude, float longitude, String numOfRestaurants, String rangeInKm)
	{
		 JSONArray json = null;
		 DATABASE_GET dbg = new DATABASE_GET();
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
	            json = dbg.convertToJSON(rs);
	            con.close();
	          
	        }
	      catch(Exception e)
	      {
	           e.printStackTrace();
	      }
		  return json.toString();

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
	            String query = "SELECT * from Restaurant WHERE (lower(Rest_Name) LIKE '" + stringStart + "%') order by REST_NAME DESC";
	           
	            System.out.println(query);
	            Statement stmt=con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            
	            result += "{ \"Restaurants\" : [ ";
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
		public static String searchRestaurant_KeyWords(String[] keyWords)
		{
			 String result = "";
			 try
			 {  		
			        Class.forName("com.mysql.jdbc.Driver");
			        Connection con= DriverManager.getConnection(URL,USERNAME, PASSWORD);
		           
		            String query = "SELECT * From Restaurants ";
		           
		            System.out.println(query);
		            Statement stmt=con.createStatement();
		            ResultSet rs = stmt.executeQuery(query);
		         
		            ArrayList<Restaurant> tuples = new ArrayList<Restaurant>();
		           
		            while(rs.next())
		            {
		            		String name = rs.getString("Rest_Name");
		            		int id = (Integer.parseInt(rs.getString("Rest_ID")));
		            		String cuisine_main = rs.getString("Rest_Type_Cuisine_Main");;
		            		String cuisine_secondary = rs.getString("Rest_Type_Cuisine_Main");
		            		String keywordString = rs.getString("Rest_Keywords");
		            		
		            		
		            		//Careful Regex going on here. Regular Languages <3
		            		//Split the keywords based on  (Each Regex listed below)
		            		// (\\s+) - 1 or more spaces
		            		// (,+\\s*) - 1 or more comma, followed by 0 or more spaces
		            		// (_+\\s*) - 1 or more underscore, followed by 0 or more space. 
		            		String Keywords[] = keywordString.split("\\s+|,+\\s*|_+\\s*");
		            		tuples.add(new Restaurant(name, id, cuisine_main, cuisine_secondary, Keywords));
		            	
		            }
		            Restaurant_Search_Utils searcher = new Restaurant_Search_Utils(tuples);
		            result = searcher.Search(keyWords);

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

