package food.instant.instant;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.*;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import java.security.interfaces.RSAKey;
import java.util.Collections;
import java.util.List;

import static food.instant.instant.HttpRequests.HttpGET;


public class MainActivity extends AppCompatActivity implements user_home_maps.OnFragmentInteractionListener, user_home_orders.OnFragmentInteractionListener, user_home.OnFragmentInteractionListener,user_home_restaurant.OnFragmentInteractionListener, user_home_search.OnFragmentInteractionListener, admin_home.OnFragmentInteractionListener, vendor_analytics.OnFragmentInteractionListener, vendor_edit_menu.OnFragmentInteractionListener, vendor_home.OnFragmentInteractionListener, vendor_orders.OnFragmentInteractionListener, vendor_restaurant_details.OnFragmentInteractionListener, user_home_food.OnFragmentInteractionListener, user_home_order.OnFragmentInteractionListener , VendorCompletedOrdersFragment.OnFragmentInteractionListener, VendorPendingOrdersFragment.OnFragmentInteractionListener, vendor_home_order.OnFragmentInteractionListener, VendorConfirmedOrdersFragment.OnFragmentInteractionListener,user_home_chat.OnFragmentInteractionListener, user_home_messages.OnFragmentInteractionListener, vendor_my_restaurants.OnFragmentInteractionListener, vendor_add_restaurant.OnFragmentInteractionListener, vendor_menu_details.OnFragmentInteractionListener, vendor_messages.OnFragmentInteractionListener,vendor_message.OnFragmentInteractionListener{



    private Context c;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private MainActivityHandler handler;
    private boolean serviceStarted = false;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;

        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
       // HttpPostRestaurant("http://proj-309-sd-4.cs.iastate.edu/demo/addRestaurant","");
        //HttpGET("getRestaurants");
        //This loads the starting fragment
        chooseStartingFragment(c);
        //Starting Fragment loaded

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final NavigationView mNavigationView = findViewById(R.id.nav_view);

        //helper method to hide login/logout buttons
        chooseNavDrawer(mNavigationView, c);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean close = true;
                switch (item.getItemId())
                {
                    case(R.id.nav_messages):
                        if(SaveSharedPreference.getType(c).equals("Vendor")){
                            swapFragments(new vendor_messages());
                        }
                        else{
                            swapFragments(new user_home_messages());
                        }
                        break;
                    case(R.id.nav_home_user):
                        swapFragments(new user_home());
                        break;
                    case(R.id.nav_login):
                        Intent loginIntent = new Intent(c, LoginActivity.class);
                        c.startActivity(loginIntent);
                        close = false;
                        break;
                    case(R.id.nav_logout):
                        if(serviceStarted) {
                            stopService(new Intent(c, ChatService.class));
                            OrderDbHelper dbHelper = new OrderDbHelper(c);
                            dbHelper.deleteMessages(dbHelper.getWritableDatabase());
                            dbHelper.close();
                            serviceStarted=false;
                        }
                        SaveSharedPreference.logout(c);
                        chooseNavDrawer(mNavigationView, c);
                        close = false;
                        break;
                    case(R.id.nav_map):
                        swapFragments(new user_home_maps());
                        break;
                    case(R.id.nav_search):
                        swapFragments(new user_home_search());
                        break;
                    case(R.id.nav_orders_user):
                        swapFragments(new user_home_orders());
                        break;
                    case(R.id.nav_account):
                        break;
                    case(R.id.nav_home_admin):
                        swapFragments(new admin_home());
                        break;
                    case(R.id.nav_vendor_analytics):
                        swapFragments(new vendor_analytics());
                        break;
                    case(R.id.nav_vendor_edit_menu):
                        swapFragments(new vendor_edit_menu());
                        break;
                    case(R.id.nav_home_vendor):
                        swapFragments(new vendor_home());
                        break;
                    case(R.id.nav_vendor_orders):
                        swapFragments(new vendor_orders());
                        break;
                    case(R.id.nav_vendor_restaurant_details):
                        swapFragments(new vendor_restaurant_details());
                        break;
                    default:
                        Log.d(TAG, "#########Default case hit on the navigation drawer switch!#########");
                }
                if(close) {
                    mDrawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }

    public void bindService(){
        Intent intent = new Intent(this,ChatService.class);
        Messenger messenger = new Messenger(handler);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Handler",messenger);
        intent.putExtras(bundle);
        startService(intent);
        //bindService(intent,connection,Context.BIND);
    }
    @Override
    protected void onResume(){
        super.onResume();
        final NavigationView mNavigationView = findViewById(R.id.nav_view);
        chooseNavDrawer(mNavigationView, c);
        if(SaveSharedPreference.isLoggedIn(this)&&!(SaveSharedPreference.getType(this).equals("Admin"))) {
            serviceStarted=true;
            bindService();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        handler = new MainActivityHandler(this);
        HttpGET("getRSAPublicKey",handler);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceStarted) {
            stopService(new Intent(this, ChatService.class));
            //unbindService(connection);
            serviceStarted=false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void chooseStartingFragment(Context cxt)
    {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        //if a vendor is logged in
        if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("Vendor"))
        {
            transaction.add(R.id.content_frame, new vendor_home());
        }
        //if an admin is logged in
        else if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("Admin"))
        {
            transaction.add(R.id.content_frame, new admin_home());
        }
        //else user is logged out or a customer is logged in
        else
        {
            transaction.add(R.id.content_frame, new user_home());
        }
        transaction.commit();
    }

