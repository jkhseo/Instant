package food.instant.instant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Peter on 2/13/18.
 */

public class OrderDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "order_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TAG = "Database Operations";

    public static final String CREATE_TABLE = "create table " + OrderContract.OrderEntry.TABLE_NAME +
            "(" + OrderContract.OrderEntry.ORDER_ID + " number," + OrderContract.OrderEntry.FOOD_NAME + " text," +
            OrderContract.OrderEntry.EMAIL + " text);";

    public static final String DROP_TABLE = "drop table if exists " + OrderContract.OrderEntry.TABLE_NAME;

    public OrderDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Database Created...");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d(TAG, "Created table...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d(TAG, "Upgraded table...");
    }

    public void addOrder(int id, String foodName, String email, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderContract.OrderEntry.ORDER_ID, id);
        contentValues.put(OrderContract.OrderEntry.FOOD_NAME, foodName);
        contentValues.put(OrderContract.OrderEntry.EMAIL, email);

        database.insert(OrderContract.OrderEntry.TABLE_NAME, null, contentValues);
        Log.d(TAG, "One row inserted");

    }
}
