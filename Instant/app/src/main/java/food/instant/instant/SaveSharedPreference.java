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
    static final String USER_TYPE = "user type";
    static final String LOGGED_IN = "login state";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void login(Context ctx, String userName, String userType)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_TYPE, userType);
        editor.putString(PREF_USER_NAME, userName);
        editor.putBoolean(LOGGED_IN, true);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
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