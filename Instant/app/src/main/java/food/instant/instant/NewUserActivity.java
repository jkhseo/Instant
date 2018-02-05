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

                    SaveSharedPreference.setUserName(c, username);
                    finish();
                }
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
