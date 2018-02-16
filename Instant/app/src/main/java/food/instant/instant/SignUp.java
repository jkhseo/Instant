package food.instant.instant;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static food.instant.instant.HttpRequests.HttpGET;

public class SignUp extends AppCompatActivity {

    private Context c = this;
    private static final String TAG = "SignUp";

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAddress;
    private EditText etBirthday;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etReenter;
    private Button submit;
    private String userType = "";
    private RadioGroup rgUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etAddress = findViewById(R.id.et_Address);
        etBirthday = findViewById(R.id.et_Birthday);
        etEmail = findViewById(R.id.et_Email);
        etPassword = findViewById(R.id.et_Password);
        etReenter = findViewById(R.id.et_Reenter);
        rgUserType = findViewById(R.id.rg_userType);
        submit = findViewById(R.id.bn_Register);

        rgUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton customer = findViewById(R.id.rb_Customer);
                RadioButton vendor = findViewById(R.id.rb_Vendor);
                if(customer.isChecked())
                {
                    userType = "Customer";
                }
                else if(vendor.isChecked())
                {
                    userType = "Vendor";
                }
                else
                {
                    userType = "";
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String address = etAddress.getText().toString();
                String birthday = etBirthday.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String reenter = etReenter.getText().toString();

                if(firstName.length() == 0 || lastName.length() == 0 || address.length() == 0 || birthday.length() == 0
                        || email.length() == 0 || password.length() == 0 || reenter.length() == 0 || userType.length() == 0)
                {
                    Toast.makeText(c, "Please fill in all fields and select user type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!password.equals(reenter))
                {
                    Toast.makeText(c, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!checkDateFormat(birthday))
                {
                    Toast.makeText(c, "Bad date format!", Toast.LENGTH_SHORT).show();
                    etBirthday.setText("");
                    return;
                }
                else
                {
                    final SignUpHandler handler = new SignUpHandler(SignUp.this);

                    String request = "addUser?User_Type=" + userType + "&First_Name=" + firstName +
                            "&Last_Name=" + lastName + "&User_Address=" + address + "&User_Birthday=" + birthday +
                            "&User_Email=" + email + "&User_Password=" + password;
                    HttpGET(request , handler);
                    Toast.makeText(c, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private static boolean checkDateFormat(String date) {
        if (date.length() != 10) {
            return false;
        } else if(!(Character.isDigit(date.charAt(0)) && Character.isDigit(date.charAt(1)) && Character.isDigit(date.charAt(2)) &&
                Character.isDigit(date.charAt(3)) && date.charAt(4) == '-'))
        {
            return false;
        }
        else if(!(Character.isDigit(date.charAt(5)) && Character.isDigit(date.charAt(6)) && date.charAt(7) == '-'))
        {
            return false;
        }
        else if(!(Integer.parseInt(date.substring(5, 7)) <= 12 && Integer.parseInt(date.substring(5, 7)) > 0))
        {
            return false;
        }
        else if(!(Character.isDigit(date.charAt(8)) && Character.isDigit(date.charAt(9))))
        {
            return false;
        }
        else if(!(Integer.parseInt(date.substring(8, 10)) <= 31 && Integer.parseInt(date.substring(8, 10)) > 0))
        {
            return false;
        }
        return true;
    }

    private static class SignUpHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<SignUp> signupActivity;
        public SignUpHandler(SignUp signupActivity) {
            this.signupActivity = new WeakReference<SignUp>(signupActivity);
        }
        /*** End Code***/
        @Override
        public void handleMessage(Message msg) {
            SignUp signup = signupActivity.get();
            if (signup != null) {
                JSONObject response = null;
                response = (JSONObject) msg.obj;
                Log.d(TAG, "Request made.........................");
                Log.d(TAG, response.toString());
            }
        }
    }
}
