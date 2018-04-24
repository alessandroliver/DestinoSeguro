package com.if1001exemplo1.tcc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.if1001exemplo1.tcc.AlunoDBOpenHelper.TABLE_ALUNO;
import static com.if1001exemplo1.tcc.AlunoDBOpenHelper.TABLE_ALUN_DISC;

/**
 * Created by Alessandro on 23/05/2017.
 */

    //cmanipulações do Banco de dados
public class AlunoDBController {

    private SQLiteDatabase db;
    private AlunoDBOpenHelper alunoDB;

    public AlunoDBController(Context context){
        alunoDB = new AlunoDBOpenHelper(context);
    }


    //método que retorna a lista de todos os alunos
    public List<Aluno> getAllAluno() throws ParseException {
        List<Aluno> alunoList = new ArrayList<Aluno>();
        Cursor cursor = loadItems();


        if (cursor.moveToFirst()) {
            do {
                //resgata as informações dos alunos que estão no cursor
                Endereco end = new Endereco(cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9),
                        cursor.getString(10),cursor.getString(11),cursor.getString(12));
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = df.parse(cursor.getString(13));
                Telefone tel = new Telefone(cursor.getString(14),cursor.getInt(15),cursor.getInt(16));
                boolean cot = false;
                if (cursor.getInt(20) == 1){
                    cot = true;
                }
                DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");
                Date dt = null;
                String dataM = cursor.getString(23);

                if(dataM!= null) {
                     dt = dft.parse(dataM);

                }
                String cpf = cursor.getString(1);
               List<Disciplina> listDisciplinas = null;
                listDisciplinas = getAllDisciplinasAluno(cpf);

                Boletim bol = new Boletim(cursor.getDouble(27),cursor.getDouble(28),cursor.getDouble(29),cursor.getDouble(30),
                        cursor.getDouble(31),cursor.getDouble(32),listDisciplinas);
                Aluno alu = new Aluno(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),end,date,tel,cursor.getString(17),cursor.getString(18),
                        cursor.getString(19),cot,cursor.getString(21),cursor.getInt(22),dt,cursor.getDouble(24),cursor.getInt(25),
                        cursor.getInt(26),listDisciplinas,bol);

                //adiciona cada aluno resgatado dentro da lista a ser retornada
                alunoList.add(alu);
            } while (cursor.moveToNext());
        }
        //cursor fecha
        cursor.close();

        return alunoList;
    }

    //método para carregar os alunos da tabela de alunos
    //retorna um cursor a ser usado em getAllAluno
    public Cursor loadItems(){
        Cursor cursor;
        //abrindo o bd pra leitura
        db = alunoDB.getReadableDatabase();
        //faz a requisição das colunas que tá em alunoDB.COLUNA da tabela de aluno
        cursor = db.query(TABLE_ALUNO, alunoDB.COLUNA, null, null, null, null, null, null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        //fecha
        db.close();
        return cursor;
    }

    //método para inserir Aluno
    public String insert(Aluno aluno){
        //checa se o bd foi criado
        long check =0;
        //abrir o bd pra escrita
        SQLiteDatabase db = alunoDB.getWritableDatabase();
        //serve para colocar os valores
        ContentValues values = new ContentValues();

        values.put(AlunoDBOpenHelper.NOME, aluno.getNome());
        values.put(AlunoDBOpenHelper.CPF, aluno.getCpf());
        values.put(AlunoDBOpenHelper.RG, aluno.getRg());
        values.put(AlunoDBOpenHelper.NACIONALIDADE, aluno.getNacionalidade());
        values.put(AlunoDBOpenHelper.SEXO, aluno.getSexo());
        values.put(AlunoDBOpenHelper.NATURALIDADE, aluno.getNaturalidade());
        values.put(AlunoDBOpenHelper.RUAENDERECO, aluno.getEndereco().getRua_av());
        values.put(AlunoDBOpenHelper.BAIRROENDERECO, aluno.getEndereco().getBairro());
        values.put(AlunoDBOpenHelper.NUMEROENDERECO, aluno.getEndereco().getNum());
        values.put(AlunoDBOpenHelper.CIDADEENDERECO, aluno.getEndereco().getCidade());
        values.put(AlunoDBOpenHelper.CEPENDERECO, aluno.getEndereco().getCep());
        values.put(AlunoDBOpenHelper.ESTADOENDERECO, aluno.getEndereco().getEstado());
        values.put(AlunoDBOpenHelper.COMPLEMENTOENDERECO, aluno.getEndereco().getComplemento());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(aluno.getData_nascimento());

        values.put(AlunoDBOpenHelper.DATA_NASCIMENTO, reportDate);
        values.put(AlunoDBOpenHelper.OPERADORATELEFONE, aluno.getTelefone().getOperadora());
        values.put(AlunoDBOpenHelper.NUMEROTELEFONE, aluno.getTelefone().getNumero());
        values.put(AlunoDBOpenHelper.DDDTELEFONE, aluno.getTelefone().getDdd());
        values.put(AlunoDBOpenHelper.EMAIL, aluno.getEmail());
        values.put(AlunoDBOpenHelper.SENHA, aluno.getSenha());
        values.put(AlunoDBOpenHelper.TURMA, aluno.getTurma());
        values.put(AlunoDBOpenHelper.COTA, aluno.getCota());
        values.put(AlunoDBOpenHelper.CURSO, aluno.getCurso());
        values.put(AlunoDBOpenHelper.MATRICULA, aluno.getMatricula());
        DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");
        String reportDt = null;
        if(aluno.getData_matricula() != null){

            reportDt = dft.format(aluno.getData_matricula());
        }

        values.put(AlunoDBOpenHelper.DATAMATRICULA, reportDt);
        values.put(AlunoDBOpenHelper.NOTAENTRADA, aluno.getNota_entrada());
        values.put(AlunoDBOpenHelper.HORASCURSADAS, 0);
        values.put(AlunoDBOpenHelper.HORASRESTANTES, 0);

        values.put(AlunoDBOpenHelper.MEDIA1BOLETIM, 0);
        values.put(AlunoDBOpenHelper.MEDIA2BOLETIM, 0);
        values.put(AlunoDBOpenHelper.MEDIA3BOLETIM, 0);
        values.put(AlunoDBOpenHelper.MEDIA4BOLETIM, 0);
        values.put(AlunoDBOpenHelper.MEDIAGERALBOLETIM, 0);
        values.put(AlunoDBOpenHelper.RECUPERACAOBOLETIM, 0);

        //insiro no BD
        check = db.insert(TABLE_ALUNO, null, values);

        //sempre que abre, fecha
        db.close();

        //se o método insert deu erro, retorna -1
        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    //método que insere as disciplinas de um determinado aluno
    public String insertDisciplinaAluno(Disciplina disciplina, String cpf){
        long check =0;
        SQLiteDatabase db = alunoDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AlunoDBOpenHelper.CPF_Disc, cpf);
        values.put(AlunoDBOpenHelper.NOMEDISCIPLINA, disciplina.getNome());
        values.put(AlunoDBOpenHelper.CODIGODISCIPLINA, disciplina.getCodigo());

        String reportD = null;
        if(disciplina.getData_inicio()!= null){
            DateFormat dfr = new SimpleDateFormat("MM/dd/yyyy");
            reportD = dfr.format(disciplina.getData_inicio());
        }

        values.put(AlunoDBOpenHelper.DATAINICIODISCIPLINA, reportD);

        String reportDe = null;
        if(disciplina.getData_fim() != null){
            DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy");
            reportDe = dfm.format(disciplina.getData_fim());
        }

        values.put(AlunoDBOpenHelper.DATAFIMDISCIPLINA, reportDe);
        values.put(AlunoDBOpenHelper.NOTA1DISCIPLINA, 0);
        values.put(AlunoDBOpenHelper.NOTA2DISCIPLINA, 0);
        values.put(AlunoDBOpenHelper.NOTA3DISCIPLINA, 0);
        values.put(AlunoDBOpenHelper.NOTA4DISCIPLINA, 0);
        values.put(AlunoDBOpenHelper.FALTASDISCIPLINA, 0);


            check = db.insert(TABLE_ALUN_DISC, null, values);



        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    //carrega as disciplinas de um aluno específico

    public Cursor loadDisciplinaAluno(String cpf){
        db = alunoDB.getReadableDatabase();
        //retorna as disciplinas onde o cpf de aluno da tabela é igual ao cpf fornecido
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ALUN_DISC
                + " WHERE cpfAlunoDisciplina = '" + cpf + "'", new String[] {});
        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    //método que retorna todas as disciplinas de um determinado aluno ( pelo cpf)
    public List<Disciplina> getAllDisciplinasAluno(String cpf) throws ParseException {
        List<Disciplina> disciplinasAluno =null;
        Cursor cursor = loadDisciplinaAluno(cpf);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            disciplinasAluno = new ArrayList<Disciplina>();
            do {

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                if(cursor.getString(3) != null){
                    datei = df.parse(cursor.getString(3));
                }

                Date datef = null;
                if(cursor.getString(4) != null){
                    datef = df.parse(cursor.getString(4));
                }

                Disciplina disciplina = new Disciplina(cursor.getString(1),cursor.getInt(2),datei, datef,
                        cursor.getDouble(5),cursor.getDouble(6),cursor.getDouble(7),cursor.getDouble(8), cursor.getInt(9), cursor.getDouble(10));

                //Adiona a disciplina na lista de disciplinas a ser retornada
                disciplinasAluno.add(disciplina);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return disciplinasAluno;
    }



    //método pra deletar tabela
    public void clearAll(){
        alunoDB.getWritableDatabase().delete(TABLE_ALUNO,null,null);

    }

    //retorna um aluno específico por meio de seu cpf
    public Aluno getAluno(String cpf) throws ParseException {
        SQLiteDatabase db = alunoDB.getWritableDatabase();
        Aluno aluno = null;
        //retorna apenas um aluno com esse cpf dado
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ALUNO
                + " WHERE cpf = '" + cpf + "'", new String[] {});
        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
       if(cursor.moveToFirst()){
           Endereco end = new Endereco(cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9),
                   cursor.getString(10),cursor.getString(11),cursor.getString(12));
           DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
           Date date = df.parse(cursor.getString(13));
           Telefone tel = new Telefone(cursor.getString(14),cursor.getInt(15),cursor.getInt(16));
           boolean cot = false;
           if (cursor.getInt(20) == 1){
               cot = true;
           }
           Date dt = null;
           if(cursor.getString(23)!= null) {
               DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");
               dt = dft.parse(cursor.getString(23));

           }
           List<Disciplina> listDisciplinas = null;
           listDisciplinas = getAllDisciplinasAluno(cpf);

           Boletim bol = new Boletim(cursor.getDouble(27),cursor.getDouble(28),cursor.getDouble(29),cursor.getDouble(30),
                   cursor.getDouble(31),cursor.getDouble(32),listDisciplinas);
           aluno = new Aluno(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                   cursor.getString(4),cursor.getString(5),end,date,tel,cursor.getString(17),cursor.getString(18),
                   cursor.getString(19),cot,cursor.getString(21),cursor.getInt(22),dt,cursor.getDouble(24),cursor.getInt(25),
                   cursor.getInt(26),listDisciplinas,bol);

       }
        return aluno;

    }

    //atualizar disciplina de um aluno
    //usá-se quando o professor atribui as notas
    public int updateDisciplina(Disciplina disciplina, String cpf) {
        SQLiteDatabase db = alunoDB.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AlunoDBOpenHelper.CPF_Disc, cpf);
        values.put(AlunoDBOpenHelper.NOMEDISCIPLINA, disciplina.getNome());
        values.put(AlunoDBOpenHelper.CODIGODISCIPLINA, disciplina.getCodigo());

        String reportD = null;
        if(disciplina.getData_inicio()!= null){
            DateFormat dfr = new SimpleDateFormat("MM/dd/yyyy");
            reportD = dfr.format(disciplina.getData_inicio());
        }

        values.put(AlunoDBOpenHelper.DATAINICIODISCIPLINA, reportD);

        String reportDe = null;
        if(disciplina.getData_fim() != null){
            DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy");
            reportDe = dfm.format(disciplina.getData_fim());
        }

        values.put(AlunoDBOpenHelper.DATAFIMDISCIPLINA, reportDe);
        values.put(AlunoDBOpenHelper.NOTA1DISCIPLINA, disciplina.getNota1());
        values.put(AlunoDBOpenHelper.NOTA2DISCIPLINA, disciplina.getNota2());
        values.put(AlunoDBOpenHelper.NOTA3DISCIPLINA, disciplina.getNota3());
        values.put(AlunoDBOpenHelper.NOTA4DISCIPLINA, disciplina.getNota4());
        values.put(AlunoDBOpenHelper.FALTASDISCIPLINA, disciplina.getFaltas());


        int update = db.update(TABLE_ALUN_DISC, values, AlunoDBOpenHelper.CODIGODISCIPLINA + " = ?",
                new String[]{String.valueOf(disciplina.getCodigo())});
        db.close();
        return update;

    }

    //atualiza as informações do aluno
    //usá-se quando se faz a matricula
    public int updateSite(Aluno aluno){
        SQLiteDatabase db = alunoDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AlunoDBOpenHelper.NOME, aluno.getNome());
        values.put(AlunoDBOpenHelper.CPF, aluno.getCpf());
        values.put(AlunoDBOpenHelper.RG, aluno.getRg());
        values.put(AlunoDBOpenHelper.NACIONALIDADE, aluno.getNacionalidade());
        values.put(AlunoDBOpenHelper.NATURALIDADE, aluno.getNaturalidade());
        values.put(AlunoDBOpenHelper.SEXO, aluno.getSexo());
        values.put(AlunoDBOpenHelper.RUAENDERECO, aluno.getEndereco().getRua_av());
        values.put(AlunoDBOpenHelper.BAIRROENDERECO, aluno.getEndereco().getBairro());
        values.put(AlunoDBOpenHelper.NUMEROENDERECO, aluno.getEndereco().getNum());
        values.put(AlunoDBOpenHelper.CIDADEENDERECO, aluno.getEndereco().getCidade());
        values.put(AlunoDBOpenHelper.CEPENDERECO, aluno.getEndereco().getCep());
        values.put(AlunoDBOpenHelper.ESTADOENDERECO, aluno.getEndereco().getEstado());
        values.put(AlunoDBOpenHelper.COMPLEMENTOENDERECO, aluno.getEndereco().getComplemento());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(aluno.getData_nascimento());

        values.put(AlunoDBOpenHelper.DATA_NASCIMENTO, reportDate);
        values.put(AlunoDBOpenHelper.OPERADORATELEFONE, aluno.getTelefone().getOperadora());
        values.put(AlunoDBOpenHelper.NUMEROTELEFONE, aluno.getTelefone().getNumero());
        values.put(AlunoDBOpenHelper.DDDTELEFONE, aluno.getTelefone().getDdd());
        values.put(AlunoDBOpenHelper.EMAIL, aluno.getEmail());
        values.put(AlunoDBOpenHelper.TURMA, aluno.getTurma());
        values.put(AlunoDBOpenHelper.COTA, aluno.getCota());
        values.put(AlunoDBOpenHelper.CURSO, aluno.getCurso());
        values.put(AlunoDBOpenHelper.MATRICULA, aluno.getMatricula());

        DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");
        String reportDt = null;
        if(aluno.getData_matricula() != null){
            reportDt = dft.format(aluno.getData_matricula());
        }


        values.put(AlunoDBOpenHelper.DATAMATRICULA, reportDt);
        values.put(AlunoDBOpenHelper.NOTAENTRADA, aluno.getNota_entrada());
        values.put(AlunoDBOpenHelper.HORASCURSADAS, aluno.getHoras_cursadas());
        values.put(AlunoDBOpenHelper.HORASRESTANTES, aluno.getHoras_restantes());
        values.put(AlunoDBOpenHelper.MEDIA1BOLETIM, aluno.getBoletim().getMedia1());
        values.put(AlunoDBOpenHelper.MEDIA2BOLETIM, aluno.getBoletim().getMedia2());
        values.put(AlunoDBOpenHelper.MEDIA3BOLETIM, aluno.getBoletim().getMedia3());
        values.put(AlunoDBOpenHelper.MEDIA4BOLETIM, aluno.getBoletim().getMedia4());
        values.put(AlunoDBOpenHelper.MEDIAGERALBOLETIM, aluno.getBoletim().getMedia_geral());
        values.put(AlunoDBOpenHelper.RECUPERACAOBOLETIM, aluno.getBoletim().getRecuperacao());
        values.put(AlunoDBOpenHelper.SENHA, aluno.getSenha());
        int update = db.update(TABLE_ALUNO, values, AlunoDBOpenHelper.CPF + " = ?",
                new String[]{String.valueOf(aluno.getCpf())});
        db.close();
        return update;

    }

}
