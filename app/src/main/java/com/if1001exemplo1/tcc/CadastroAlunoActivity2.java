package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroAlunoActivity2 extends Activity {
    private EditText turma, ddd, telefone, operadora,nota_entrada, curso, data_nascimento;
    private Button cadastrar;
    private String nome;
    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String estado;
    private String complemento;
    private String sexo;
    private String nacionalidade;
    private String naturalidade;
    private String cpf;
    private String rg;
    private String email;
    private String senha;
    private boolean cota;

    @Override
    //carregando layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno2);

        //recuperando os componentes do layout para manipulá-los
        cadastrar = (Button) findViewById(R.id.bt_cadastrar_aluno);
        turma = (EditText) findViewById(R.id.et_turma_aluno);
        ddd = (EditText) findViewById(R.id.et_ddd_aluno);
        telefone = (EditText) findViewById(R.id.et_telefone_aluno);
        operadora = (EditText) findViewById(R.id.et_operadora_aluno);
        nota_entrada = (EditText) findViewById(R.id.et_ne_aluno);
        curso = (EditText) findViewById(R.id.et_curso_aluno);
        data_nascimento = (EditText) findViewById(R.id.et_dn_aluno);


        //ação quando aperta o botão
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //resgatando as informações do CAActivity através do sharedPreferences
                //chave e valor default
                nome = CadastroAlunoActivity.sharedPreferences.getString("nomealuno", "");
                rua = CadastroAlunoActivity.sharedPreferences.getString("ruaaluno", "");
                numero = CadastroAlunoActivity.sharedPreferences.getInt("numeroaluno", 0);
                bairro = CadastroAlunoActivity.sharedPreferences.getString("bairroaluno", "");
                cidade = CadastroAlunoActivity.sharedPreferences.getString("cidadealuno", "");
                cep = CadastroAlunoActivity.sharedPreferences.getString("cepaluno", "");
                estado = CadastroAlunoActivity.sharedPreferences.getString("estadoaluno", "");
                complemento = CadastroAlunoActivity.sharedPreferences.getString("complementoaluno", "");
                sexo = CadastroAlunoActivity.sharedPreferences.getString("sexoaluno", "");
                nacionalidade = CadastroAlunoActivity.sharedPreferences.getString("nacionalidadealuno", "");
                naturalidade = CadastroAlunoActivity.sharedPreferences.getString("naturalidadealuno", "");
                cpf = CadastroAlunoActivity.sharedPreferences.getString("cpfaluno", "");
                rg = CadastroAlunoActivity.sharedPreferences.getString("rgaluno", "");
                email = CadastroAlunoActivity.sharedPreferences.getString("emailaluno", "");
                senha = CadastroAlunoActivity.sharedPreferences.getString("senhaaluno", "");
                //na tela peço s/n e aqui tenho que converter para booleano
                if(CadastroAlunoActivity.sharedPreferences.getString("cotaealuno", "").equals("s")){
                    cota = true;
                } else{
                    cota = false;
                }
                DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");
                Date dt = null;
                try {
                    //converter pra data o texto que foi digitado
                    dt = dft.parse(data_nascimento.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Endereco endereco = new Endereco(rua,bairro,numero,cidade,cep,estado,complemento);
                Telefone tel = new Telefone(operadora.getText().toString(),Integer.parseInt(telefone.getText().toString()),
                        Integer.parseInt(ddd.getText().toString()));
                //cria aluno com seus respectivos atributos
                Aluno aluno = new Aluno(nome,cpf,rg,nacionalidade,sexo,naturalidade,endereco,dt,tel,email,
                        senha,turma.getText().toString(),cota,curso.getText().toString(),
                        0,null,
                        Double.parseDouble(nota_entrada.getText().toString()),0,0,null,null);

                //criação de um objeto AlunoDBController pra acessar ao método
                AlunoDBController alunoDBController = new AlunoDBController(CadastroAlunoActivity2.this);

                //insere aluno no BD
                alunoDBController.insert(aluno);


                //Emite um feedback para avisar que o aluno foi cadastrado
                Toast.makeText(CadastroAlunoActivity2.this, "Aluno Cadastrado", Toast.LENGTH_SHORT).show();

                cadastrar.setText("");
                turma.setText("");
                ddd.setText("");
                telefone.setText("");
                operadora.setText("");
                nota_entrada.setText("");
                curso.setText("");
                data_nascimento.setText("");

            }
        });
    }

}
