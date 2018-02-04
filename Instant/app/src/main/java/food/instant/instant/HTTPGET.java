package food.instant.instant;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by mpauk on 1/30/2018.
 */

public class HTTPGET {


    public static ArrayList<Restaurant> HTTPGetRestaurants(String query){
        ArrayList<Restaurant> queryResults = new ArrayList<Restaurant>();
        try {
            String url = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309sd4";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "dbu309sd4", "xeft3GXR");
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                int id = rs.getInt("Rest_ID");
                String restname = rs.getString("Rest_Name");
                float latitude = Float.parseFloat(rs.getString("Rest_Coordinate_X"));
                float longitude = Float.parseFloat(rs.getString("Rest_Coordinate_Y"));
                String address = rs.getString("Rest_Address");
                int rating = Integer.parseInt(rs.getString("Rest_Rating"));
                queryResults.add(new Restaurant(id,restname,latitude,longitude,address,rating));
            }
            st.close();
        }
        catch (Exception e)
        {
            System.out.println("Querying Error");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return queryResults;
    }
}

   /* @Override
    protected Restaurant doInBackground(Void... voids) {
        try {
            final String url = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309sd4";
            Class.forName("com.mysql.jdbc.Driver");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Restaurant greeting = restTemplate.getForObject(url,Restaurant.class);
            return greeting;
        }catch(Exception e){
            Log.e("UserTemplate",e.getMessage(),e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Restaurant greeting){
        //System.out.println(greeting.getContent());
        System.out.println(greeting.getRest_Name());
    }*/

