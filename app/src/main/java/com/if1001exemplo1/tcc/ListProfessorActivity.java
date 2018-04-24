package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

public class ListProfessorActivity extends Activity {
    private List<Professor> professorLista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_professor);
        try {
            professorLista = new ProfessorDBController(this).getAllProf();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView professorlista = (ListView) findViewById(R.id.professor_list);
        //ArrayAdapter<Professor> adapter = new ArrayAdapter<Professor>(this, android.R.layout.simple_list_item_1, professorLista);
        ProfessorCustomAdapter professorCustomAdapter = new ProfessorCustomAdapter(professorLista, this);
        professorlista.setAdapter(professorCustomAdapter);
    }

}
