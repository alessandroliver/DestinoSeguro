package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

public class VisualizarNotaDisciplinaActivity extends Activity {
    private TextView nota1, nota2, nota3, nota4, media, falta;
    private String nomeDisciplina, cpfAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_nota_disciplina);

        nota1 = (TextView) findViewById(R.id.tv_valor1_vnd);
        nota2 = (TextView) findViewById(R.id.tv_valor2_vnd);
        nota3 = (TextView) findViewById(R.id.tv_valor3_vnd);
        nota4 = (TextView) findViewById(R.id.tv_valor4_vnd);
        media = (TextView) findViewById(R.id.tv_vmedia_vnd);
        falta = (TextView) findViewById(R.id.tv_result_vnd);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            nomeDisciplina = extras.getString("nomeDisciplinaBoletim");
            cpfAluno = extras.getString("cpfAlunoDiciplinaBoletim");
        }

        try {
            List<Disciplina> disciplinas = new AlunoDBController(this).getAllDisciplinasAluno(cpfAluno);
            for(Disciplina disciplina :disciplinas){
                if(disciplina.getNome().equals(nomeDisciplina)){
                    nota1.setText(""+disciplina.getNota1()+"");
                    nota2.setText(""+disciplina.getNota2()+"");
                    nota3.setText(""+disciplina.getNota3()+"");
                    nota4.setText(""+disciplina.getNota4()+"");
                    double m = (disciplina.getNota1()+disciplina.getNota2()+disciplina.getNota3()+disciplina.getNota4())/4;
                    media.setText(""+m+"");
                    falta.setText(""+disciplina.getFaltas()+"");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
