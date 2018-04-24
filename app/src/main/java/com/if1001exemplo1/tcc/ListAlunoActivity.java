package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

public class ListAlunoActivity extends Activity {
    private List<Aluno> alunoLista = null;
    public static String acao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_aluno);

        try {
            alunoLista = new AlunoDBController(this).getAllAluno();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            acao = extras.getString("acaoExtra");
        }

        ListView alunolista = (ListView) findViewById(R.id.aluno_list);
        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunoLista);
        //alunolista.setAdapter(adapter);
        AlunoCustomAdapter adapter = new AlunoCustomAdapter(alunoLista, ListAlunoActivity.this);
        alunolista.setAdapter(adapter);
    }


}
