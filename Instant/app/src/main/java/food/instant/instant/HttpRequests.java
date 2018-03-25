package food.instant.instant;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

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
    public static void GoogleMapsGET(String url, final Handler handler){
        OkHttpClient client = new OkHttpClient();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message msg = handler.obtainMessage();
                msg.what = GlobalConstants.GOOGLE_MAPS_DISTANCES;
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    msg.obj = responseObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        });
    }
    public static void HttpGET(String path, final Handler handler) {
        String url = "http://proj-309-sd-4.cs.iastate.edu:8080/demo/"+path;
        String localurl = "http://10.26.12.74:8080/demo/"+path;
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
                   String temp = response.body().string();
                   if(temp!="") {
                       Message msg = handler.obtainMessage();
                       responseObject = new JSONObject(temp);
                       if (responseObject.length() == 0)
                           msg.what = GlobalConstants.EMPTY_JSON;
                       if (responseObject.has("Restaurant_Search_Results"))
                           msg.what = GlobalConstants.RESTAURANT_SEARCH_CODE;
                       if (responseObject.has("Fuzzy_Search_Results"))
                           msg.what = GlobalConstants.FUZZY_SEARCH_CODE;
                       if(responseObject.has("Get_Password"))
                           msg.what = GlobalConstants.PASSWORD;
                       if(responseObject.has("All_User_Info"))
                           msg.what = GlobalConstants.USERINFO;
                       if(responseObject.has("Restaurant_From_OwnerUserEmail"))
                           msg.what = GlobalConstants.USER_RESTAURANTS;
                       if(responseObject.has("All_Pending_Orders"))
                           msg.what = GlobalConstants.ORDERS;
                       if(responseObject.has("Confirmation_Code"))
                           msg.what = GlobalConstants.QRCODE;
                       if(responseObject.has("Restaurant_Name"))
                           msg.what = GlobalConstants.RESTAURANT_INFO;
                       //restaurantsearchresults
                       msg.obj = responseObject;
                       handler.sendMessage(msg);
                   }

                } catch (JSONException e) {
                  e.printStackTrace();
               }
            }
        });
    }
    public static void HttpPost(String path, final Handler handler){
        String url = "http://proj-309-sd-4.cs.iastate.edu:8080/demo/"+path;
        //String url = "http://10.26.12.74:8080/demo/"+path;
        OkHttpClient client = new OkHttpClient();
        //success tag, true or false value

        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    Message msg = handler.obtainMessage();
                    msg.what = GlobalConstants.ORDER_SUBMISSION_RESPONSE;
                    JSONObject responseObject = new JSONObject(response.body().string());
                    msg.obj = responseObject;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
