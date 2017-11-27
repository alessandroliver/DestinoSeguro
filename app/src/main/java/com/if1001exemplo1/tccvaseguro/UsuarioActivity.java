package com.if1001exemplo1.tccvaseguro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioActivity extends AppCompatActivity {

    private EditText nomeet,sobrenomet,emailet,cidadeet,ufet,senhaet,sexoet,nascimentoet,numeroet;
    private CheckBox receber_emailet;
    private Button cadastrar;
    private String nome;
    private String sobrenome;
    private String email;
    private String cidade;
    private String uf;
    private String senha;
    private String receber_email;
    private String sexo;
    private Date nascimento;
    private int numero_celular;

    @Override
    //carregando layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //recuperando os componentes do layout para manipulá-los
        cadastrar = (Button) findViewById(R.id.bt_usuario_cadastro);
        nomeet = (EditText) findViewById(R.id.et_usuario_nome);
        sobrenomet = (EditText) findViewById(R.id.et_usuario_sobrenome);
        emailet = (EditText) findViewById(R.id.et_usuario_email);
        cidadeet = (EditText) findViewById(R.id.et_usuario_cidade);
        ufet = (EditText) findViewById(R.id.et_usuario_uf);
        senhaet = (EditText) findViewById(R.id.et_usuario_senha);
        receber_emailet = (CheckBox) findViewById(R.id.et_usuario_receber_email);
        sexoet = (EditText) findViewById(R.id.et_usuario_sexo);
        nascimentoet = (EditText) findViewById(R.id.et_usuario_nascimento);
        numeroet = (EditText) findViewById(R.id.et_usuario_numero_celular);

        //ação quando aperta o botão
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //resgatando as informações do CAActivity através do sharedPreferences
                //chave e valor default
                nome = nomeet.getText().toString();
                sobrenome = sobrenomet.getText().toString();
                email = emailet.getText().toString();
                cidade = cidadeet.getText().toString();
                uf = ufet.getText().toString();
                senha = senhaet.getText().toString();
                if(receber_emailet.isChecked()){
                    receber_email = "T";

                } else {
                    receber_email = "F";
                }
                sexo = sexoet.getText().toString();

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date dte = null;
                try {
                    //converter pra data o texto que foi digitado
                    dte = df.parse(nascimentoet.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                nascimento = dte;
                if(numeroet != null){
                    numero_celular = Integer.parseInt(numeroet.getText().toString());

                }

                DateFormat dft = new SimpleDateFormat("MM/dd/yyyy");
                Date dt = null;
                try {
                    //converter pra data o texto que foi digitado
                    dt = dft.parse(nascimentoet.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //cria aluno com seus respectivos atributos
                Usuario usuario = new Usuario(nome,sobrenome,email,cidade,uf,senha,receber_email,sexo,dt,
                        numero_celular);

                //criação de um objeto AlunoDBController pra acessar ao método
                //insere aluno no BD
                new UsuarioDBController(UsuarioActivity.this).insert(usuario);


                //Emite um feedback para avisar que o aluno foi cadastrado
                Toast.makeText(UsuarioActivity.this, "Usuario Cadastrado", Toast.LENGTH_SHORT).show();

                cadastrar.setText("");
                nomeet.setText("");
                sobrenomet.setText("");
                emailet.setText("");
                cidadeet.setText("");
                ufet.setText("");
                senhaet.setText("");
                receber_emailet.setText("");
                sexoet.setText("");
                nascimentoet.setText("");
                numeroet.setText("");

                Intent intent = new Intent(getApplication(),LoginActivity.class);
                startActivity(intent);

            }
        });

    }

}
