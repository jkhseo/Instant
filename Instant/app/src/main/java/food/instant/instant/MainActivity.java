package food.instant.instant;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
