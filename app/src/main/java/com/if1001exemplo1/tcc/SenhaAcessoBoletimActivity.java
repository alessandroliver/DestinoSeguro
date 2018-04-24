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

//pra solicitar as credenciais do aluno para que ele obtenha acesso as informações de suas respectivas disciplinas
public class SenhaAcessoBoletimActivity extends AppCompatActivity {
    String cpfVerdadeiro;
    private EditText cpf, senha;
    private Button logar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha_acesso_boletim);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //recupero o cpf passada na intent
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cpfVerdadeiro = extras.getString("cpfAlunoDisciplinaExtra");
        }

        cpf = (EditText) findViewById(R.id.et_acesso_cpf);
        senha = (EditText) findViewById(R.id.et_acesso_senha);
        logar = (Button) findViewById(R.id.bt_acesso_logar);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //recupera todos alunos
                    List<Aluno> alunos = new AlunoDBController(SenhaAcessoBoletimActivity.this).getAllAluno();
                    //pra casa aluno da lista de alunos
                    for(Aluno aluno : alunos){
                        //pega o aluno com esse CPF
                        if(aluno.getCpf().equals(cpfVerdadeiro)){
                            //verifica se o cpf bate com o que o usuário forneceu
                            if(aluno.getCpf().equals(cpf.getText().toString())){
                                //verifica se a senha bate
                                if(aluno.getSenha().equals(senha.getText().toString())){

                                    //Vai pra tela de boletim
                                    Intent intent = new Intent(SenhaAcessoBoletimActivity.this, BoletimActivity.class);
                                    intent.putExtra("cpfAlunoDisciplinaExtra", cpfVerdadeiro);
                                    startActivity(intent);

                                } else{
                                    cpf.setText("");
                                    senha.setText("");
                                    Toast.makeText(SenhaAcessoBoletimActivity.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                                }

                            } else{
                                cpf.setText("");
                                senha.setText("");
                                Toast.makeText(SenhaAcessoBoletimActivity.this, "CPF incorreto", Toast.LENGTH_SHORT).show();

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
