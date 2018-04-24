package com.if1001exemplo1.tccvaseguro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UsuarioDBOpenHelper extends SQLiteOpenHelper{

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "DBUsuario";

    static final String TABLE_USUARIO = "TableUsuario";

    static final String NOME = "nome";
    static final String SOBRENOME = "sobrenome";
    static final String EMAIL = "email";
    static final String CIDADE = "cidade";
    static final String UF = "uf";
    static final String SENHA = "senha";
    static final String RECEBER_EMAIL = "receber_email";
    static final String SEXO = "sexo";
    static final String NASCIMENTO = "nascimento";
    static final String NUMERO_CELULAR = "numero_celular";

    //coluna a ser usada nas querys do DB
    static final String[]COLUNA = {NOME, SOBRENOME, EMAIL, CIDADE, UF, SENHA, RECEBER_EMAIL, SEXO,
            NASCIMENTO, NUMERO_CELULAR};

    final private Context mContext;

    public UsuarioDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //criação de tabela do usuário
        String CREATE_TABLE = "CREATE TABLE " + TABLE_USUARIO+"(" + NOME + " TEXT NOT NULL,"
                + SOBRENOME + " TEXT," + EMAIL + " TEXT," + CIDADE + " TEXT,"  + UF + " TEXT,"
                + SENHA + " TEXT PRIMARY KEY," + RECEBER_EMAIL + " TEXT," + SEXO + " TEXT,"
                + NASCIMENTO + " TEXT," + NUMERO_CELULAR + " INTEGER" +")";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);

        onCreate(db);
    }

    void deleteDatabase() {
        mContext.deleteDatabase(TABLE_USUARIO);
    }

}
