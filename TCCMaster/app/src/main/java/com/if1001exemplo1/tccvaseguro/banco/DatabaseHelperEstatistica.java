package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelperEstatistica extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "estatistica.db";
    public static final String TABLE_NAME = "estatistica";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "numeroAlertas";
    public static final String COL_3 = "porcentagemMulheresAlertas";
    public static final String COL_4 = "porcentagemHomensAlertas";
    public static final String COL_5 = "horarioDeMaisAlertas";
    public static final String COL_6 = "cidadeDeMaisAlertas";
    public static final String COL_7 = "bairroDeMaisAlertas";
    public static final String COL_8 = "dateMaisAlertas";
    public static final String COL_9 = "modoMaisAlerta";
    public static final String COL_10 = "horarioMenosAlertas";
    public static final String COL_11 = "numerosBOfeitos";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " INTEGER, " +
            COL_3 + " REAL, " +
            COL_4 + " REAL, " +
            COL_5 + " TEXT, " +
            COL_6 + " TEXT, " +
            COL_7 + " TEXT, " +
            COL_8 + " TEXT, " +
            COL_9 + " TEXT, " +
            COL_10 + " TEXT, " +
            COL_11 + " INTEGER );";
    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperEstatistica(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean salvaEstatistica(int numeroAlertas, double porcentagemMulheresAlertas,
                                    double porcentagemHomensAlertas, String horarioDeMaisAlertas,
                                    String cidadeDeMaisAlertas, String bairroDeMaisAlertas, Date dateMaisAlertas,
                                    String modoMaisAlerta, String horarioMenosAlertas, int numerosBOfeitos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, numeroAlertas);
        content_values.put(COL_3, porcentagemMulheresAlertas);
        content_values.put(COL_4, porcentagemHomensAlertas);
        content_values.put(COL_5, horarioDeMaisAlertas);
        content_values.put(COL_6, cidadeDeMaisAlertas);
        content_values.put(COL_7, bairroDeMaisAlertas);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dateMaisAlertas);
        content_values.put(COL_8, reportDate);
        content_values.put(COL_9, modoMaisAlerta);
        content_values.put(COL_10, horarioMenosAlertas);
        content_values.put(COL_11, numerosBOfeitos);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listEstatistica() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor students = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return students;
    }


    public boolean updateEstatistica(String id, int numeroAlertas, double porcentagemMulheresAlertas,
                                     double porcentagemHomensAlertas, String horarioDeMaisAlertas,
                                     String cidadeDeMaisAlertas, String bairroDeMaisAlertas,
                                     Date dateMaisAlertas, String modoMaisAlerta, String horarioMenosAlertas,
                                     int numerosBOfeitos) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, numeroAlertas);
        content_values.put(COL_3, porcentagemMulheresAlertas);
        content_values.put(COL_4, porcentagemHomensAlertas);
        content_values.put(COL_5, horarioDeMaisAlertas);
        content_values.put(COL_6, cidadeDeMaisAlertas);
        content_values.put(COL_7, bairroDeMaisAlertas);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dateMaisAlertas);
        content_values.put(COL_8, reportDate);
        content_values.put(COL_9, modoMaisAlerta);
        content_values.put(COL_10, horarioMenosAlertas);
        content_values.put(COL_11, numerosBOfeitos);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }


    public Integer delete_estatistica(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
