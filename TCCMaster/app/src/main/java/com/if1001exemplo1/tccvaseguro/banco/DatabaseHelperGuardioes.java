package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.if1001exemplo1.tccvaseguro.basicas.Guardioes;
import com.if1001exemplo1.tccvaseguro.basicas.User;
import com.if1001exemplo1.tccvaseguro.old.Guard;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseHelperGuardioes extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "guardioes.db";
    public static final String TABLE_NAME = "guardioes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Nome_guard";
    public static final String COL_3 = "Sobrenome_guar";
    public static final String COL_4 = "email_guar";
    public static final String COL_5 = "cidade_guar";
    public static final String COL_6 = "uf_guar";
    public static final String COL_7 = "sexo_guar";
    public static final String COL_8 = "nascimento_guar";
    public static final String COL_9 = "celular_guar";
    public static final String COL_10 = "operadora_guar";
    public static final String COL_11 = "nacionalidade_guar";
    public static final String COL_12 = "cep_guar";
    public static final String COL_13 = "parentesco_guar";

    static final String TABLE_USER = "user_de_guard";
    static final String CPF_USER = "guardian_nome";
    static final String CPF_GUARD = "usuar_cpf";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " TEXT, " +
            COL_5 + " TEXT, " +
            COL_6 + " TEXT, " +
            COL_7 + " TEXT, " +
            COL_8 + " INTEGER, " +
            COL_9 + " TEXT, " +
            COL_10 + " TEXT, " +
            COL_11 + " TEXT, " +
            COL_12 + " REAL, " +
            COL_13 + " TEXT );";
    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER+"(" + CPF_USER + " TEXT,"
                + CPF_GUARD  + " TEXT" +")";

        db.execSQL(CREATE_USER_TABLE);

        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperGuardioes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean salvaGuardioes(String nome, String sobrenome, String email, String cidade, String uf, String sexo,
                                  Date nascimento, int numero_celular, String operadora, String nacionalidade, double cep,
                                  String parentesco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, nome);
        content_values.put(COL_3, sobrenome);
        content_values.put(COL_4, email);
        content_values.put(COL_5, cidade);
        content_values.put(COL_6, uf);
        content_values.put(COL_7, sexo);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(nascimento);
        content_values.put(COL_8, reportDate);
        content_values.put(COL_9, numero_celular);
        content_values.put(COL_10, operadora);
        content_values.put(COL_11, nacionalidade);
        content_values.put(COL_12, cep);
        content_values.put(COL_13, parentesco);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listGuardioes() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor guar = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return guar;
    }

    public boolean salvaUserdeGuardian(String cpfUser, String nomeGuardian) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(CPF_GUARD, cpfUser);
        content_values.put(CPF_USER, nomeGuardian);
        long result = db.insert(TABLE_USER, null, content_values);
        return result != -1;
    }

    public List<String> listGuardianUser(String nomeGuardian) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor us = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        List<String> cpfUs = new ArrayList<String>();
        while (us.moveToNext()) {
            if(us.getString(0).equals(nomeGuardian)){
                cpfUs.add(us.getString(1));
            }
        }
        return cpfUs;
    }

    public Cursor getGuardian(String nome){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor user = db.rawQuery("SELECT * FROM " + TABLE_NAME + "WHERE Nome_guard = " + nome, null);
        return user;
    }


    public boolean updateGuardioes(String id, String nome, String sobrenome, String email, String cidade, String uf,
                                 String sexo, Date nascimento, int numero_celular, String operadora, String nacionalidade,
                                 double cep, String parentesco) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, nome);
        content_values.put(COL_3, sobrenome);
        content_values.put(COL_4, email);
        content_values.put(COL_5, cidade);
        content_values.put(COL_6, uf);
        content_values.put(COL_7, sexo);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(nascimento);
        content_values.put(COL_8, reportDate);
        content_values.put(COL_9, numero_celular);
        content_values.put(COL_10, operadora);
        content_values.put(COL_11, nacionalidade);
        content_values.put(COL_12, cep);
        content_values.put(COL_13, parentesco);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }


    public Integer delete_guardioes(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public Integer delete_guard(String cpfUs) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "usuar_cpf = ?", new String[] {cpfUs});
    }

    public List<Guardioes> getGuardians(List<String> nomeGuardians){
        List<Guardioes> us = null;

        Cursor gua = listGuardioes();

        while (gua.moveToNext()) {
            for (int i = 0; i < nomeGuardians.size(); i++) {
                if(gua.getString(12).equals(nomeGuardians.get(i))){

                    DateFormat dateUsu = new SimpleDateFormat("MM/dd/yyyy");
                    Date nasci = null;
                    try {
                        nasci = dateUsu.parse( gua.getString(7).toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    List<String> g =  listGuardianUser(gua.getString(12));
                    //DatabaseHelperGuardioes bdGuar = new DatabaseHelperGuardioes(this.context);

                    Guardioes guardioes = new Guardioes(gua.getInt(0), gua.getString(1), gua.getString(2), gua.getString(3),
                            gua.getString(4), gua.getString(5), gua.getString(6), nasci, gua.getInt(8),
                            gua.getString(9), gua.getString(10), gua.getDouble(11), gua.getString(12), null);


                    us.add(guardioes);

                }
            }
        }
        return us;
    }



}
