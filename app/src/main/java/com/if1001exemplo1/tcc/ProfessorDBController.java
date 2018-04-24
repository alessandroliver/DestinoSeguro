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

import static com.if1001exemplo1.tcc.ProfessorDBOpenHelper.TABLE_PROFESSOR;

/**
 * Created by Alessandro on 27/05/2017.
 */

public class ProfessorDBController {

    private SQLiteDatabase db;
    private ProfessorDBOpenHelper professorDB;

    public ProfessorDBController(Context context){
        professorDB = new ProfessorDBOpenHelper(context);
    }

    public List<Professor> getAllProf() throws ParseException {
        List<Professor> professorList = new ArrayList<Professor>();
        Cursor cursor = loadItems();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Endereco end = new Endereco(cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9),
                        cursor.getString(10),cursor.getString(11),cursor.getString(12));
                Telefone tel = new Telefone(cursor.getString(14),cursor.getInt(15),cursor.getInt(16));
                Disciplina dis = new Disciplina(cursor.getString(23),0,null,null,0,0,0,0,0,0);
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = df.parse(cursor.getString(13));
                Professor prof = new Professor(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),end,date,tel,cursor.getString(17),cursor.getString(18),
                        cursor.getDouble(19),cursor.getInt(20),cursor.getDouble(21),cursor.getString(22),dis);

                // Adding contact to list
                professorList.add(prof);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return professorList;
    }

    public Cursor loadItems(){
        Cursor cursor;
        db = professorDB.getReadableDatabase();
        cursor = db.query(TABLE_PROFESSOR, professorDB.COLUNA, null, null, null, null, null, null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String insert(Professor professor){
        long check =0;
        SQLiteDatabase db = professorDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProfessorDBOpenHelper.NOME, professor.getNome());
        values.put(ProfessorDBOpenHelper.CPF, professor.getCpf());
        values.put(ProfessorDBOpenHelper.RG, professor.getRg());
        values.put(ProfessorDBOpenHelper.NACIONALIDADE, professor.getNacionalidade());
        values.put(ProfessorDBOpenHelper.NATURALIDADE, professor.getNaturalidade());
        values.put(ProfessorDBOpenHelper.SEXO, professor.getSexo());
        values.put(ProfessorDBOpenHelper.RUAENDERECO, professor.getEndereco().getRua_av());
        values.put(ProfessorDBOpenHelper.BAIRROENDERECO, professor.getEndereco().getBairro());
        values.put(ProfessorDBOpenHelper.NUMEROENDERECO, professor.getEndereco().getNum());
        values.put(ProfessorDBOpenHelper.CIDADEENDERECO, professor.getEndereco().getCidade());
        values.put(ProfessorDBOpenHelper.CEPENDERECO, professor.getEndereco().getCep());
        values.put(ProfessorDBOpenHelper.ESTADOENDERECO, professor.getEndereco().getEstado());
        values.put(ProfessorDBOpenHelper.COMPLEMENTOENDERECO, professor.getEndereco().getComplemento());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(professor.getData_nascimento());

        values.put(ProfessorDBOpenHelper.DATA_NASCIMENTO, reportDate);
        values.put(ProfessorDBOpenHelper.OPERADORATELEFONE, professor.getTelefone().getOperadora());
        values.put(ProfessorDBOpenHelper.NUMEROTELEFONE, professor.getTelefone().getNumero());
        values.put(ProfessorDBOpenHelper.DDDTELEFONE, professor.getTelefone().getDdd());
        values.put(ProfessorDBOpenHelper.EMAIL, professor.getEmail());
        values.put(ProfessorDBOpenHelper.SENHA, professor.getSenha());
        values.put(ProfessorDBOpenHelper.SALARIO, professor.getSalario());
        values.put(ProfessorDBOpenHelper._ID, professor.getId());
        values.put(ProfessorDBOpenHelper.CARGA_HORARIA, 0);
        values.put(ProfessorDBOpenHelper.CARGO, "");
        values.put(ProfessorDBOpenHelper.DISCIPLINA, professor.getDisciplina().getNome());


        check = db.insert(TABLE_PROFESSOR, null, values);


        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    public void clearAll(){

        professorDB.getWritableDatabase().delete(TABLE_PROFESSOR,null,null);
        db.close();

    }

    public int updateSite(Professor professor){
        SQLiteDatabase db = professorDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProfessorDBOpenHelper.NOME, professor.getNome());
        values.put(ProfessorDBOpenHelper.CPF, professor.getCpf());
        values.put(ProfessorDBOpenHelper.RG, professor.getRg());
        values.put(ProfessorDBOpenHelper.NACIONALIDADE, professor.getNacionalidade());
        values.put(ProfessorDBOpenHelper.NATURALIDADE, professor.getNaturalidade());
        values.put(ProfessorDBOpenHelper.SEXO, professor.getSexo());
        values.put(ProfessorDBOpenHelper.RUAENDERECO, professor.getEndereco().getRua_av());
        values.put(ProfessorDBOpenHelper.BAIRROENDERECO, professor.getEndereco().getBairro());
        values.put(ProfessorDBOpenHelper.NUMEROENDERECO, professor.getEndereco().getNum());
        values.put(ProfessorDBOpenHelper.CIDADEENDERECO, professor.getEndereco().getCidade());
        values.put(ProfessorDBOpenHelper.CEPENDERECO, professor.getEndereco().getCep());
        values.put(ProfessorDBOpenHelper.ESTADOENDERECO, professor.getEndereco().getEstado());
        values.put(ProfessorDBOpenHelper.COMPLEMENTOENDERECO, professor.getEndereco().getComplemento());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(professor.getData_nascimento());

        values.put(ProfessorDBOpenHelper.DATA_NASCIMENTO, reportDate);
        values.put(ProfessorDBOpenHelper.OPERADORATELEFONE, professor.getTelefone().getOperadora());
        values.put(ProfessorDBOpenHelper.NUMEROTELEFONE, professor.getTelefone().getNumero());
        values.put(ProfessorDBOpenHelper.DDDTELEFONE, professor.getTelefone().getDdd());
        values.put(ProfessorDBOpenHelper.EMAIL, professor.getEmail());
        values.put(ProfessorDBOpenHelper.SALARIO, professor.getSalario());
        values.put(ProfessorDBOpenHelper._ID, professor.getId());
        values.put(ProfessorDBOpenHelper.CARGA_HORARIA, professor.getCarga_horaria());
        values.put(ProfessorDBOpenHelper.CARGO, professor.getCargo());
        values.put(ProfessorDBOpenHelper.SENHA, professor.getSenha());
        int update = db.update(TABLE_PROFESSOR, values, ProfessorDBOpenHelper._ID + " = ?",
                new String[]{String.valueOf(professor.getId())});
        db.close();
        return update;

    }

}
