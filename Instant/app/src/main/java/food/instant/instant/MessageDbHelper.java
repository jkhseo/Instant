package food.instant.instant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mpauk on 3/28/2018.
 */

public class MessageDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "message_db";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE = "create table " + MessageContract.MessageEntry.TABLE_NAME +
            "(" + MessageContract.MessageEntry.MESSAGE + " text," + MessageContract.MessageEntry.RECIEVER_TYPE + " text,"+
            MessageContract.MessageEntry.RECIEVER_ID + " number,"+ MessageContract.MessageEntry.SENDER_TYPE + " text,"+ MessageContract.MessageEntry.SENDER_ID
            + " number);";
    public static final String DROP_TABLE = "drop table if exists " + MessageContract.MessageEntry.TABLE_NAME;
    ContentValues contentValues = new ContentValues();

    public MessageDbHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    public Cursor getRestMessages(SQLiteDatabase database,int ID,String type){
        String query = "SELECT * FROM " + MessageContract.MessageEntry.TABLE_NAME + " WHERE (("+ MessageContract.MessageEntry.SENDER_ID+"="+ID+" AND " +MessageContract.MessageEntry.SENDER_TYPE +"='"+type+"') " +
                "OR ("+MessageContract.MessageEntry.RECIEVER_ID+"="+ID+" AND " +MessageContract.MessageEntry.RECIEVER_TYPE +"='"+type+"'))";
        System.out.println(query);
        return database.rawQuery(query,null);
    }
    public void addMessage(Message message,SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put(MessageContract.MessageEntry.MESSAGE,message.getMessage());
        values.put(MessageContract.MessageEntry.RECIEVER_TYPE,message.getRecieverType());
        values.put(MessageContract.MessageEntry.RECIEVER_ID,message.getRecieverID());
        values.put(MessageContract.MessageEntry.SENDER_TYPE,message.getSenderType());
        values.put(MessageContract.MessageEntry.SENDER_ID,message.getSenderID());
        database.insert(MessageContract.MessageEntry.TABLE_NAME,null,values);
    }
    public Cursor readMessages(SQLiteDatabase database){
        String[] columns = {MessageContract.MessageEntry.MESSAGE, MessageContract.MessageEntry.RECIEVER_TYPE, MessageContract.MessageEntry.RECIEVER_ID,
                MessageContract.MessageEntry.SENDER_TYPE, MessageContract.MessageEntry.SENDER_ID};
        Cursor cursor = database.query(MessageContract.MessageEntry.TABLE_NAME,columns,null,null,null,null,null);
        return cursor;

    }
}
