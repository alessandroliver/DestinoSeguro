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

import static com.if1001exemplo1.tcc.LivroDBOpenHelper.TABLE_LIVRO;


/**
 * Created by Alessandro on 23/05/2017.
 */

public class LivroDBController {

    private SQLiteDatabase db;
    private LivroDBOpenHelper livroDB;

    public LivroDBController(Context context){
        livroDB = new LivroDBOpenHelper(context);
    }

    public List<Livro> getAllLivro() throws ParseException {
        List<Livro> livroList = new ArrayList<Livro>();
        Cursor cursor = loadItens();
        if (cursor.moveToFirst()){
            do{

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = df.parse(cursor.getString(6));
                boolean emp = false;
                if (cursor.getInt(8) == 1){
                    emp = true;
                }
                DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");

                Date data = null;
                if(!cursor.getString(9).equals("")){
                    data = dft.parse(cursor.getString(9));

                }
                Date dat = null;

                if(!cursor.getString(10).equals("")){
                    dat = dft.parse(cursor.getString(10));

                }
                Livro liv = new Livro(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getInt(5),date,cursor.getInt(7),emp,data,dat ,cursor.getString(11));

                livroList.add(liv);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return livroList;
    }

    public Cursor loadItens(){
        Cursor cursor;
        db = livroDB.getReadableDatabase();
        cursor = db.query(TABLE_LIVRO, livroDB.COLUNA, null, null, null, null, null, null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String insert(Livro livro){
        long check =0;
        SQLiteDatabase db = livroDB.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(LivroDBOpenHelper.IDIOMA, livro.getIdioma());
        values.put(LivroDBOpenHelper.TITULO, livro.getTitulo());
        values.put(LivroDBOpenHelper.AREA, livro.getArea());
        values.put(LivroDBOpenHelper.AUTOR, livro.getAutor());
        values.put(LivroDBOpenHelper.EDITORA, livro.getEditora());
        values.put(LivroDBOpenHelper.EDICAO, livro.getEdicao());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(livro.getData_publicacao());


        values.put(LivroDBOpenHelper.DATA_PUBLICACAO, reportDate);
        values.put(LivroDBOpenHelper._ID, livro.getId());
        values.put(LivroDBOpenHelper.EMPRESTADO, livro.getEmprestado());
        values.put(LivroDBOpenHelper.DATA_EMPRESTIMO, "");
        values.put(LivroDBOpenHelper.DATA_RENOVACAO, "");
        values.put(LivroDBOpenHelper.CPF_ALUNO, "");


        check = db.insert(TABLE_LIVRO, null, values);


        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    public void clearAll(){

        livroDB.getWritableDatabase().delete(TABLE_LIVRO,null,null);

    }

    public int updateLivro(Livro livro){
        SQLiteDatabase db = livroDB.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(LivroDBOpenHelper.IDIOMA, livro.getIdioma());
        values.put(LivroDBOpenHelper.TITULO, livro.getTitulo());
        values.put(LivroDBOpenHelper.AREA, livro.getArea());
        values.put(LivroDBOpenHelper.AUTOR, livro.getAutor());
        values.put(LivroDBOpenHelper.EDITORA, livro.getEditora());
        values.put(LivroDBOpenHelper.EDICAO, livro.getEdicao());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(livro.getData_publicacao());

        String reportData =null;
        if(!livro.getData_publicacao().equals("")){
            reportData = df.format(livro.getData_publicacao());
        }

        String reportDte = null;
        if(!livro.getData_publicacao().equals("")){
            reportDte = df.format(livro.getData_publicacao());
        }

        values.put(LivroDBOpenHelper.DATA_PUBLICACAO, reportDate);
        values.put(LivroDBOpenHelper._ID, livro.getId());
        values.put(LivroDBOpenHelper.EMPRESTADO, livro.getEmprestado());
        values.put(LivroDBOpenHelper.DATA_EMPRESTIMO, reportData);
        values.put(LivroDBOpenHelper.DATA_RENOVACAO, reportDte);
        values.put(LivroDBOpenHelper.CPF_ALUNO, livro.getCpfAluno());


        int update = db.update(TABLE_LIVRO, values, LivroDBOpenHelper._ID + " = ?", new String[]{String.valueOf(livro.getId())});
        db.close();
        return update;

    }

}