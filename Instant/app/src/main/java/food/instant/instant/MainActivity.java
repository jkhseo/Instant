package food.instant.instant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView mNavigationView = findViewById(R.id.nav_view);

        //helper method to hide login/logout buttons
        checkUi(mNavigationView, MainActivity.this);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case(R.id.nav_login):
                        Intent loginIntent = new Intent(MainActivity.this, NewUserActivity.class);
                        MainActivity.this.startActivity(loginIntent);
                        break;
                    case(R.id.nav_logout):
                        SaveSharedPreference.clearUserName(MainActivity.this);
                        checkUi(mNavigationView, MainActivity.this);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        final NavigationView mNavigationView = findViewById(R.id.nav_view);
        checkUi(mNavigationView, MainActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*Opens the new user activity*/
    public void startNewUserActivity(View view)
    {

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

    private void checkUi(NavigationView mNavigationView, Context cxt)
    {
        TextView welcomeMsg = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message);
        //if user is logged out
        if(SaveSharedPreference.getUserName(cxt).length() == 0)
        {
            mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            mNavigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
            mNavigationView.getMenu().findItem(R.id.nav_settings).setVisible(false);
            mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            welcomeMsg.setText("Welcome");
        }
        //else user is logged in
        else
        {
            mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.nav_account).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            welcomeMsg.setText("Welcome, " + SaveSharedPreference.getUserName(cxt));
        }
    }
}
