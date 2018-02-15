package food.instant.instant;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static food.instant.instant.HttpRequests.HttpGET;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private Context c;

    private EditText etUsername;
    private EditText etPassword;

    private Button bLogin;

    private ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;

        setContentView(R.layout.activity_login);

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
                    final LoginHandler handler = new LoginHandler(LoginActivity.this);

                    HttpGET("getPassword?User_Email=" + username, handler);

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

        TextView forgotPassword = findViewById(R.id.tvForgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, ForgotPassword.class);
                startActivity(intent);
            }
        });

        TextView signUp = findViewById(R.id.tvSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private static class LoginHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<LoginActivity> loginActivity;
        public LoginHandler(LoginActivity loginActivity) {
            this.loginActivity = new WeakReference<LoginActivity>(loginActivity);
        }
        /*** End Code***/
        @Override
        public void handleMessage(Message msg) {
            LoginActivity login = loginActivity.get();
            if (login != null) {
                JSONObject response = null;
                response = (JSONObject) msg.obj;
                Log.d(TAG, response.toString());
            }
        }
    }

}
