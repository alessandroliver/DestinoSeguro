package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelperRelatorio extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "relatorio.db";
    public static final String TABLE_NAME = "relatorio";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "origem";
    public static final String COL_3 = "destino";
    public static final String COL_4 = "tempo_saida";
    public static final String COL_5 = "tempo_chegada";
    public static final String COL_6 = "latDangerousLocation";
    public static final String COL_7 = "lngDangerousLocation";
    public static final String COL_8 = "horario_do_alerta";
    public static final String COL_9 = "pessoas_receberam_alerta";
    public static final String COL_10 = "data_do_alerta";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " TEXT, " +
            COL_5 + " REAL, " +
            COL_6 + " REAL, " +
            COL_7 + " TEXT, " +
            COL_8 + " TEXT, " +
            COL_9 + " TEXT, " +
            COL_10 + " TEXT );";
    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperRelatorio(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean salvaRelatorio(String origem, String destino, String tempo_saida, String tempo_chegada,
                                 double latDangerousLocation, double lngDangerousLocation, String horario_do_alerta,
                                 String pessoas_receberam_alerta, Date data_do_alerta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, origem);
        content_values.put(COL_3, destino);
        content_values.put(COL_4, tempo_saida);
        content_values.put(COL_5, tempo_chegada);
        content_values.put(COL_6, latDangerousLocation);
        content_values.put(COL_7, lngDangerousLocation);
        content_values.put(COL_8, horario_do_alerta);
        content_values.put(COL_9, pessoas_receberam_alerta);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(data_do_alerta);
        content_values.put(COL_10, reportDate);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listRelatorio() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor rel = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return rel;
    }


    public boolean updateRelatorio(String id, String origem, String destino, String tempo_saida, String tempo_chegada,
                                   double latDangerousLocation, double lngDangerousLocation, String horario_do_alerta,
                                   String pessoas_receberam_alerta, Date data_do_alerta) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, origem);
        content_values.put(COL_3, destino);
        content_values.put(COL_4, tempo_saida);
        content_values.put(COL_5, tempo_chegada);
        content_values.put(COL_6, latDangerousLocation);
        content_values.put(COL_7, lngDangerousLocation);
        content_values.put(COL_8, horario_do_alerta);
        content_values.put(COL_9, pessoas_receberam_alerta);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(data_do_alerta);
        content_values.put(COL_10, reportDate);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }


    public Integer delete_relatorio(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
