package com.if1001exemplo1.tccvaseguro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelperMensagemEmail extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mensagemEmail.db";
    public static final String TABLE_NAME = "mensagemEmail";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "texto";
    public static final String COL_3 = "tempo_para_enviar_primeiro_alerta";
    public static final String COL_4 = "intervalo_de_tempo_mensagem";
    public static final String COL_5 = "tipo";
    public static final String COL_6 = "data";
    public static final String COL_7 = "horario";
    public static final String COL_8 = "latitude";
    public static final String COL_9 = "longitude";
    public static final String COL_10 = "origem";
    public static final String COL_11 = "destino";
    public static final String COL_12 = "email_destino";
    public static final String COL_13 = "from_email";
    public static final String COL_14 = "password";
    public static final String COL_15 = "assunto";
    public static final String COL_16 = "email_host";
    public static final String COL_17 = "email_port";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " REAL, " +
            COL_4 + " TEXT, " +
            COL_5 + " TEXT, " +
            COL_6 + " TEXT, " +
            COL_7 + " TEXT, " +
            COL_8 + " INTEGER, " +
            COL_9 + " TEXT, " +
            COL_10 + " TEXT, " +
            COL_11 + " TEXT, " +
            COL_12 + " REAL, " +
            COL_13 + " REAL, " +
            COL_14 + " REAL, " +
            COL_15 + " REAL, " +
            COL_16 + " REAL, " +
            COL_17 + " TEXT );";
    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }
    public DatabaseHelperMensagemEmail(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean salvaMensagemEmail(String texto, double tempo_para_enviar_primeiro_alerta, double intervalo_de_tempo_mensagem,
                                      String tipo, Date data, String horario, double latitude, double longitude, String origem,
                                      String destino, String email_destino, String from_email, String password, String assunto,
                                      String email_host, String email_port) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, texto);
        content_values.put(COL_3, tempo_para_enviar_primeiro_alerta);
        content_values.put(COL_4, intervalo_de_tempo_mensagem);
        content_values.put(COL_5, tipo);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(data);
        content_values.put(COL_6, reportDate);
        content_values.put(COL_7, horario);
        content_values.put(COL_8, latitude);
        content_values.put(COL_9, longitude);
        content_values.put(COL_10, origem);
        content_values.put(COL_11, destino);
        content_values.put(COL_12, email_destino);
        content_values.put(COL_13, from_email);
        content_values.put(COL_14, password);
        content_values.put(COL_15, assunto);
        content_values.put(COL_16, email_host);
        content_values.put(COL_17, email_port);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }


    public Cursor listMensagemEmail() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mEmail = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return mEmail;
    }


    public boolean updateMensagemEmail(String id, String texto, double tempo_para_enviar_primeiro_alerta, double intervalo_de_tempo_mensagem,
                                       String tipo, Date data, String horario, double latitude, double longitude, String origem,
                                       String destino, String email_destino, String from_email, String password, String assunto,
                                       String email_host, String email_port) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, texto);
        content_values.put(COL_3, tempo_para_enviar_primeiro_alerta);
        content_values.put(COL_4, intervalo_de_tempo_mensagem);
        content_values.put(COL_5, tipo);
        //converter data para string
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(data);
        content_values.put(COL_6, reportDate);
        content_values.put(COL_7, horario);
        content_values.put(COL_8, latitude);
        content_values.put(COL_9, longitude);
        content_values.put(COL_10, origem);
        content_values.put(COL_11, destino);
        content_values.put(COL_12, email_destino);
        content_values.put(COL_13, from_email);
        content_values.put(COL_14, password);
        content_values.put(COL_15, assunto);
        content_values.put(COL_16, email_host);
        content_values.put(COL_17, email_port);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;

    }

    public Integer delete_mensagemEmail(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

}
