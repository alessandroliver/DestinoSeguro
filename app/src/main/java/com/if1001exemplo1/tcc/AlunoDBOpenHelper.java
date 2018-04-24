package com.if1001exemplo1.tcc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alessandro on 21/05/2017.
 */
//classe para criação de tabelas e DBs
public class AlunoDBOpenHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "DBAluno";

    static final String TABLE_ALUNO = "TableAluno";
    static final String TABLE_ALUN_DISC = "TableAlunoDisc";

    static final String NOME = "nome";
    static final String CPF = "cpf";
    static final String RG = "rg";
    static final String NACIONALIDADE = "nacionalidade";
    static final String NATURALIDADE = "naturalidade";
    static final String SEXO = "sexo";
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
    static final String TURMA = "turma";
    static final String COTA = "cota";
    static final String CURSO = "curso";
    static final String MATRICULA = "matricula";
    static final String DATAMATRICULA = "dataMat";
    static final String NOTAENTRADA = "notaEnt";
    static final String HORASCURSADAS = "horasCur";
    static final String HORASRESTANTES = "horasRes";
    static final String MEDIA1BOLETIM = "media1Bol";
    static final String MEDIA2BOLETIM = "media2Bol";
    static final String MEDIA3BOLETIM = "media3Bol";
    static final String MEDIA4BOLETIM = "media4Bol";
    static final String MEDIAGERALBOLETIM = "mediaGerBol";
    static final String RECUPERACAOBOLETIM = "recuperacaoBol";
    static final String SENHA = "senha";
    //coluna a ser usada nas querys do DB
    static final String[]COLUNA = {NOME, CPF, RG, NACIONALIDADE, NATURALIDADE, SEXO, RUAENDERECO,
            BAIRROENDERECO, NUMEROENDERECO, CIDADEENDERECO, CEPENDERECO, ESTADOENDERECO, COMPLEMENTOENDERECO,
            DATA_NASCIMENTO, OPERADORATELEFONE, NUMEROTELEFONE, DDDTELEFONE, EMAIL,SENHA, TURMA, COTA, CURSO,
            MATRICULA, DATAMATRICULA, NOTAENTRADA, HORASCURSADAS, HORASRESTANTES, MEDIA1BOLETIM, MEDIA2BOLETIM,
            MEDIA3BOLETIM, MEDIA4BOLETIM, MEDIAGERALBOLETIM, RECUPERACAOBOLETIM, SENHA};


    static final String NOMEDISCIPLINA = "nomeDis";
    static final String CODIGODISCIPLINA = "codigoDis";
    static final String DATAINICIODISCIPLINA = "dataInicioDis";
    static final String DATAFIMDISCIPLINA = "dataFimDis";
    static final String NOTA1DISCIPLINA = "nota1Dis";
    static final String NOTA2DISCIPLINA = "nota2Dis";
    static final String NOTA3DISCIPLINA = "nota3Dis";
    static final String NOTA4DISCIPLINA = "nota4Dis";
    static final String FALTASDISCIPLINA = "faltasDis";
    static final String CARGAHORARIADISCIPLINA = "cargaHorDis";
    static final String CPF_Disc = "cpfAlunoDisciplina";
    static final String[] COLUNA_DISCIPLINAS = {CPF_Disc, NOMEDISCIPLINA, CODIGODISCIPLINA,
            DATAINICIODISCIPLINA, DATAFIMDISCIPLINA, NOTA1DISCIPLINA, NOTA2DISCIPLINA, NOTA3DISCIPLINA,
            NOTA4DISCIPLINA, FALTASDISCIPLINA, CARGAHORARIADISCIPLINA};
    final private Context mContext;

    public AlunoDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //criação de tabela dos alunos
        String CREATE_TABLE = "CREATE TABLE " + TABLE_ALUNO+"(" + NOME + " TEXT NOT NULL,"
                + CPF + " TEXT PRIMARY KEY," + RG + " TEXT," + NACIONALIDADE + " TEXT,"  + SEXO + " TEXT,"
                + NATURALIDADE + " TEXT," + RUAENDERECO + " TEXT," + BAIRROENDERECO + " TEXT,"
                + NUMEROENDERECO + " INTEGER," + CIDADEENDERECO + " TEXT," + CEPENDERECO + " TEXT,"
                + ESTADOENDERECO + " TEXT," + COMPLEMENTOENDERECO + " TEXT," + DATA_NASCIMENTO + " TEXT,"
                + OPERADORATELEFONE + " TEXT," + NUMEROTELEFONE + " INTEGER," + DDDTELEFONE + " INTEGER,"
                + EMAIL + " TEXT," + SENHA + " TEXT," + TURMA + " TEXT," + COTA + " INTEGER,"
                + CURSO + " TEXT," + MATRICULA + " INTEGER," + DATAMATRICULA + " TEXT,"
                + NOTAENTRADA + " REAL," + HORASCURSADAS + " INTEGER," + HORASRESTANTES + " INTEGER,"
                + MEDIA1BOLETIM + " REAL," + MEDIA2BOLETIM + " REAL," + MEDIA3BOLETIM + " REAL,"
                + MEDIA4BOLETIM + " REAL," + MEDIAGERALBOLETIM + " REAL,"+ RECUPERACAOBOLETIM + " REAL" +")";

        //criação da tabela para as disciplinas do aluno
        //porque aluno tem uma Lista de disciplinas
        //SQLite não suporta listas em suas tabelas
        // a alternativa foi criar uma tabela para cada disciplina do aluno
        //cada disciplina do aluno é identificada pelo cpf do aluno

        String CREATE_TABLE_ALUN_DISC = "CREATE TABLE " + TABLE_ALUN_DISC+"(" + CPF_Disc + " TEXT,"
                +NOMEDISCIPLINA + " TEXT," + CODIGODISCIPLINA + " INTEGER," + DATAINICIODISCIPLINA + " TEXT,"
                + DATAFIMDISCIPLINA + " TEXT," + NOTA1DISCIPLINA + " REAL," + NOTA2DISCIPLINA + " REAL,"
                + NOTA3DISCIPLINA + " REAL," + NOTA4DISCIPLINA + " REAL," + FALTASDISCIPLINA + " INTEGER,"
                + CARGAHORARIADISCIPLINA + " REAL" +")";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_ALUN_DISC);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALUNO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALUN_DISC);


        onCreate(db);

    }

    void deleteDatabase() {
        mContext.deleteDatabase(TABLE_ALUNO);
    }

}