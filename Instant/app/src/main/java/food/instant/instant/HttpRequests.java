package food.instant.instant;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mpauk on 2/7/2018.
 */

public class HttpRequests {
    public static void HttpGET(String path, final Handler handler) {
        String url = "http://proj-309-sd-4.cs.iastate.edu:8080/demo/"+path;
        OkHttpClient client = new OkHttpClient();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
               JSONObject responseObject = null;
               try {

                   responseObject = new JSONObject(response.body().string());
                   Message msg = handler.obtainMessage();
                   msg.what = 99;
                   msg.obj = responseObject;
                   handler.sendMessage(msg);

                } catch (JSONException e) {
                  e.printStackTrace();
               }
            }
        });
    }
    public static void HttpPostRestaurant(String url,String json){
        OkHttpClient client = new OkHttpClient();
        //success tag, true or false value
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody postRequest = RequestBody.create(JSON,json);
        /*RequestBody postRequest = new FormEncodingBuilder()
                .add("Rest_ID","9")
                .add("Rest_Name","Wendys")
                .add("Rest_Address","123")
                .add("Rest_Coordinate_X","1.2")
                .add("Rest_Coordinate_Y","1")
                .add("Rest_Rating","4")
                .build();*/
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).post(postRequest).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    System.out.println(responseObject.get("Success"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
