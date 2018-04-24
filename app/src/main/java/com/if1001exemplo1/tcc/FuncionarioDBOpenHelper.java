package com.if1001exemplo1.tcc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alessandro on 21/05/2017.
 */

public class FuncionarioDBOpenHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "DBFuncionario";

    static final String TABLE_FUNCIONARIO = "TableFuncionario";

    static final String NOME = "nome";
    static final String CPF = "cpf";
    static final String RG = "rg";
    static final String NACIONALIDADE = "nacionalidade";
    static final String SEXO = "sexo";
    static final String NATURALIDADE = "naturalidade";
    static final String RUAENDERECO = "ruaEnd";
    static final String BAIRROENDERECO = "bairroEnd";
    static final String NUMEROENDERECO = "numeroEnd";
    static final String CIDADEENDERECO = "cidadeEnd";
    static final String CEPENDERECO = "cepEnd";
    static final String ESTADOENDERECO = "estadoEnd";
    static final String COMPLEMENTOENDERECO = "complementoEnd";
    static final String DATA_NASCIMENTO = "dataNas";
    static final String OPERADORATELEFONE = "operadoraTel";
    static final String NUMEROTELEFONE = "numTel";
    static final String DDDTELEFONE = "dddTel";
    static final String EMAIL = "email";
    static final String SENHA = "senha";
    static final String SALARIO = "salario";
    static final String _ID = "_id";
    static final String CARGA_HORARIA = "cargaHr";
    static final String CARGO = "cargo";
    static final String[]COLUNA = {NOME, CPF, RG, NACIONALIDADE, SEXO, NATURALIDADE, RUAENDERECO,
    BAIRROENDERECO, NUMEROENDERECO, CIDADEENDERECO, CEPENDERECO, ESTADOENDERECO, COMPLEMENTOENDERECO,
    DATA_NASCIMENTO, OPERADORATELEFONE, NUMEROTELEFONE, DDDTELEFONE, EMAIL, SENHA, SALARIO, _ID,
            CARGA_HORARIA, CARGO};
    final private Context mContext;

    public FuncionarioDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_FUNCIONARIO+"(" + NOME + " TEXT NOT NULL,"
                + CPF + " TEXT," + RG + " TEXT," + NACIONALIDADE + " TEXT," + SEXO + " TEXT,"
                + NATURALIDADE + " TEXT," + RUAENDERECO + " TEXT," + BAIRROENDERECO + " TEXT,"
                + NUMEROENDERECO + " INTEGER," + CIDADEENDERECO + " TEXT," + CEPENDERECO + " TEXT,"
                + ESTADOENDERECO + " TEXT," + COMPLEMENTOENDERECO + " TEXT," + DATA_NASCIMENTO + " TEXT,"
                + OPERADORATELEFONE + " TEXT," + NUMEROTELEFONE + " INTEGER," + DDDTELEFONE + " INTEGER,"
                + EMAIL + " TEXT," + SENHA + " TEXT," + SALARIO + " REAL," + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CARGA_HORARIA + " REAL," + CARGO + " TEXT" +")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCIONARIO);

        onCreate(db);

    }

    void deleteDatabase() {
        mContext.deleteDatabase(TABLE_FUNCIONARIO);
    }

}
