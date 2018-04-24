package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

//serve para o professor colocar as notas e as faltas de seu aluno
public class AtribuirNotasActivity extends Activity {
    private EditText n1,n2,n3,n4,m, result;
    String cpf = null;
    int cod = 0;
    ImageView menos, mais;
    Button confirma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atribuir_notas);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cpf = extras.getString("extraCPFALuno");
            cod = extras.getInt("extraCodDisAluno");
        }

        n1 = (EditText) findViewById(R.id.et_nota1_el);
        n2 = (EditText) findViewById(R.id.et_nota2_el);
        n3 = (EditText) findViewById(R.id.et_nota3_el);
        n4 = (EditText) findViewById(R.id.et_nota4_el);
        m = (EditText) findViewById(R.id.et_media_el);
        confirma = (Button) findViewById(R.id.bt_confirmar_el);
        result = (EditText)  findViewById(R.id.et_result_faltas);
        menos = (ImageView) findViewById(R.id.minus);
        mais = (ImageView) findViewById(R.id.plus);

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eu preciso recuperar a quantidade de faltas já atribuidas para ser mostrado
                int faltas = 0;
                try {
                    List<Disciplina> disciplinas= new AlunoDBController(AtribuirNotasActivity.this).getAllDisciplinasAluno(cpf);
                    for(Disciplina disciplina: disciplinas){
                        if(disciplina.getCodigo() == cod){
                            faltas = disciplina.getFaltas();
                            //só pode subtrair de faltas se for maior que 0
                            if(faltas>0){
                                faltas= faltas -1;
                                disciplina.setFaltas(faltas);

                                //atualiza a disciplina do aluno
                                new AlunoDBController(AtribuirNotasActivity.this).updateDisciplina(disciplina, cpf);
                                result.setText(""+faltas+"");
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int faltas = 0;
                try {
                    List<Disciplina> disciplinas= new AlunoDBController(AtribuirNotasActivity.this).getAllDisciplinasAluno(cpf);
                    for(Disciplina disciplina: disciplinas){
                        if(disciplina.getCodigo() == cod){
                            faltas = disciplina.getFaltas();
                                faltas= faltas +1;
                                disciplina.setFaltas(faltas);
                                new AlunoDBController(AtribuirNotasActivity.this).updateDisciplina(disciplina, cpf);
                            result.setText(""+faltas+"");

                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        int faltas = 0;
        double media = 0;
        try {
            List<Disciplina> disciplinas= new AlunoDBController(AtribuirNotasActivity.this).getAllDisciplinasAluno(cpf);
            for(Disciplina disciplina: disciplinas){
                if(disciplina.getCodigo() == cod){
                    faltas = disciplina.getFaltas();
                    result.setText(""+faltas+"");
                    media = disciplina.getNota1() + disciplina.getNota2() + disciplina.getNota3() + disciplina.getNota4();
                    media = media /4;
                    m.setText(""+media+"");
                    n1.setText(""+ disciplina.getNota1()+"");
                    n2.setText(""+ disciplina.getNota2()+"");
                    n3.setText(""+ disciplina.getNota3()+"");
                    n4.setText(""+ disciplina.getNota4()+"");
                    }
                }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        confirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //atualiza todas as informações da disciplina de aluno
                    List<Disciplina> disciplinas= new AlunoDBController(AtribuirNotasActivity.this).getAllDisciplinasAluno(cpf);
                    for(Disciplina disciplina: disciplinas){
                        if(disciplina.getCodigo() == cod){
                            disciplina.setFaltas(Integer.parseInt(result.getText().toString()));
                            disciplina.setNota1(Double.parseDouble(n1.getText().toString()));
                            disciplina.setNota2(Double.parseDouble(n2.getText().toString()));
                            disciplina.setNota3(Double.parseDouble(n3.getText().toString()));
                            disciplina.setNota4(Double.parseDouble(n4.getText().toString()));
                            new AlunoDBController(AtribuirNotasActivity.this).updateDisciplina(disciplina,cpf);
                            Toast.makeText(AtribuirNotasActivity.this, "Atualizado", Toast.LENGTH_SHORT).show();


                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });



    }

}
