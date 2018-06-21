package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelperBO extends SQLiteOpenHelper {

    //COLUNAS DA TABELA
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bo.db";
    public static final String TABLE_NAME = "bo";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "tipoOcorrencia";
    public static final String COL_3 = "rua";
    public static final String COL_4 = "cep";
    public static final String COL_5 = "bairro";
    public static final String COL_6 = "uf";
    public static final String COL_7 = "dataOcorrencia";
    public static final String COL_8 = "descricaoAcontecido";
    public static final String COL_9 = "descricaoPessoasEnvolvidas";
    public static final String COL_10 = "quantidadeCoisasRoubadas";
    public static final String COL_11 = "cpfUsu";


    //DECLARANDO A TABELA
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " REAL, " +
            COL_5 + " TEXT, " +
            COL_6 + " TEXT, " +
            COL_7 + " TEXT, " +
            COL_8 + " TEXT, " +
            COL_9 + " TEXT, " +
            COL_10 + " INTEGER, " +
            COL_11 + " TEXT );";
    @Override

    public void onCreate(SQLiteDatabase db) {

        //CRIA TABELA DECLARADA
        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperBO(Context context) {
       // CONSTRUTOR
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ATUALIZA
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean salvaBO(String tipoOcorrencia, String rua, double cep, String bairro, String uf, Date dataOcorrencia,
                           String descricaoAcontecido, String descricaoPessoasEnvolvidas, int quantidadeCoisasRoubadas,
                           String cfpUsu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, tipoOcorrencia);
        content_values.put(COL_3, rua);
        content_values.put(COL_4, cep);
        content_values.put(COL_5, bairro);
        content_values.put(COL_6, uf);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dataOcorrencia);
        content_values.put(COL_7, reportDate);
        content_values.put(COL_8, descricaoAcontecido);
        content_values.put(COL_9, descricaoPessoasEnvolvidas);
        content_values.put(COL_10, quantidadeCoisasRoubadas);
        content_values.put(COL_11, cfpUsu);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listBO() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor boo = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return boo;
    }


    public boolean updateBO(String id, String tipoOcorrencia, String rua, double cep, String bairro, String uf,
                            Date dataOcorrencia, String descricaoAcontecido, String descricaoPessoasEnvolvidas,
                            int quantidadeCoisasRoubadas, String cfpUsu) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, tipoOcorrencia);
        content_values.put(COL_3, rua);
        content_values.put(COL_4, cep);
        content_values.put(COL_5, bairro);
        content_values.put(COL_6, uf);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(dataOcorrencia);
        content_values.put(COL_7, reportDate);
        content_values.put(COL_8, descricaoAcontecido);
        content_values.put(COL_9, descricaoPessoasEnvolvidas);
        content_values.put(COL_10, quantidadeCoisasRoubadas);
        content_values.put(COL_11, cfpUsu);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }


    public Integer delete_bo(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
