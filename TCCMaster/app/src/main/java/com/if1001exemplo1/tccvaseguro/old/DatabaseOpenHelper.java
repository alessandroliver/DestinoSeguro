package com.if1001exemplo1.tccvaseguro.old;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public final static String TABLE_NAME = "safe_contacts";
    public final static String CONTACT_NAME = "name";
    public final static String CONTACT_NUMBER = "number";
    public final static String CONTACT_ALERT = "alert";
    final static String _ID = "_id";
    public final static String[] columns = { _ID, CONTACT_NAME, CONTACT_NUMBER, CONTACT_ALERT};

    final private static String CREATE_CMD =

            "CREATE TABLE "+TABLE_NAME+" ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CONTACT_NAME + " TEXT NOT NULL, "
                    +CONTACT_NUMBER+" TEXT NOT NULL, "
                    +CONTACT_ALERT+" TEXT NOT NULL)";


    final private static String NAME = "safe_contacts";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    public static DatabaseOpenHelper getMyDatabase(Context context){
        return (new DatabaseOpenHelper(context.getApplicationContext()));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
        //deleteDatabase();
        //db.delete(TABLE_NAME, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }

}
