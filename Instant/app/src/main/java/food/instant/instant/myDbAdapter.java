package food.instant.instant;

/**
 * Created by adamdegala on 1/25/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String food, String restaurant, String quanity)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.FOOD, food);
        contentValues.put(myDbHelper.RESTAURANT, restaurant);
        contentValues.put(myDbHelper.QUANITY, quanity);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.FOOD,myDbHelper.RESTAURANT,myDbHelper.QUANITY};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();

        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String food =cursor.getString(cursor.getColumnIndex(myDbHelper.FOOD));
            String  restaurant =cursor.getString(cursor.getColumnIndex(myDbHelper.RESTAURANT));
            String  quanity =cursor.getString(cursor.getColumnIndex(myDbHelper.QUANITY));
            buffer.append(cid+ "   " + food + "   " + restaurant + "   " + quanity +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String Food)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={Food};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.FOOD+" = ?",whereArgs);
        return  count;
    }

//    public int updateName(String oldName , String newName)
//    {
//        SQLiteDatabase db = myhelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(myDbHelper.NAME,newName);
//        String[] whereArgs= {oldName};
//        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
//        return count;
//    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "localDatabase";    // Database Name
        private static final String TABLE_NAME = "orders";   // Table Name
        private static final int DATABASE_Version = 1;    // Not Sure what this is
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String FOOD = "Food";    //Column II
        private static final String RESTAURANT= "Restaturant";    // Column III
        private static final String QUANITY ="Quanity";     // Column IV

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ FOOD +" VARCHAR(255) ," + RESTAURANT +" VARCHAR(255) ," + QUANITY+" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}