package food.instant.instant;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mpauk on 2/7/2018.
 */

public class HttpRequests {
    public static void HttpGET(String url) {
        OkHttpClient client = new OkHttpClient();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                JSONObject object = null;
                try {
                    object = new JSONObject(response.body().string());
                    System.out.println(object.get("content"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
