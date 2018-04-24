package com.if1001exemplo1.tcc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alessandro on 21/05/2017.
 */

public class LivroDBOpenHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "DBLivro";

    static final String TABLE_LIVRO = "TableLivro";

    static final String IDIOMA = "idioma";
    static final String TITULO = "titulo";
    static final String AREA = "area";
    static final String AUTOR = "autor";
    static final String EDITORA = "editora";
    static final String EDICAO = "edicao";
    static final String DATA_PUBLICACAO = "dataPub";
    static final String _ID = "_id";
    static final String EMPRESTADO = "emprestado";
    static final String CPF_ALUNO = "cpfAluno";
    static final String DATA_RENOVACAO = "dataRen";
    static final String DATA_EMPRESTIMO = "dataEmp";
    static final String[]COLUNA = {IDIOMA, TITULO, AREA, AUTOR, EDITORA, EDICAO, DATA_PUBLICACAO, _ID, EMPRESTADO, DATA_RENOVACAO, DATA_EMPRESTIMO, CPF_ALUNO};
    final private Context mContext;

    public LivroDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LIVRO+"(" + IDIOMA + " TEXT,"
                + TITULO + " TEXT NOT NULL," + AREA + " TEXT," + AUTOR + " TEXT,"
                + EDITORA + " TEXT," + EDICAO + " INTEGER," + DATA_PUBLICACAO + " TEXT,"
                + _ID + " INTEGER," + EMPRESTADO + " INTEGER," + DATA_RENOVACAO + " TEXT," + DATA_EMPRESTIMO + " TEXT," +
                CPF_ALUNO + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRO);

        onCreate(db);

    }

    void deleteDatabase() {
        mContext.deleteDatabase(TABLE_LIVRO);
    }

}
