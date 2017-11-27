package com.if1001exemplo1.tccvaseguro;
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

import static com.if1001exemplo1.tccvaseguro.UsuarioDBOpenHelper.TABLE_USUARIO;


//manipulações do Banco de dados
public class UsuarioDBController {

    private SQLiteDatabase db;
    private UsuarioDBOpenHelper usuarioDB;
    private Context myContext;

    public UsuarioDBController(Context context){
        myContext = context;
        usuarioDB = new UsuarioDBOpenHelper(context);
    }

    //método que retorna a lista de todos os usuarios
    public List<Usuario> getAllUsuario() throws ParseException {
        List<Usuario> usuarioList = new ArrayList<Usuario>();
        Cursor cursor = loadItems();

        if (cursor.moveToFirst()) {
            do {
                //resgata as informações dos usuarios que estão no cursor
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = df.parse(cursor.getString(8));

                Usuario usu = new Usuario(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),date,
                        cursor.getInt(9));

                //adiciona cada aluno resgatado dentro da lista a ser retornada
                usuarioList.add(usu);
            } while (cursor.moveToNext());
        }
        //cursor fecha
        cursor.close();

        return usuarioList;
    }


    //método para carregar os usuarios da tabela de usuarios
    //retorna um cursor a ser usado em getAllAluno
    public Cursor loadItems(){
        Cursor cursor;
        //abrindo o bd pra leitura
        db = usuarioDB.getReadableDatabase();
        //faz a requisição das colunas que tá em alunoDB.COLUNA da tabela de aluno
        cursor = db.query(TABLE_USUARIO, usuarioDB.COLUNA, null, null, null, null, null, null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        //fecha
        db.close();
        return cursor;
    }

    //método para inserir Aluno
    public String insert(Usuario usuario){
        //checa se o bd foi criado
        long check =0;
        //abrir o bd pra escrita
        db = usuarioDB.getWritableDatabase();
        //serve para colocar os valores
        ContentValues values = new ContentValues();

        values.put(UsuarioDBOpenHelper.NOME, usuario.getNome());
        values.put(UsuarioDBOpenHelper.SOBRENOME, usuario.getSobrenome());
        values.put(UsuarioDBOpenHelper.EMAIL, usuario.getEmail());
        values.put(UsuarioDBOpenHelper.CIDADE, usuario.getCidade());
        values.put(UsuarioDBOpenHelper.UF, usuario.getUf());
        values.put(UsuarioDBOpenHelper.SENHA, usuario.getSenha());
        values.put(UsuarioDBOpenHelper.RECEBER_EMAIL, usuario.getReceber_email());
        values.put(UsuarioDBOpenHelper.SEXO, usuario.getSexo());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(usuario.getNascimento());

        values.put(UsuarioDBOpenHelper.NASCIMENTO, reportDate);
        values.put(UsuarioDBOpenHelper.NUMERO_CELULAR, 0);

        //insiro no BD
        check = db.insert(TABLE_USUARIO, null, values);

        //sempre que abre, fecha
        db.close();

        //se o método insert deu erro, retorna -1
        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }



}

