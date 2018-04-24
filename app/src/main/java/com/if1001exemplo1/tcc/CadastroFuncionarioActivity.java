package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroFuncionarioActivity extends Activity {
    private EditText nome, rua, numero, bairro, cidade, estado, complemento, cep, rg, cpf,
            sexo, email;
    private Button proximo;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);

        proximo = (Button) findViewById(R.id.bt_proximo_funcionario);
        nome = (EditText) findViewById(R.id.et_nome_funcionario);
        rua = (EditText) findViewById(R.id.et_rua_funcionario);
        numero = (EditText) findViewById(R.id.et_numero_funcionario);
        bairro = (EditText) findViewById(R.id.et_bairro_funcionario);
        cidade = (EditText) findViewById(R.id.et_cidade_funcionario);
        estado = (EditText) findViewById(R.id.et_estado_funcionario);
        complemento = (EditText) findViewById(R.id.et_complemento_funcionario);
        cep = (EditText) findViewById(R.id.et_cep_funcionario);
        rg = (EditText) findViewById(R.id.et_rg_funcionario);
        cpf = (EditText) findViewById(R.id.et_cpf_funcionario);
        sexo = (EditText) findViewById(R.id.et_sexo_funcionario);
        email = (EditText) findViewById(R.id.et_email_funcionario);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("nomefuncionario", nome.getText().toString());
                editor.commit();
                editor.putString("rgfuncionario", rg.getText().toString());
                editor.commit();
                editor.putString("cpffuncionario", cpf.getText().toString());
                editor.commit();
                editor.putString("ruafuncionario", rua.getText().toString());
                editor.commit();
                editor.putInt("numerofuncionario", Integer.parseInt(numero.getText().toString()));
                editor.commit();
                editor.putString("bairrofuncionario", bairro.getText().toString());
                editor.commit();
                editor.putString("cidadefuncionario", cidade.getText().toString());
                editor.commit();
                editor.putString("estadofuncionario", estado.getText().toString());
                editor.commit();
                editor.putString("complementofuncionario", complemento.getText().toString());
                editor.commit();
                editor.putString("cepfuncionario", cep.getText().toString());
                editor.commit();
                editor.putString("sexofuncionario", sexo.getText().toString());
                editor.commit();
                editor.putString("emailfuncionario", email.getText().toString());
                editor.commit();

                nome.setText("");
                rua.setText("");
                numero.setText("");
                bairro.setText("");
                cidade.setText("");
                estado.setText("");
                complemento.setText("");
                cep.setText("");
                rg.setText("");
                cpf.setText("");
                sexo.setText("");
                email.setText("");

                Intent intent = new Intent (CadastroFuncionarioActivity.this, CadastroFuncionarioActivity2.class);
                startActivity(intent);

            }
        });

    }

}
