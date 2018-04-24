package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;
//lista as disciplinas de um aluno específico
public class ListDisciplinaAlunoActivity extends Activity {
    public static String cpf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disciplina_aluno);

        //recupera a informaçção do cpf do aluno
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cpf = extras.getString("cpfAlunoDisciplinaExtra");
        }

        List<Disciplina> disciplinas = null;
        try {
            //pega todas as disciplina de um aluno
            disciplinas = new AlunoDBController(ListDisciplinaAlunoActivity.this).getAllDisciplinasAluno(cpf);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //seto o listview com o adapter
        ListView disciplinasLV = (ListView) findViewById(R.id.disciplina_aluno_list);
        DisciplinaAlunoCustomAdapter disciplinaAlunoCustomAdapter = new DisciplinaAlunoCustomAdapter(disciplinas, ListDisciplinaAlunoActivity.this);
        disciplinasLV.setAdapter(disciplinaAlunoCustomAdapter);


    }

}
