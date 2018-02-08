package food.instant.instant;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
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

public class MainActivity extends AppCompatActivity implements user_home_maps.OnFragmentInteractionListener, user_home_orders.OnFragmentInteractionListener, user_home.OnFragmentInteractionListener,user_home_restaurant.OnFragmentInteractionListener, user_home_search.OnFragmentInteractionListener {

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

        //This loads the starting fragment
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content_frame, new VendorAdminFragment());
        transaction.commit();
        //Starting Fragment loaded

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final NavigationView mNavigationView = findViewById(R.id.nav_view);

        //helper method to hide login/logout buttons
        checkUi(mNavigationView, MainActivity.this);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean close = true;
                switch (item.getItemId())
                {
                    case(R.id.nav_login):
                        Intent loginIntent = new Intent(MainActivity.this, NewUserActivity.class);
                        MainActivity.this.startActivity(loginIntent);
                        close = false;
                        break;
                    case(R.id.nav_logout):
                        SaveSharedPreference.logout(MainActivity.this);
                        checkUi(mNavigationView, MainActivity.this);
                        close = false;
                        break;
                    case(R.id.nav_vendor):
                        swapFragments(new VendorAdminFragment());
                        break;
                    case(R.id.nav_map):
                        swapFragments(new user_home_maps());
                        break;
                    case(R.id.nav_search):
                        swapFragments(new user_home_search());
                        break;
                    case(R.id.nav_orders):
                        swapFragments(new user_home_orders());
                        break;
                    case(R.id.nav_account):
                        break;

                }
                if(close) {
                    mDrawerLayout.closeDrawers();
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
        //if customer is logged in
        if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("customer"))
        {
            loginDefault(mNavigationView, cxt);
        }
        //if a vendor is logged in
        else if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("vendor"))
        {
            loginDefault(mNavigationView, cxt);
            mNavigationView.getMenu().findItem(R.id.nav_vendor).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.nav_vendor).setEnabled(true);
        }
        //if an admin is logged in
        else if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("admin"))
        {
            loginDefault(mNavigationView, cxt);
            mNavigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.nav_admin).setEnabled(true);
        }
        //else user is logged out
        else
        {
            logoutDefault(mNavigationView);
        }
    }

    public void loginDefault(NavigationView mNavigationView, Context cxt)
    {
        mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_logout).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_account).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_account).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_orders).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_orders).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_login).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_vendor).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_vendor).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_admin).setEnabled(false);
        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message)).setText("Welcome, " + SaveSharedPreference.getUserName(cxt));
    }

    public void logoutDefault(NavigationView mNavigationView)
    {
        mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_logout).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_account).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_orders).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_vendor).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_vendor).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_admin).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_login).setEnabled(true);
        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message)).setText("Welcome");
    }

    public void swapFragments(Fragment obj)
    {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, obj);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
