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

import static com.if1001exemplo1.tcc.FuncionarioDBOpenHelper.TABLE_FUNCIONARIO;


/**
 * Created by Alessandro on 23/05/2017.
 */

public class FuncionarioDBController {

    private SQLiteDatabase db;
    private FuncionarioDBOpenHelper funcionarioDB;

    public FuncionarioDBController(Context context){
        funcionarioDB = new FuncionarioDBOpenHelper(context);
    }

    public List<Funcionario> getAllFunc() throws ParseException {
        List<Funcionario> funcionarioList = new ArrayList<Funcionario>();
        Cursor cursor = loadItems();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Endereco end = new Endereco(cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getString(9),
                        cursor.getString(10),cursor.getString(11),cursor.getString(12));
                Telefone tel = new Telefone(cursor.getString(14),cursor.getInt(15),cursor.getInt(16));
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = df.parse(cursor.getString(13));
                Funcionario func = new Funcionario(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),end,date,tel,cursor.getString(17),cursor.getString(18),
                        cursor.getDouble(19),cursor.getInt(20),cursor.getDouble(21),cursor.getString(22));

                // Adding contact to list
                funcionarioList.add(func);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return funcionarioList;
    }


    public Cursor loadItems(){
        Cursor cursor;
        db = funcionarioDB.getReadableDatabase();
        cursor = db.query(TABLE_FUNCIONARIO, funcionarioDB.COLUNA, null, null, null, null, null, null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String insert(Funcionario funcionario){
        long check =0;
        SQLiteDatabase db = funcionarioDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FuncionarioDBOpenHelper.NOME, funcionario.getNome());
        values.put(FuncionarioDBOpenHelper.CPF, funcionario.getCpf());
        values.put(FuncionarioDBOpenHelper.RG, funcionario.getRg());
        values.put(FuncionarioDBOpenHelper.NACIONALIDADE, funcionario.getNacionalidade());
        values.put(FuncionarioDBOpenHelper.SEXO, funcionario.getSexo());
        values.put(FuncionarioDBOpenHelper.NATURALIDADE, funcionario.getNaturalidade());
        values.put(FuncionarioDBOpenHelper.RUAENDERECO, funcionario.getEndereco().getRua_av());
        values.put(FuncionarioDBOpenHelper.BAIRROENDERECO, funcionario.getEndereco().getBairro());
        values.put(FuncionarioDBOpenHelper.NUMEROENDERECO, funcionario.getEndereco().getNum());
        values.put(FuncionarioDBOpenHelper.CIDADEENDERECO, funcionario.getEndereco().getCidade());
        values.put(FuncionarioDBOpenHelper.CEPENDERECO, funcionario.getEndereco().getCep());
        values.put(FuncionarioDBOpenHelper.ESTADOENDERECO, funcionario.getEndereco().getEstado());
        values.put(FuncionarioDBOpenHelper.COMPLEMENTOENDERECO, funcionario.getEndereco().getComplemento());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(funcionario.getData_nascimento());

        values.put(FuncionarioDBOpenHelper.DATA_NASCIMENTO, reportDate);
        values.put(FuncionarioDBOpenHelper.OPERADORATELEFONE, funcionario.getTelefone().getOperadora());
        values.put(FuncionarioDBOpenHelper.NUMEROTELEFONE, funcionario.getTelefone().getNumero());
        values.put(FuncionarioDBOpenHelper.DDDTELEFONE, funcionario.getTelefone().getDdd());
        values.put(FuncionarioDBOpenHelper.EMAIL, funcionario.getEmail());
        values.put(FuncionarioDBOpenHelper.SENHA, funcionario.getSenha());
        values.put(FuncionarioDBOpenHelper.SALARIO, funcionario.getSalario());
        values.put(FuncionarioDBOpenHelper._ID, funcionario.getId());
        values.put(FuncionarioDBOpenHelper.CARGA_HORARIA, 0);
        values.put(FuncionarioDBOpenHelper.CARGO, funcionario.getCargo());


        check = db.insert(TABLE_FUNCIONARIO, null, values);


        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    public void clearAll(){

        funcionarioDB.getWritableDatabase().delete(TABLE_FUNCIONARIO,null,null);

    }

    public int updateSite(Funcionario funcionario){
        SQLiteDatabase db = funcionarioDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FuncionarioDBOpenHelper.NOME, funcionario.getNome());
        values.put(FuncionarioDBOpenHelper.CPF, funcionario.getCpf());
        values.put(FuncionarioDBOpenHelper.RG, funcionario.getRg());
        values.put(FuncionarioDBOpenHelper.NACIONALIDADE, funcionario.getNacionalidade());
        values.put(FuncionarioDBOpenHelper.SEXO, funcionario.getSexo());
        values.put(FuncionarioDBOpenHelper.NATURALIDADE, funcionario.getNaturalidade());
        values.put(FuncionarioDBOpenHelper.RUAENDERECO, funcionario.getEndereco().getRua_av());
        values.put(FuncionarioDBOpenHelper.BAIRROENDERECO, funcionario.getEndereco().getBairro());
        values.put(FuncionarioDBOpenHelper.NUMEROENDERECO, funcionario.getEndereco().getNum());
        values.put(FuncionarioDBOpenHelper.CIDADEENDERECO, funcionario.getEndereco().getCidade());
        values.put(FuncionarioDBOpenHelper.CEPENDERECO, funcionario.getEndereco().getCep());
        values.put(FuncionarioDBOpenHelper.ESTADOENDERECO, funcionario.getEndereco().getEstado());
        values.put(FuncionarioDBOpenHelper.COMPLEMENTOENDERECO, funcionario.getEndereco().getComplemento());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(funcionario.getData_nascimento());

        values.put(FuncionarioDBOpenHelper.DATA_NASCIMENTO, reportDate);
        values.put(FuncionarioDBOpenHelper.OPERADORATELEFONE, funcionario.getTelefone().getOperadora());
        values.put(FuncionarioDBOpenHelper.NUMEROTELEFONE, funcionario.getTelefone().getNumero());
        values.put(FuncionarioDBOpenHelper.DDDTELEFONE, funcionario.getTelefone().getDdd());
        values.put(FuncionarioDBOpenHelper.EMAIL, funcionario.getEmail());
        values.put(FuncionarioDBOpenHelper.SALARIO, funcionario.getSalario());
        values.put(FuncionarioDBOpenHelper._ID, funcionario.getId());
        values.put(FuncionarioDBOpenHelper.CARGA_HORARIA, funcionario.getCarga_horaria());
        values.put(FuncionarioDBOpenHelper.CARGO, funcionario.getCargo());
        values.put(FuncionarioDBOpenHelper.SENHA, funcionario.getSenha());
        int update = db.update(TABLE_FUNCIONARIO, values, FuncionarioDBOpenHelper._ID + " = ?",
                new String[]{String.valueOf(funcionario.getId())});
        db.close();
        return update;

    }

}
