package food.instant.instant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Peter on 1/31/18.
 * This class stores the login state of the app
 * When a user is logged in, all of their relevant info is
 * stored in this class
 */
public class SaveSharedPreference
{
    /**
     * Username of the current user
     */
    static final String PREF_USER_NAME= "username";
    //can be either admin, customer, or vendor
    /**
     * First name of the current user
     */
    static final String FIRST_NAME = "first name";

    /**
     * Last name of the current user
     */
    static final String LAST_NAME = "last name";

    /**
     * Birthday of the current user
     */
    static final String BIRTHDAY = "birthday";

    /**
     * Address of the current user
     */
    static final String ADDRESS = "address";

    /**
     * ID of the current user
     */
    static final String UID = "id";

    /**
     * Type of the current user
     * (can be either admin, customer, or vendor)
     */
    static final String USER_TYPE = "user type";

    /**
     * Login state of the app
     */
    static final String LOGGED_IN = "login state";

    /**
     * Constructor to create an instance of this class
     * @param ctx context in which this class is being instantiated
     * @return an instance of this object
     */
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    //context, username, firstname, lastname, birthday, address, id, type

    /**
     * Registers a user as logged in to this app
     * @param ctx context of the activity/fragment which is calling this method
     * @param userName username of the user that is being logged in
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param birthday birthday of the user
     * @param address address of the user
     * @param id id of the user
     * @param userType type of the user (admin, vendor, or customer)
     */
    public static void login(Context ctx, String userName, String firstName, String lastName, String birthday, String address, String id, String userType)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(BIRTHDAY, birthday);
        editor.putString(ADDRESS, address);
        editor.putString(UID, id);
        editor.putString(USER_TYPE, userType);

        editor.putBoolean(LOGGED_IN, true);
        editor.commit();
    }

    /**
     * Accessor for the username
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current logged-in user's username
     */
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    /**
     * Accessor for the first name
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current logged-in user's first name
     */
    public static String getFirstName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(FIRST_NAME, "");
    }

    /**
     * Accessor for the last name
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current logged-in user's last name
     */
    public static String getLastName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LAST_NAME, "");
    }

    /**
     * Accessor for the birthday
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current logged-in user's birthday
     */
    public static String getBirthday(Context ctx)
    {
        return getSharedPreferences(ctx).getString(BIRTHDAY, "");
    }

    /**
     * Accessor for the address
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current logged-in user's address
     */
    public static String getAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(ADDRESS, "");
    }

    /**
     * Accessor for the user id
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current logged-in user's id
     */
    public static String getId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(UID, "");
    }

    /**
     * Accessor for the user type
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current logged-in user's type
     */
    public static String getType(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_TYPE, "");
    }

    /**
     * Accessor for the login-state
     * @param ctx context of the activity/fragment which is calling this method
     * @return the current login-state of the app
     */
    public static boolean isLoggedIn(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(LOGGED_IN, false);
    }

    /**
     * Method to logout a user from the app
     * It clears all the data stored in this class
     * @param ctx context of the activity/fragment which is calling this method
     */
    public static void logout(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}