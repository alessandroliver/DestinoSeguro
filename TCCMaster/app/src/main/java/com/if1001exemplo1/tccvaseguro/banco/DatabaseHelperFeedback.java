package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelperFeedback extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "feedback.db";
    public static final String TABLE_NAME = "feedback";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "origem";
    public static final String COL_3 = "destino";
    public static final String COL_4 = "modo";
    public static final String COL_5 = "quantidadeAlerta";
    public static final String COL_6 = "tempoPercurso";
    public static final String COL_7 = "dateFeedback";
    public static final String COL_8 = "latDangerousLocation";
    public static final String COL_9 = "lngDangerousLocation";
    public static final String COL_10 = "horarioAlerta";
    public static final String COL_11 = "descricaoAlerta";
    public static final String COL_12 = "cpfUsu";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " REAL, " +
            COL_5 + " INTEGER, " +
            COL_6 + " REAL, " +
            COL_7 + " TEXT, " +
            COL_8 + " REAL, " +
            COL_9 + " REAL, " +
            COL_10 + " TEXT, " +
            COL_11 + " TEXT, " +
            COL_12 + " TEXT );";
    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperFeedback(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean salvaFeedback(String origem, String destino, String modo, int quantidadeAlerta, double tempoPercurso,
                                 Date dateFeedback, double latDangerousLocation, double lngDangerousLocation,
                                 String horarioAlerta, String descricaoAlerta, String cfpUsu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, origem);
        content_values.put(COL_3, destino);
        content_values.put(COL_4, modo);
        content_values.put(COL_5, quantidadeAlerta);
        content_values.put(COL_6, tempoPercurso);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dateFeedback);
        content_values.put(COL_7, reportDate);
        content_values.put(COL_8, latDangerousLocation);
        content_values.put(COL_9, lngDangerousLocation);
        content_values.put(COL_10, horarioAlerta);
        content_values.put(COL_11, descricaoAlerta);
        content_values.put(COL_12, cfpUsu);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listFeedback() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor feed = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return feed;
    }


    public boolean updateFeedback(String id, String origem, String destino, String modo, int quantidadeAlerta,
                            double tempoPercurso, Date dateFeedback, double latDangerousLocation,
                            double lngDangerousLocation, String horarioAlerta, String descricaoAlerta, String cfpUsu) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, origem);
        content_values.put(COL_3, destino);
        content_values.put(COL_4, modo);
        content_values.put(COL_5, quantidadeAlerta);
        content_values.put(COL_6, tempoPercurso);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dateFeedback);
        content_values.put(COL_7, reportDate);
        content_values.put(COL_8, latDangerousLocation);
        content_values.put(COL_9, lngDangerousLocation);
        content_values.put(COL_10, horarioAlerta);
        content_values.put(COL_11, descricaoAlerta);
        content_values.put(COL_12, cfpUsu);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }


    public Integer delete_feedback(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
