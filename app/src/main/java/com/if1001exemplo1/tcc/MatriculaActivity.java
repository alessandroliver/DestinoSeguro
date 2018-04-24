package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//serve pra matricular os alunos
public class MatriculaActivity extends Activity {
    EditText matricula, cpf, data_inicio, data_fim;
    Button selecDisciplinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);

        matricula = (EditText) findViewById(R.id.et_matricula_matricula);
        cpf = (EditText) findViewById(R.id.et_matricula_cpf);
        data_inicio  = (EditText) findViewById(R.id.et_matricula_di);
        data_fim = (EditText) findViewById(R.id.et_matricula_df);

        selecDisciplinas = (Button) findViewById(R.id.bt_escolher_disciplinas);


        //quando clicar ele vai pra uma lista de todas as disciplinas cadastradas e ele poderá escolher

        selecDisciplinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatriculaActivity.this, ListDisciplinaMatriculaActivity.class);
                intent.putExtra("numMatriculaExtra", Integer.parseInt(matricula.getText().toString()));
                intent.putExtra("cpfMatriculaExtra", cpf.getText().toString());
                intent.putExtra("diMatriculaExtra", data_inicio.getText().toString());
                intent.putExtra("dfMatriculaExtra", data_fim.getText().toString());

                startActivity(intent);


            }
        });




    }

}
