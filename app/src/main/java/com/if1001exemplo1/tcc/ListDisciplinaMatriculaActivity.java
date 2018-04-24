package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListDisciplinaMatriculaActivity extends Activity {
    private ListView list;
    public static String cpf = null;
    public static int matricula = 0;
    public static String di = null;
    public static String df = null;
    private Button matricular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disciplina_matricula);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            matricula = extras.getInt("numMatriculaExtra");
            cpf = extras.getString("cpfMatriculaExtra");
            di = extras.getString("diMatriculaExtra");
            df = extras.getString("dfMatriculaExtra");
        }

        matricular = (Button) findViewById(R.id.bt_selec_disc);

        matricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Aluno aluno = new AlunoDBController(ListDisciplinaMatriculaActivity.this).getAluno(cpf);

                    if (aluno != null) {
                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        try {
                            Date datei = df.parse(di);
                            aluno.setData_matricula(datei);
                            new AlunoDBController(ListDisciplinaMatriculaActivity.this).updateSite(aluno);
                            Toast.makeText(ListDisciplinaMatriculaActivity.this, "Aluno Matriculado", Toast.LENGTH_LONG).show();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    } catch(ParseException e){
                        e.printStackTrace();
                    }

            }
        });


        List<Disciplina> items = null;
        try {
            items = new DisciplinaDBController(ListDisciplinaMatriculaActivity.this).getAllDisciplina();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        list = (ListView) findViewById(R.id.list_matricula_aluno);
        DisciplinaCustomAdapter customAdapter = new DisciplinaCustomAdapter(items, ListDisciplinaMatriculaActivity.this);
       // ArrayAdapter<Disciplina> arrayAdap = new ArrayAdapter<Disciplina>(ListDisciplinaMatriculaActivity.this, android.R.layout.simple_list_item_1, items);

        //list.setAdapter(arrayAdap);
        list.setAdapter(customAdapter);
    }

}
