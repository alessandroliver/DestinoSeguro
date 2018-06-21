package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.if1001exemplo1.tccvaseguro.basicas.Guardioes;
import com.if1001exemplo1.tccvaseguro.basicas.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseHelperUsuario extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "usuario.db";
    public static final String TABLE_NAME = "usuario";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Nome";
    public static final String COL_3 = "Sobrenome";
    public static final String COL_4 = "email";
    public static final String COL_5 = "cidade";
    public static final String COL_6 = "uf";
    public static final String COL_7 = "sexo";
    public static final String COL_8 = "nascimento";
    public static final String COL_9 = "celular";
    public static final String COL_10 = "operadora";
    public static final String COL_11 = "nacionalidade";
    public static final String COL_12 = "cep";
    public static final String COL_13 = "cpf";
    public static final String COL_14 = "senha";

    static final String TABLE_GUARDIAN = "guardiao";
    static final String CPF_USER = "user_cpf";
    static final String CPF_GUARD = "guard_nome";

    public Context context;


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
            COL_13 + " TEXT, " +
            COL_14 + " TEXT );";
    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_GUARD_TABLE = "CREATE TABLE " + TABLE_GUARDIAN+"(" + CPF_GUARD + " TEXT,"
                + CPF_USER  + " TEXT" +")";

        db.execSQL(CREATE_GUARD_TABLE);
        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperUsuario(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GUARDIAN);

        onCreate(db);
    }


    public boolean salvaUsusario(String nome, String sobrenome, String email, String cidade, String uf, String sexo, Date nascimento,
                                 int numero_celular, String operadora, String nacionalidade, double cep, String cpf, String senha) {
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
        content_values.put(COL_13, cpf);
        content_values.put(COL_14, senha);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listUsuario() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor user = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return user;
    }





    public Cursor getUsuario(String cpf){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor user = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE cpf = " + cpf, null);
        return user;
    }


    public boolean updateUsuario(String id, String nome, String sobrenome, String email, String cidade, String uf, String sexo, Date nascimento,
                                 int numero_celular, String operadora, String nacionalidade, double cep, String cpf, String senha) {

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
        content_values.put(COL_13, cpf);
        content_values.put(COL_14, senha);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }


    public Integer delete_student(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public List<User> getUsers(List<String> cpfsUsers){
        List<User> us = null;

        Cursor usu = listUsuario();

        while (usu.moveToNext()) {
            for (int i = 0; i < cpfsUsers.size(); i++) {
                if(usu.getString(12).equals(cpfsUsers.get(i))){

                    DateFormat dateUsu = new SimpleDateFormat("MM/dd/yyyy");
                    Date nasci = null;
                    try {
                        nasci = dateUsu.parse( usu.getString(7).toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    List<String> g =  listGuardianUser(usu.getString(12));
                    DatabaseHelperGuardioes bdGuar = new DatabaseHelperGuardioes(this.context);

                  User usuario = new User(usu.getInt(0), usu.getString(1), usu.getString(2), usu.getString(3),
                                usu.getString(4), usu.getString(5), usu.getString(6), nasci, usu.getInt(8),
                                usu.getString(9), usu.getString(10), usu.getDouble(11), usu.getString(12), usu.getString(13), bdGuar.getGuardians(g));


                    us.add(usuario);

                }
            }
        }
            return us;
        }

    //Relacionados aos guardiãos de usuer

    public boolean salvaGuardianUser(String cpfUser, String nomeGuardian) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(CPF_USER, cpfUser);
        content_values.put(CPF_GUARD, nomeGuardian);
        long result = db.insert(TABLE_GUARDIAN, null, content_values);
        return result != -1;
    }


    public List<String> listGuardianUser(String cpfUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gd = db.rawQuery("SELECT * FROM " + TABLE_GUARDIAN, null);
        List<String> cpfGuardiaos = new ArrayList<String>();
        while (gd.moveToNext()) {
            if(gd.getString(1).equals(cpfUser)){
                cpfGuardiaos.add(gd.getString(0));
            }
        }
        return cpfGuardiaos;
    }

    public Integer delete_guard(String nomeGuard) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "guard_nome = ?", new String[] {nomeGuard});
    }




}