    private void chooseNavDrawer(NavigationView mNavigationView, Context cxt)
    {
        TextView welcomeMsg = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message);
        //if customer is logged in
        if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("Customer"))
        {
            loginDefault(mNavigationView, cxt);
        }
        //if a vendor is logged in
        else if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("Vendor"))
        {
            loginVendor(mNavigationView, cxt);
            swapFragments(new vendor_home());
        }
        //if an admin is logged in
        else if(SaveSharedPreference.isLoggedIn(cxt) && SaveSharedPreference.getType(cxt).equals("Admin"))
        {
            loginAdmin(mNavigationView, cxt);
            swapFragments(new admin_home());
        }
        //else user is logged out
        else
        {
            logoutDefault(mNavigationView);
        }
    }

    public void loginAdmin(NavigationView mNavigationView, Context cxt)
    {
        logoutDefault(mNavigationView);

        mNavigationView.getMenu().findItem(R.id.nav_home_admin).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_home_admin).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_logout).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_home_user).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_home_user).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_search).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_search).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_map).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_map).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_login).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_messages).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_messages).setEnabled(false);

        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message)).setText("Welcome, " + SaveSharedPreference.getFirstName(cxt) + " " + SaveSharedPreference.getLastName(cxt));
    }

    public void loginVendor(NavigationView mNavigationView, Context cxt)
    {
        logoutDefault(mNavigationView);

        mNavigationView.getMenu().findItem(R.id.nav_home_vendor).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_home_vendor).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_logout).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_restaurant_details).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_restaurant_details).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_edit_menu).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_edit_menu).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_orders).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_orders).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_analytics).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_analytics).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_home_user).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_home_user).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_search).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_search).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_map).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_map).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_messages).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_messages).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_login).setEnabled(false);



        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message)).setText("Welcome, " + SaveSharedPreference.getFirstName(cxt) + " " + SaveSharedPreference.getLastName(cxt));
    }

    public void loginDefault(NavigationView mNavigationView, Context cxt)
    {
        logoutDefault(mNavigationView);

        mNavigationView.getMenu().findItem(R.id.nav_account).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_account).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_orders_user).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_orders_user).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_login).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_home_user).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_home_user).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_messages).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_messages).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_logout).setEnabled(true);



        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message)).setText("Welcome, " + SaveSharedPreference.getFirstName(cxt) + " " + SaveSharedPreference.getLastName(cxt));
    }

    public void logoutDefault(NavigationView mNavigationView)
    {
        //clear user items
        mNavigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_account).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_orders_user).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_orders_user).setEnabled(false);

        //clear vendor items
        mNavigationView.getMenu().findItem(R.id.nav_home_vendor).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_home_vendor).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_restaurant_details).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_restaurant_details).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_edit_menu).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_edit_menu).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_orders).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_orders).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_vendor_analytics).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_vendor_analytics).setEnabled(false);

        //clear admin items
        mNavigationView.getMenu().findItem(R.id.nav_home_admin).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_home_admin).setEnabled(false);

        //clear items common to any logged in account
        mNavigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_logout).setEnabled(false);

        //re-show items that are viewable by default
        mNavigationView.getMenu().findItem(R.id.nav_home_user).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_home_user).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_search).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_search).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_map).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_map).setEnabled(true);

        mNavigationView.getMenu().findItem(R.id.nav_messages).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.nav_messages).setEnabled(false);

        mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.nav_login).setEnabled(true);



        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_message)).setText("Welcome");
        swapFragments(new user_home());
    }

    public void swapFragments(Fragment obj)
    {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, obj)
                    .addToBackStack(null)
                    .commit();
    }
    private static class MainActivityHandler extends Handler {
        private final WeakReference<MainActivity> mainActivity;
        public MainActivityHandler(MainActivity mainActivity) {
            this.mainActivity = new WeakReference<MainActivity>(mainActivity);
        }
        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
            MainActivity activity = mainActivity.get();
            FragmentManager manager = activity.getSupportFragmentManager();
            List<Fragment> list = manager.getFragments();
            if(msg.what== GlobalConstants.RSA_KEY) {
                String RSA_KEY;
                String EncryptionExponent;
                int Version;
                try {
                    JSONArray object = ((JSONObject)msg.obj).getJSONArray("Keys");
                    RSA_KEY = ((String)((JSONObject)object.get(0)).get("Public_Key"));
                    Version = Integer.parseInt((String)((JSONObject)object.get(0)).get("Version"));
                    EncryptionExponent = ((String) ((JSONObject)object.get(0)).get("Encyption_Exponet"));
                    OrderDbHelper dbHelper = new OrderDbHelper(activity);
                    dbHelper.insertRSAInfo(dbHelper.getWritableDatabase(),RSA_KEY,EncryptionExponent,Version);
                    dbHelper.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Message temp = (Message) msg.obj;
                System.out.println("Received" + list.size());
                if (list != null) {
                    list.removeAll(Collections.singleton(null));
                    int index = list.size()-1;
                    if (list.get(index).getClass().getSimpleName().equals("user_home_chat")) {
                        user_home_chat chat = (user_home_chat) list.get(index);
                        if (chat.getRestID()==temp.getRest_ID())
                            chat.addMessage((Message) msg.obj);

                    } else if (list.get(index).getClass().getSimpleName().equals("user_home_messages")) {
                        user_home_messages messages = (user_home_messages) list.get(index);
                        messages.addMessage(temp);
                    }
                    else if(list.get(index).getClass().getSimpleName().equals("vendor_message")){
                        vendor_message message = (vendor_message)list.get(index);
                        if(message.getRest_ID()==temp.getRest_ID()){
                            message.addMessage(temp);
                        }
                    }
                }
                OrderDbHelper dbHelper = new OrderDbHelper(activity);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                dbHelper.addMessage(temp, database);
                dbHelper.close();
            }

        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
        {}
    }
}
