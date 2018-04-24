package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

//lista todos os livros cadastrados
public class ListLivroActivity extends Activity {
    private List<Livro> livroLista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_livro);
        try {
            //setar livroLista com todos os livros retornados de getAllAluno
            livroLista = new LivroDBController(this).getAllLivro();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //adaptar a lista de livros para uma lista de views
        LivroCustomAdapter livroCustomAdapter = new LivroCustomAdapter(livroLista,this);
        //resgata a listview do layout
        ListView livrolista = (ListView) findViewById(R.id.livro_list);
       // ArrayAdapter<Livro> adapter = new ArrayAdapter<Livro>(this, android.R.layout.simple_list_item_1, livroLista);
        //setando a listView com o adapter
        livrolista.setAdapter(livroCustomAdapter);
    }

}
