package food.instant.instant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Peter on 1/31/18.
 */

public class SaveSharedPreference
{
    static final String PREF_USER_NAME= "username";
    //can be either admin, customer, or vendor
    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";
    static final String BIRTHDAY = "birthday";
    static final String ADDRESS = "address";
    static final String UID = "id";
    static final String USER_TYPE = "user type";
    static final String LOGGED_IN = "login state";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    //context, username, firstname, lastname, birthday, address, id, type
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

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getFirstName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(FIRST_NAME, "");
    }

    public static String getLastName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LAST_NAME, "");
    }

    public static String getBirthday(Context ctx)
    {
        return getSharedPreferences(ctx).getString(BIRTHDAY, "");
    }

    public static String getAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(ADDRESS, "");
    }

    public static String getId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(UID, "");
    }

    public static String getType(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_TYPE, "");
    }

    public static boolean isLoggedIn(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(LOGGED_IN, false);
    }

    public static void logout(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}