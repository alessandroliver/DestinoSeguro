package com.if1001exemplo1.tcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

public class AcessoAtribuirNotasActivity extends AppCompatActivity {
    private EditText cpf, senha;
    private Button login;
    private String cpfAluno, nomeDisciplina;
    private int codDisciplinaAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_atribuir_notas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cpf = (EditText) findViewById(R.id.et_acesso_professor_cpf);
        senha = (EditText) findViewById(R.id.et_acesso_professor_senha);
        login = (Button) findViewById(R.id.bt_acesso_professor_logar);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cpfAluno = extras.getString("extraCPFALuno");
            codDisciplinaAluno = extras.getInt("extraCodDisAluno");
            nomeDisciplina = extras.getString("extraNomeDis");
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //recupera todos os professores
                    List<Professor> professorList = new ProfessorDBController(AcessoAtribuirNotasActivity.this).getAllProf();
                    for (Professor professor : professorList) {
                        if (professor.getCpf().equals(cpf.getText().toString())) {
                            if (professor.getSenha().equals(senha.getText().toString())) {
                                //confere se a disciplina é a mesma desse professor
                                String nomeD = professor.getDisciplina().getNome();
                                if (nomeD.equals(nomeDisciplina)) {
                                    //se tudo tiver ok, vai para atribuir notas
                                    Intent intent = new Intent(AcessoAtribuirNotasActivity.this, AtribuirNotasActivity.class);
                                    intent.putExtra("extraCPFALuno", cpfAluno);
                                    intent.putExtra("extraCodDisAluno", codDisciplinaAluno);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(AcessoAtribuirNotasActivity.this, "Não é sua disciplina", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(AcessoAtribuirNotasActivity.this, "Senha Inválida", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });



    }

}
