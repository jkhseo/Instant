package food.instant.instant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 *
 * Class used for Database operations on the SQLite Database.
 * Created by Peter on 2/13/18.
 */

public class OrderDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "order_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TAG = "Database Operations";
    /**
     * Order Table Creation
     */
    public static final String CREATE_TABLE = "create table " + OrderContract.OrderEntry.TABLE_NAME +
            "(" + OrderContract.OrderEntry.RESTAURANT_ID + " number," + OrderContract.OrderEntry.RESTAURANT_NAME + " text,"+
            OrderContract.OrderEntry.FOOD_ID + " number,"+OrderContract.OrderEntry.FOOD_QUANTITY + " number,"+OrderContract.OrderEntry.FOOD_NAME
            + " text,"+OrderContract.OrderEntry.FOOD_PRICE+ " number," + OrderContract.OrderEntry.ORDER_STATUS + " text,"+OrderContract.OrderEntry.COMMENTS + " text);";
    ContentValues contentValues = new ContentValues();
    /**
     * Message Table Creation
     */
    public static final String CREATE_MESSAGE_TABLE = "create table " + MessageContract.MessageEntry.TABLE_NAME +
            "(" + MessageContract.MessageEntry.MESSAGE + " text," + MessageContract.MessageEntry.RECIEVER_TYPE + " text,"+
            MessageContract.MessageEntry.RECIEVER_ID + " number,"+ MessageContract.MessageEntry.SENDER_TYPE + " text,"+ MessageContract.MessageEntry.SENDER_ID
            +" number,"+ MessageContract.MessageEntry.REST_ID + " number);";
    /**
     * Key Table Creation
     */
    public static final String CREATE_KEY_TABLE = "create table "+ KeyContract.KeyEntry.TABLE_NAME+"("+ KeyContract.KeyEntry.SESSION_KEY_VALUE+" text,"+ KeyContract.KeyEntry.ENCRYPTION_EXPONENT +" text,"+ KeyContract.KeyEntry.VERSION+" number,"+ KeyContract.KeyEntry.AES_KEY+" number);";
    /**
     * Drop Message Table
     */
    public static final String DROP_MESSAGE_TABLE = "drop table if exists " + MessageContract.MessageEntry.TABLE_NAME;
    /**
     * Drop Key Table
     */
    public static final String DROP_KEY_TABLE = "drop table if exists "+ KeyContract.KeyEntry.TABLE_NAME;
    /**
     * Drop Order Table
     */
    public static final String DROP_TABLE = "drop table if exists " + OrderContract.OrderEntry.TABLE_NAME;

    /**
     * Default constructor
     * @param context context for this database
     */
    public OrderDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Database Created...");
    }

    /**
     * Creates message, order, and key tables.
     * @param sqLiteDatabase database object to create tables for
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_MESSAGE_TABLE);
        sqLiteDatabase.execSQL(CREATE_KEY_TABLE);
        Log.d(TAG, "Created table...");
    }

    /**
     * Drops message,order,and key tables and creates new ones
     * @param sqLiteDatabase database object to update tables
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(DROP_MESSAGE_TABLE);
        sqLiteDatabase.execSQL(DROP_KEY_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_MESSAGE_TABLE);
        sqLiteDatabase.execSQL(CREATE_KEY_TABLE);
        Log.d(TAG, "Upgraded table...");
    }

    /**
     * Adds order to the Order table of the database
     * @param Rest_ID restaurant ID of this order
     * @param food Food object belonging to this order
     * @param Food_Quantity Number of food objects belonging to this order
     * @param comments comments associated with this order
     * @param Restaurant_Name name of the restaurant associated with this order
     * @param database database instance used to update table information
     */
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
        contentValues.put(OrderContract.OrderEntry.ORDER_STATUS,"L");
        database.insert(OrderContract.OrderEntry.TABLE_NAME, null, contentValues);
        Log.d(TAG, "One row inserted");
    }

    /**
     * Remove an order from the order table
     * @param row_id row id of the order to be removed
     * @param database instance of the database to be used to update table information
     */
    public void removeOrder(int row_id,SQLiteDatabase database){
        database.delete(OrderContract.OrderEntry.TABLE_NAME,"_rowid_="+row_id,null);
        Log.d(TAG,"One row removed");
    }

    /**
     * Remove all orders with a status of Pending
     * @param database instance of the database to be used to update table information
     */
    public void removePendingOrders(SQLiteDatabase database){
        database.delete(OrderContract.OrderEntry.TABLE_NAME,OrderContract.OrderEntry.ORDER_STATUS+"='P'",null);
        Log.d(TAG,"Pending Orders Removed");
    }

    /**
     * Get all orders with status local for a particular restaurant
     * @param Rest_ID restaurant id for this restaurant
     * @param database instance of the database to be used to update table information
     * @return Cursor holding the requested data
     */
    public Cursor getLocalOrdersByRest(int Rest_ID, SQLiteDatabase database){
        String orderStatus = "'L'";
        String query = "SELECT * FROM " + OrderContract.OrderEntry.TABLE_NAME + " WHERE ("+ OrderContract.OrderEntry.RESTAURANT_ID+"="+Rest_ID+") AND "+"("+OrderContract.OrderEntry.ORDER_STATUS+"="+orderStatus+")";
        return database.rawQuery(query,null);

    }

    /**
     *Reads all orders from the order table.
     * @param database instance of the database to be used to update table information
     * @return Cursor holding the requested data
     */
    public Cursor readOrders(SQLiteDatabase database)
    {
        String[] projections = {"rowid",OrderContract.OrderEntry.RESTAURANT_ID, OrderContract.OrderEntry.RESTAURANT_NAME,OrderContract.OrderEntry.FOOD_ID,
                OrderContract.OrderEntry.FOOD_QUANTITY,OrderContract.OrderEntry.FOOD_NAME,OrderContract.OrderEntry.FOOD_PRICE,OrderContract.OrderEntry.COMMENTS,OrderContract.OrderEntry.ORDER_STATUS};
        Cursor cursor = database.query(OrderContract.OrderEntry.TABLE_NAME,
                projections, null, null, null, null, null);

        return cursor;
    }

    /**
     * Gets all messages for a particular ID and Type.
     * @param database instance of the database to be used to update table information
     * @param ID ID
     * @param type Type
     * @return Cursor with requested information
     */
    public Cursor getUserMessages(SQLiteDatabase database,int ID,String type){
        String query = "SELECT * FROM " + MessageContract.MessageEntry.TABLE_NAME + " WHERE (("+ MessageContract.MessageEntry.SENDER_ID+"="+ID+" AND " +MessageContract.MessageEntry.SENDER_TYPE +"='"+type+"') " +
                "OR ("+MessageContract.MessageEntry.RECIEVER_ID+"="+ID+" AND " +MessageContract.MessageEntry.RECIEVER_TYPE +"='"+type+"'))";
        System.out.println(query);
        return database.rawQuery(query,null);
    }
    public Cursor getRestMessages(SQLiteDatabase database, int Rest_ID){
        String query = "SELECT * FROM " + MessageContract.MessageEntry.TABLE_NAME + " WHERE "+ MessageContract.MessageEntry.REST_ID+"="+Rest_ID;
        System.out.println(query);
        return database.rawQuery(query,null);
    }
    /**
     * Adds a message to the the message database table
     * @param message message data to add to the table
     * @param database instance of the database to be used to update table information
     */
    public void addMessage(Message message,SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put(MessageContract.MessageEntry.MESSAGE,message.getMessage());
        values.put(MessageContract.MessageEntry.RECIEVER_TYPE,message.getRecieverType());
        values.put(MessageContract.MessageEntry.RECIEVER_ID,message.getRecieverID());
        values.put(MessageContract.MessageEntry.SENDER_TYPE,message.getSenderType());
        values.put(MessageContract.MessageEntry.SENDER_ID,message.getSenderID());
        values.put(MessageContract.MessageEntry.REST_ID,message.getRest_ID());
        database.insert(MessageContract.MessageEntry.TABLE_NAME,null,values);
    }

    /**
     * Gets all messages from the message database table
     * @param database instance of the database to be used to update table information
     * @return Cursor with requested data
     */
    public Cursor readMessages(SQLiteDatabase database){
        String[] columns = {MessageContract.MessageEntry.MESSAGE, MessageContract.MessageEntry.RECIEVER_TYPE, MessageContract.MessageEntry.RECIEVER_ID,
                MessageContract.MessageEntry.SENDER_TYPE, MessageContract.MessageEntry.SENDER_ID, MessageContract.MessageEntry.REST_ID};
        Cursor cursor = database.query(MessageContract.MessageEntry.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;

    }

    /**
     * Deletes all messages in the message database table
     * @param database instance of the database to be used to update table information
     */
    public void deleteMessages(SQLiteDatabase database){
        database.delete(MessageContract.MessageEntry.TABLE_NAME,null,null);
    }
    public void insertRSAInfo(SQLiteDatabase database, String RSA_KEY,String EncyptionExponent, int Version){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KeyContract.KeyEntry.SESSION_KEY_VALUE, RSA_KEY);
        contentValues.put(KeyContract.KeyEntry.ENCRYPTION_EXPONENT, EncyptionExponent);
        contentValues.put(KeyContract.KeyEntry.VERSION, Version);
        database.insert(KeyContract.KeyEntry.TABLE_NAME, null, contentValues);
        Log.d(TAG, "One row inserted");
    }
    public void insertAESKey(SQLiteDatabase database,int AESKEY){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KeyContract.KeyEntry.AES_KEY, AESKEY);
        database.insert(KeyContract.KeyEntry.TABLE_NAME, null, contentValues);
        Log.d(TAG, "One row inserted");
    }
    public Cursor getRSAInfo(SQLiteDatabase database){
        String[] columns = {KeyContract.KeyEntry.SESSION_KEY_VALUE, KeyContract.KeyEntry.ENCRYPTION_EXPONENT, KeyContract.KeyEntry.VERSION};
        Cursor cursor = database.query(KeyContract.KeyEntry.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }
}
