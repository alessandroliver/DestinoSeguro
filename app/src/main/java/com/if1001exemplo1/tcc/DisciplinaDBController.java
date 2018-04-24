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

import static com.if1001exemplo1.tcc.DisciplinaDBOpenHelper.TABLE_DISCIPLINA;

/**
 * Created by Alessandro on 23/05/2017.
 */

public class DisciplinaDBController {

    private SQLiteDatabase db;
    private DisciplinaDBOpenHelper disciplinaDB;

    public DisciplinaDBController(Context context){
        disciplinaDB = new DisciplinaDBOpenHelper(context);
    }

    public List<Disciplina> getAllDisciplina() throws ParseException {
        List<Disciplina> disciplinaList = new ArrayList<Disciplina>();
        Cursor cursor = loadItens();
        if (cursor.moveToFirst()){
            do{
                Date date = null;
                if(cursor.getString(2) != null){
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    date = df.parse(cursor.getString(2));
                }
                Date dt = null;
                if(cursor.getString(3) != null){
                    DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");
                    dt = dft.parse(cursor.getString(3));
                }


                Disciplina dis = new Disciplina(cursor.getString(0),cursor.getInt(1),date,dt,cursor.getDouble(4),cursor.getDouble(5),
                        cursor.getDouble(6),cursor.getDouble(7),cursor.getInt(8),cursor.getDouble(9));

                disciplinaList.add(dis);

            }while (cursor.moveToNext());
        }
        cursor.close();

        return disciplinaList;
    }

    public Cursor loadItens(){
        Cursor cursor;
        db = disciplinaDB.getReadableDatabase();
        cursor = db.query(TABLE_DISCIPLINA, disciplinaDB.COLUNA, null, null, null, null, null, null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String insert(Disciplina disciplina){
        long check =0;
        SQLiteDatabase db = disciplinaDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DisciplinaDBOpenHelper.NOME, disciplina.getNome());
        values.put(DisciplinaDBOpenHelper.CODIGO, disciplina.getCodigo());

        String reportD = null;
        if(disciplina.getData_inicio() != null) {
            DateFormat dfr = new SimpleDateFormat("MM/dd/yyyy");
            reportD = dfr.format(disciplina.getData_inicio());
        }

        values.put(DisciplinaDBOpenHelper.DATA_INICIO, reportD);

        String reportDe = null;
        if(disciplina.getData_inicio() != null) {
            DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy");
            reportDe = dfm.format(disciplina.getData_fim());
        }

        values.put(DisciplinaDBOpenHelper.DATA_FIM, reportDe);
        values.put(DisciplinaDBOpenHelper.NOTA1, 0);
        values.put(DisciplinaDBOpenHelper.NOTA2, 0);
        values.put(DisciplinaDBOpenHelper.NOTA3, 0);
        values.put(DisciplinaDBOpenHelper.NOTA4, 0);
        values.put(DisciplinaDBOpenHelper.FALTAS, 0);
        values.put(DisciplinaDBOpenHelper.CARGAHORARIA, disciplina.getCarga_horaria());

        check = db.insert(TABLE_DISCIPLINA, null, values);


        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    public void clearAll(){

        disciplinaDB.getWritableDatabase().delete(TABLE_DISCIPLINA,null,null);
        db.close();

    }

    public int updateSite(Disciplina disciplina){
        SQLiteDatabase db = disciplinaDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DisciplinaDBOpenHelper.NOME, disciplina.getNome());
        values.put(DisciplinaDBOpenHelper.CODIGO, disciplina.getCodigo());

        DateFormat dfr = new SimpleDateFormat("MM/dd/yyyy");
        String reportD = dfr.format(disciplina.getData_inicio());

        values.put(DisciplinaDBOpenHelper.DATA_INICIO, reportD);

        DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy");
        String reportDe = dfm.format(disciplina.getData_fim());

        values.put(DisciplinaDBOpenHelper.DATA_FIM, reportDe);
        values.put(DisciplinaDBOpenHelper.NOTA1, disciplina.getNota1());
        values.put(DisciplinaDBOpenHelper.NOTA2, disciplina.getNota2());
        values.put(DisciplinaDBOpenHelper.NOTA3, disciplina.getNota3());
        values.put(DisciplinaDBOpenHelper.NOTA4, disciplina.getNota4());
        values.put(DisciplinaDBOpenHelper.FALTAS, disciplina.getFaltas());
        values.put(DisciplinaDBOpenHelper.CARGAHORARIA, disciplina.getCarga_horaria());

        int update = db.update(TABLE_DISCIPLINA, values, DisciplinaDBOpenHelper.CODIGO + " = ?",
                new String[]{String.valueOf(disciplina.getCodigo())});
        db.close();
        return update;

    }

}
