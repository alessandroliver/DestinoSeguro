package com.if1001exemplo1.tccvaseguro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationDatasOpenHelper extends SQLiteOpenHelper{

    // Database Version
    static final int DATABASE_VERSION = 1;

    // Database Name
    static final String DATABASE_NAME = "locationDatas";

    // Contacts table name
    static final String TABLE_COORDINATES = "coordinates";
    static final String TABLE_ROUTES = "routes";
    static final String TABLE_COORDINATES_ALERT = "coordinates_alert";



    // Contacts Table Columns names
    static final String _ID = "_id";
    static final String LAT = "lat";
    static final String LONG = "long";
    static final String STREET_ADRESS = "street_adress";
    static final String ID_ADRESS = "id_adress";
    static final String[] COLUMNS_COORDINATES = {LAT, LONG, STREET_ADRESS, ID_ADRESS};

    static final String _ID1 = "_id1";
    static final String ORIGIN_ADRESS = "origin_adress";
    static final String DESTINATION_ADRESS = "destination_adress";
    static final String ROUTE = "route";
    static final String TYPE = "type";
    static final String[] COLUMNS_ROUTE = {ORIGIN_ADRESS,DESTINATION_ADRESS,ROUTE, TYPE};

    static final String _ID2 = "_id";
    static final String LAT_ALERT = "lat_alert";
    static final String LONG_ALERT = "long_alert";
    static final String[] COLUMNS_COORDINATES_ALERT = {LAT_ALERT, LONG_ALERT};


    final private Context mContext;


    public LocationDatasOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tabela pra salvar coordenadas
        String CREATE_COORDINATES_TABLE = "CREATE TABLE " + TABLE_COORDINATES+"(" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + LAT + " REAL,"
                + LONG + " REAL," + STREET_ADRESS
                + " TEXT NOT NULL," + ID_ADRESS
                + " TEXT"+")";

        //tabela pra salvar as roras feitas
        String CREATE_ROUTES_TABLE = "CREATE TABLE " + TABLE_ROUTES+"(" + _ID1
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ORIGIN_ADRESS + " TEXT,"
                + DESTINATION_ADRESS + " TEXT," + ROUTE + " TEXT NOT NULL," + TYPE + " TEXT" +")";

        //tabela pra salvar os LP
        String CREATE_COORDINATES_ALERT_TABLE = "CREATE TABLE " + TABLE_COORDINATES_ALERT+"(" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + LAT_ALERT + " REAL,"
                + LONG_ALERT + " REAL" +")";


        db.execSQL(CREATE_COORDINATES_TABLE);
        db.execSQL(CREATE_ROUTES_TABLE);
        db.execSQL(CREATE_COORDINATES_ALERT_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COORDINATES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COORDINATES_ALERT);


        // Create tables again
        onCreate(db);

    }

    void deleteDatabaseCoordinates() {
        mContext.deleteDatabase(TABLE_COORDINATES);
    }

    void deleteDatabaseRoutes() {
        mContext.deleteDatabase(TABLE_ROUTES);
    }

    void deleteDatabaseCoordinatesAlarm() {
        mContext.deleteDatabase(TABLE_COORDINATES_ALERT);
    }



}
