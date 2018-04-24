package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

public class ListDisciplinaActivity extends Activity {
    private List<Disciplina> disciplinaLista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disciplina);
        try {
            disciplinaLista = new DisciplinaDBController(this).getAllDisciplina();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView disciplinalista = (ListView) findViewById(R.id.disciplina_list);
        DisciplinaCustomAdapter disciplinaCustomAdapter = new DisciplinaCustomAdapter(disciplinaLista, this);
        //ArrayAdapter<Disciplina> adapter = new ArrayAdapter<Disciplina>(this, android.R.layout.simple_list_item_1, disciplinaLista);
        disciplinalista.setAdapter(disciplinaCustomAdapter);
    }

}