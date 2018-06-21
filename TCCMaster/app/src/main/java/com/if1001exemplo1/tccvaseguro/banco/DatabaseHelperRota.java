package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelperRota extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rota.db";
    public static final String TABLE_NAME = "rota";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "nomeDestino";
    public static final String COL_3 = "cidade";
    public static final String COL_4 = "uf";
    public static final String COL_5 = "cep";
    public static final String COL_6 = "emailRelatorio";
    public static final String COL_7 = "metrosDestino";
    public static final String COL_8 = "metrosDaRota";
    public static final String COL_9 = "modo";
    public static final String COL_10 = "usuario";
    public static final String COL_11 = "dataCadastro";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " TEXT, " +
            COL_5 + " REAL, " +
            COL_6 + " TEXT, " +
            COL_7 + " INTEGER, " +
            COL_8 + " INTEGER, " +
            COL_9 + " TEXT, " +
            COL_10 + " TEXT, " +
            COL_11 + " TEXT );";
    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperRota(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean salvaRota(String nomeDestino, String cidade, String uf, double cep, String emailRelatorio,
                             int metrosDestino, int metrosDaRota, String modo, String cpfUsu, Date dataCadastro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, nomeDestino);
        content_values.put(COL_3, cidade);
        content_values.put(COL_4, uf);
        content_values.put(COL_5, cep);
        content_values.put(COL_6, emailRelatorio);
        content_values.put(COL_7, metrosDestino);
        content_values.put(COL_8, metrosDaRota);
        content_values.put(COL_9, modo);
        content_values.put(COL_10, cpfUsu);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dataCadastro);
        content_values.put(COL_11, reportDate);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listRota() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor rot = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return rot;
    }


    public boolean updateRota(String id, String nomeDestino, String cidade, String uf, double cep, String emailRelatorio,
                              int metrosDestino, int metrosDaRota, String modo, String cpfUsu, Date dataCadastro) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, nomeDestino);
        content_values.put(COL_3, cidade);
        content_values.put(COL_4, uf);
        content_values.put(COL_5, cep);
        content_values.put(COL_6, emailRelatorio);
        content_values.put(COL_7, metrosDestino);
        content_values.put(COL_8, metrosDaRota);
        content_values.put(COL_9, modo);
        content_values.put(COL_10, cpfUsu);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dataCadastro);
        content_values.put(COL_11, reportDate);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }


    public Integer delete_rota(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}