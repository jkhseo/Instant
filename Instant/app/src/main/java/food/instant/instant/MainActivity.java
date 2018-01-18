package food.instant.instant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*Opens the new user activity*/
    public void startNewUserActivity(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, NewUserActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    /*Opens the Vendor activity*/
    public void startVendorActivity(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, VendorActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    /*Opens the Admin activity*/
    public void startAdminActivity(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, AdminActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    /*Opens the Customer activity*/
    public void startCustomerActivity(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, CustomerActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
