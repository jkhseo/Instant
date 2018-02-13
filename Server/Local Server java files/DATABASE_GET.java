package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DATABASE_GET
{
	private final static String USERNAME = "dbu309sd4";
	private final static String PASSWORD = "xeft3GXR";
	private final static String URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309sd4";
	


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
	
}
