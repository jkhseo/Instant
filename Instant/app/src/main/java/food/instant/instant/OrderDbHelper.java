package food.instant.instant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
            "(" + OrderContract.OrderEntry.RESTAURANT_ID + " number," + OrderContract.OrderEntry.RESTAURANT_NAME + " text,"+
            OrderContract.OrderEntry.FOOD_ID + " number,"+OrderContract.OrderEntry.FOOD_QUANTITY + " number,"+OrderContract.OrderEntry.FOOD_NAME
            + " text,"+OrderContract.OrderEntry.FOOD_PRICE + " number,"+OrderContract.OrderEntry.COMMENTS + " text);";
    ContentValues contentValues = new ContentValues();

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

    public void addOrder(int Rest_ID,Food food, int Food_Quantity, String comments, String Restaurant_Name, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderContract.OrderEntry.RESTAURANT_ID, Rest_ID);
        contentValues.put(OrderContract.OrderEntry.RESTAURANT_NAME, Restaurant_Name);
        contentValues.put(OrderContract.OrderEntry.FOOD_ID, food.getFood_ID());
        contentValues.put(OrderContract.OrderEntry.FOOD_QUANTITY, Food_Quantity);
        contentValues.put(OrderContract.OrderEntry.FOOD_NAME, food.getFood_Name());
        contentValues.put(OrderContract.OrderEntry.FOOD_PRICE, food.getFood_Price());
        contentValues.put(OrderContract.OrderEntry.COMMENTS, comments);
        database.insert(OrderContract.OrderEntry.TABLE_NAME, null, contentValues);
        //Cursor temp = database.rawQuery("SELECT * FROM "+ OrderContract.OrderEntry.TABLE_NAME,null);
        //temp.moveToLast();
        Log.d(TAG, "One row inserted");
        //return temp.getInt(temp.getColumnIndex("_rowid_"));


    }
    public void removeOrder(int row_id,SQLiteDatabase database){
        database.delete(OrderContract.OrderEntry.TABLE_NAME,"_rowid_="+row_id,null);
        Log.d(TAG,"One row removed");
    }
    public Cursor readOrders(SQLiteDatabase database)
    {
        String[] projections = {"rowid",OrderContract.OrderEntry.RESTAURANT_ID, OrderContract.OrderEntry.RESTAURANT_NAME,OrderContract.OrderEntry.FOOD_ID,
                OrderContract.OrderEntry.FOOD_QUANTITY,OrderContract.OrderEntry.FOOD_NAME,OrderContract.OrderEntry.FOOD_PRICE,OrderContract.OrderEntry.COMMENTS};

        Cursor cursor = database.query(OrderContract.OrderEntry.TABLE_NAME,
                projections, null, null, null, null, null);

        return cursor;
    }
}
