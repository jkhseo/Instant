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

public class HTTPGET extends AsyncTask<Void, Void, Restaurant>{


    @Override
    protected Restaurant doInBackground(Void... voids)  {
        try {
            final String url = "localhost:8080";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Restaurant restaurant = restTemplate.getForObject(url, Restaurant.class);
            return restaurant;
        } catch (Exception e) {
            Log.e("UserTemplate", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Restaurant res) {
        if(res!=null) {
            System.out.println(res.getName());
        }
    }
}
