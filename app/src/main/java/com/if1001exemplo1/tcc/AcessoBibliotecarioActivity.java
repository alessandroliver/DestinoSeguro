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


//serve para o bibliotecario incluir suas informações pq apenas ele pode fazer o emprestimo, renovação e devolução dos livros

public class AcessoBibliotecarioActivity extends AppCompatActivity {
    EditText cpf, senha;
    Button logar;
    String titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_bibliotecario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cpf = (EditText) findViewById(R.id.et_acesso_bibliotecario_cpf);
        senha = (EditText) findViewById(R.id.et_acesso_bibliotecario_senha);
        logar = (Button) findViewById(R.id.bt_acesso_bibliotecario_logar);

        //recuperar as informações passadas no intent
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            titulo = extras.getString("tituloLivroExtra");
        }

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //recupera a lista de funcionariios do BD
                    List<Funcionario> funcionarioList = new FuncionarioDBController(AcessoBibliotecarioActivity.this).getAllFunc();
                    //pra cada funcionario dessa lista
                    for (Funcionario funcionario:funcionarioList){
                        //vemos se o cpf dele é igual ao fornecido
                        if(funcionario.getCpf().equals(cpf.getText().toString())){
                            //vemos se a senha dele é igual ao fornecido
                            if(funcionario.getSenha().equals(senha.getText().toString())){
                                //vemos se o cargo dele é um bibliotecário
                                if(funcionario.getCargo().equals("Bibliotecário")){
                                    //ele vai pra tela que realiza emprestimo, renovação e devolução dos livrros
                                    Intent intent = new Intent(AcessoBibliotecarioActivity.this, InicioLivroActivity.class);
                                    intent.putExtra("tituloLivroExtra", titulo);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(AcessoBibliotecarioActivity.this,"Você não tem permissão",Toast.LENGTH_SHORT).show();

                                }
                            } else{
                                Toast.makeText(AcessoBibliotecarioActivity.this,"Senha incorreta",Toast.LENGTH_SHORT).show();
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
