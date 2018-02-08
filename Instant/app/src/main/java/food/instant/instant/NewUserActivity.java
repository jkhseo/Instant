package food.instant.instant;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class NewUserActivity extends AppCompatActivity {

    private static final String TAG = "NewUserActivity";

    private Context c;

    private EditText etUsername;
    private EditText etPassword;

    private Button bLogin;

    private ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;

        setContentView(R.layout.activity_new_user);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.ibLogin);
        ibClose = findViewById(R.id.ibClose);

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Login button was hit!");

                String username = etUsername.getText() + "";
                String password = etPassword.getText() + "";

                if(username.length() == 0 && password.length() != 0)
                {
                    Toast.makeText(c, "Please enter a username.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(username.length() != 0 && password.length() == 0)
                {
                    Toast.makeText(c, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(username.length() == 0 && password.length() == 0)
                {
                    Toast.makeText(c, "Please enter a username and password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                    Log.d(TAG, "Username: " + username);
                    Log.d(TAG, "Password: " + password);

                    if(username.equals("vendor")) {
                        SaveSharedPreference.login(c, username, "vendor");
                    }
                    else if(username.equals("admin")) {
                        SaveSharedPreference.login(c, username, "admin");
                    }
                    else {
                        SaveSharedPreference.login(c, username, "customer");
                    }
                    finish();
                }
            }
        });
    }

    public void doHttp()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.vogella.com/index.html").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //prints the response to Logcat
                Log.d(TAG, response.toString());
            }
        });
    }

    //this is so that the UI doesnt get hung up during the http request, and instead the task gets done in the background
    public class Networking extends AsyncTask
    {
        public static final int NETWORK_STATE_REGISTER = 1;
        @Override
        protected Object doInBackground(Object[] objects) {
            Log.d(TAG, "doInBackground");

            return null;
        }
    }

}
