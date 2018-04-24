package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmprestarLivroActivity extends Activity {

    private EditText cpf,data_emprestimo,data_renovacao;
    private Button confirmar;
    private String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emprestar_livro);

        cpf = (EditText) findViewById(R.id.et_cpf_epl);
        data_emprestimo = (EditText) findViewById(R.id.et_de_epl);
        data_renovacao = (EditText) findViewById(R.id.et_dr_epl);
        confirmar = (Button) findViewById(R.id.bt_confirmar_epl);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            titulo = extras.getString("tituloLivroExtra");
        }

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Livro> livros = new LivroDBController(EmprestarLivroActivity.this).getAllLivro();
                    for(Livro livro :livros){
                        if(livro.getTitulo().equals(titulo)){
                            //atualiza as informações do livro
                            livro.setEmprestado(true);
                            livro.setCpfAluno(cpf.getText().toString());
                            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                            Date date = df.parse(data_emprestimo.getText().toString());
                            livro.setData_emprestimo(date);
                            date = df.parse(data_renovacao.getText().toString());
                            livro.setData_renovacao(date);

                            new LivroDBController(EmprestarLivroActivity.this).updateLivro(livro);
                            Toast.makeText(EmprestarLivroActivity.this, "Efetuado com sucesso", Toast.LENGTH_LONG).show();


                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });




    }

}
