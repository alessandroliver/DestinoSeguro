package com.if1001exemplo1.tcc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alessandro on 21/05/2017.
 */

public class DisciplinaDBOpenHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "DBDisciplina";

    static final String TABLE_DISCIPLINA = "TableDisciplina";

    static final String NOME = "nome";
    static final String CODIGO = "codigo";
    static final String DATA_INICIO = "dataInicio";
    static final String DATA_FIM = "dataFim";
    static final String NOTA1 = "nota1";
    static final String NOTA2 = "nota2";
    static final String NOTA3 = "nota3";
    static final String NOTA4 = "nota4";
    static final String FALTAS = "faltas";
    static final String CARGAHORARIA = "cargaHoraria";
    static final String[]COLUNA = {NOME, CODIGO, DATA_INICIO, DATA_FIM, NOTA1, NOTA2, NOTA3, NOTA4, FALTAS, CARGAHORARIA};
    final private Context mContext;

    public DisciplinaDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_DISCIPLINA+"(" + NOME + " TEXT NOT NULL,"
                + CODIGO + " INTEGER PRIMARY KEY AUTOINCREMENT," + DATA_INICIO + " TEXT,"
                + DATA_FIM + " TEXT," + NOTA1 + " REAL," + NOTA2 + " REAL," + NOTA3 + " REAL,"
                + NOTA4 + " REAL," + FALTAS + " INTEGER," + CARGAHORARIA + " REAL" +")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINA);

        onCreate(db);

    }

    void deleteDatabase() {
        mContext.deleteDatabase(TABLE_DISCIPLINA);
    }

}
