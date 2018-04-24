package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroProfessorActivity extends Activity {
    private EditText nome, rg, cpf, rua, numero, bairro, cidade, estado, complemento, cep,
            sexo, email;
    private Button proximo;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);

        proximo = (Button) findViewById(R.id.bt_proximo_professor);
        nome = (EditText) findViewById(R.id.et_nome_professor);
        rua = (EditText) findViewById(R.id.et_rua_professor);
        numero = (EditText) findViewById(R.id.et_numero_professor);
        bairro = (EditText) findViewById(R.id.et_bairro_professor);
        cidade = (EditText) findViewById(R.id.et_cidade_professor);
        estado = (EditText) findViewById(R.id.et_estado_professor);
        complemento = (EditText) findViewById(R.id.et_complemento_professor);
        cep = (EditText) findViewById(R.id.et_cep_professor);
        rg = (EditText) findViewById(R.id.et_rg_professor);
        cpf = (EditText) findViewById(R.id.et_cpf_professor);
        sexo = (EditText) findViewById(R.id.et_sexo_professor);
        email = (EditText) findViewById(R.id.et_email_professor);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("nomeprofessor", nome.getText().toString());
                editor.commit();
                editor.putString("rgprofessor", rg.getText().toString());
                editor.commit();
                editor.putString("cpfprofessor", cpf.getText().toString());
                editor.commit();
                editor.putString("ruaprofessor", rua.getText().toString());
                editor.commit();
                editor.putInt("numeroprofessor", Integer.parseInt(numero.getText().toString()));
                editor.commit();
                editor.putString("bairroprofessor", bairro.getText().toString());
                editor.commit();
                editor.putString("cidadeprofessor", cidade.getText().toString());
                editor.commit();
                editor.putString("estadoprofessor", estado.getText().toString());
                editor.commit();
                editor.putString("complementoprofessor", complemento.getText().toString());
                editor.commit();
                editor.putString("cepprofessor", (cep.getText().toString()));
                editor.commit();
                editor.putString("sexoprofessor", sexo.getText().toString());
                editor.commit();
                editor.putString("emailprofessor", email.getText().toString());
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

                Intent intent = new Intent (CadastroProfessorActivity.this, CadastroProfessorActivity2.class);
                startActivity(intent);

            }
        });


    }

}
