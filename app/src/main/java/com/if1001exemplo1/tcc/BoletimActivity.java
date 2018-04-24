package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

//exibir as informações de notas e detalhes das disciplinas do aluno
public class BoletimActivity extends Activity {
    private TextView media_geral, faltas;
    private ListView disciplina;
    public static String cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim);

        //recupera o cpf do aluno
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cpf = extras.getString("cpfAlunoDisciplinaExtra");
        }

        media_geral = (TextView) findViewById(R.id.tv_vmg_boletim);
        faltas = (TextView) findViewById(R.id.tv_vtf_boletim);
        disciplina = (ListView) findViewById(R.id.boletim_list);
        List<Disciplina> disciplinas = null;
        try {
            //recupera tadas as disciplinas desse aluno
            disciplinas = new AlunoDBController(this).getAllDisciplinasAluno(cpf);
            double medias =0;
            int f = 0;
            //pra cada disciplina dessa lista de disciplinas
            for(Disciplina disciplina : disciplinas){
                //tiro a média geral das notas das disciplinas
                medias += (disciplina.getNota1() + disciplina.getNota2() + disciplina.getNota3() +disciplina.getNota4())/4;
                f += disciplina.getFaltas();
            }
            double mediaGeral = medias/ disciplinas.size();
            media_geral.setText(""+mediaGeral+"");
            faltas.setText(""+f+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //seta as disciplinas desse aluno

        DisciplinaBoletimCustomAdapter disciplinaBoletimCustomAdapter = new DisciplinaBoletimCustomAdapter(disciplinas, this);
        disciplina.setAdapter(disciplinaBoletimCustomAdapter);

    }

}
