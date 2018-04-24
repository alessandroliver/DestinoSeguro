package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

//tela pra realizar as ações dos empréstimos do livro
public class InicioLivroActivity extends Activity {
    private Button emprestar, devolver, renovar;
    private String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_livro);

        emprestar = (Button) findViewById(R.id.bt_emprestar_il);
        devolver = (Button) findViewById(R.id.bt_devolver_il);
        renovar = (Button) findViewById(R.id.bt_renovar_il);

        //recupera a informação dada no intent
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            titulo = extras.getString("tituloLivroExtra");
        }

        //se o usuário clicar no emprestar ele dá o update nas informações do livro
        emprestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //ele pega todos os livros do bd
                    List<Livro> livros = new LivroDBController(InicioLivroActivity.this).getAllLivro();
                    //para cada livro de livros
                    for(Livro livro : livros){
                        //vê se esse livro tem o titulo igual ao fornecido
                        if(livro.getTitulo().equals(titulo)){
                            //verifico se tá emprestado
                            if(!livro.getEmprestado()){
                                //vai pra tela de empréstimo
                                Intent intent = new Intent(InicioLivroActivity.this, EmprestarLivroActivity.class);
                                intent.putExtra("tituloLivroExtra", titulo);
                                startActivity(intent);
                            } else {
                                Toast.makeText(InicioLivroActivity.this,"Livro já emprestado",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        renovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Livro> livros = new LivroDBController(InicioLivroActivity.this).getAllLivro();
                    for(Livro livro : livros){
                        if(livro.getTitulo().equals(titulo)){
                            if(livro.getEmprestado()){
                                Intent intent = new Intent(InicioLivroActivity.this, EmprestarLivroActivity.class);
                                intent.putExtra("tituloLivroExtra", titulo);
                                startActivity(intent);
                            } else {
                                Toast.makeText(InicioLivroActivity.this,"Não pode renovar",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


        devolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Livro> livros = new LivroDBController(InicioLivroActivity.this).getAllLivro();
                    for(Livro livro :livros){
                        if(livro.getTitulo().equals(titulo)){
                            if(livro.getEmprestado()) {
                                livro.setEmprestado(false);
                                livro.setCpfAluno("");
                                livro.setData_emprestimo(null);
                                livro.setData_renovacao(null);

                                new LivroDBController(InicioLivroActivity.this).updateLivro(livro);
                                Toast.makeText(InicioLivroActivity.this, "Livro devolvido", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(InicioLivroActivity.this,"Não pode devolver",Toast.LENGTH_SHORT).show();

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
