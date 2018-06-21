package com.if1001exemplo1.tccvaseguro.activities;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if1001exemplo1.tccvaseguro.R;
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperGuardioes;
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperUsuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivityUsuario extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelperUsuario myDB;
    DatabaseHelperGuardioes guarBD;
    EditText id, nome, sobrenome, email, cidade, uf, sexo, nascimento, celular, operadora, nacionalidade, cep, cpf, senha, g1, g2, g3;
    Button add, save_btn, list_btn, update_btn, delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        myDB = new DatabaseHelperUsuario(this);
        guarBD = new DatabaseHelperGuardioes(this);

        id = (EditText)findViewById(R.id.et_usuario_id);
        nome = (EditText)findViewById(R.id.et_usuario_nome);
        sobrenome = (EditText)findViewById(R.id.et_usuario_sobrenome);
        email = (EditText)findViewById(R.id.et_usuario_email);
        cidade = (EditText)findViewById(R.id.et_usuario_cidade);
        uf = (EditText)findViewById(R.id.et_usuario_uf);
        sexo = (EditText)findViewById(R.id.et_usuario_sexo);
        nascimento = (EditText)findViewById(R.id.et_usuario_nascimento);
        celular = (EditText)findViewById(R.id.et_usuario_numero_celular);
        operadora = (EditText)findViewById(R.id.et_usuario_operadora);
        nacionalidade = (EditText)findViewById(R.id.et_usuario_nacionalidade);
        cep = (EditText)findViewById(R.id.et_usuario_cep);
        cpf = (EditText)findViewById(R.id.et_usuario_cpf);
        senha = (EditText)findViewById(R.id.et_usuario_senha);
        g1 = (EditText)findViewById(R.id.et_usuario_g1);
        g2 = (EditText)findViewById(R.id.et_usuario_g2);
        g3 = (EditText)findViewById(R.id.et_usuario_g3);




        add= (Button) findViewById(R.id.send_btn);
        save_btn = (Button)findViewById(R.id.send_btn);
        list_btn = (Button)findViewById(R.id.students_btn);
        update_btn = (Button)findViewById(R.id.update_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);


      /*  addGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityUsuario.this, UsuarioActivity.class);
                startActivity(intent);

            }
        });*/
        save_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);

    }


    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn :
                //converter para int
                String myString = celular.getText().toString();
                int cll = Integer.parseInt(myString);

                //converter para double
                String text = cep.getText().toString();
                double cepDouble = Double.parseDouble(text);

                //converter para Data
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                try {
                    datei = df.parse( nascimento.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean result = myDB.salvaUsusario(nome.getText().toString(),
                        sobrenome.getText().toString(),
                        email.getText().toString(),
                        cidade.getText().toString(),
                        uf.getText().toString(),
                        sexo.getText().toString(),
                        datei,
                       cll,
                        operadora.getText().toString(),
                        nacionalidade.getText().toString(),
                        cepDouble,
                        cpf.getText().toString(),
                        senha.getText().toString()

                );
                Cursor gua =  guarBD.listGuardioes();
                while (gua.moveToNext()) {

                    if(!g1.getText().equals("")){
                        if(gua.getString(1).equals(g1.getText().toString())) {
                            myDB.salvaGuardianUser(cpf.getText().toString(), g1.getText().toString());
                            guarBD.salvaUserdeGuardian(cpf.getText().toString(), g1.getText().toString());
                        }
                    }
                    if(!g2.getText().equals("")) {
                        if (gua.getString(1).equals(g2.getText().toString())) {
                            myDB.salvaGuardianUser(cpf.getText().toString(), g2.getText().toString());
                            guarBD.salvaUserdeGuardian(cpf.getText().toString(), g2.getText().toString());

                        }
                    }

                    if(!g3.getText().equals("")) {
                        if (gua.getString(1).equals(g3.getText().toString())) {
                            myDB.salvaGuardianUser(cpf.getText().toString(), g3.getText().toString());
                            guarBD.salvaUserdeGuardian(cpf.getText().toString(), g3.getText().toString());

                        }
                    }
                }

                if (result) {
                    Toast.makeText(MainActivityUsuario.this, "Success Add Usuer", Toast.LENGTH_LONG).show();
                    nome.setText("");
                    sobrenome.setText("");
                    email.setText("");
                    cidade.setText("");
                    uf.setText("");
                    sexo.setText("");
                    nascimento.setText("");
                    celular.setText("");
                    operadora.setText("");
                    nacionalidade.setText("");
                    cep.setText("");
                    cpf.setText("");
                    senha.setText("");
                    g1.setText("");
                    g2.setText("");
                    g3.setText("");

                }else {
                    Toast.makeText(MainActivityUsuario.this, "Fails Add User", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.students_btn :
                Cursor usuario = myDB.listUsuario();
                if (usuario.getCount() == 0) {
                    alert_message("Message", "No data user found");
                    return;
                }

                //append data student to buffer

                StringBuffer buffer = new StringBuffer();
                while (usuario.moveToNext()) {
                    List<String> nomes =  myDB.listGuardianUser( usuario.getString(12));
                    for (int i = 0; i < nomes.size(); i++) {
                        buffer.append("Guardian "+ i + ": " + nomes.get(i)+ "\n\n\n");

                    }
                    buffer.append("Id : " + usuario.getString(0) + "\n");
                    buffer.append("Nome : " + usuario.getString(1) + "\n");
                    buffer.append("Sobrenome : " + usuario.getString(2) + "\n");
                    buffer.append("Email : " + usuario.getString(3) + "\n\n\n");
                    buffer.append("Cidade : " + usuario.getString(4) + "\n\n\n");
                    buffer.append("UF : " + usuario.getString(5) + "\n\n\n");
                    buffer.append("Sexo : " + usuario.getString(6) + "\n\n\n");
                    buffer.append("Nascimento : " + usuario.getString(7) + "\n\n\n");
                    buffer.append("Celular : " + usuario.getString(8) + "\n\n\n");
                    buffer.append("Operadora : " + usuario.getString(9) + "\n\n\n");
                    buffer.append("Nacionalidade : " + usuario.getString(10) + "\n\n\n");
                    buffer.append("Cep : " + usuario.getString(11) + "\n\n\n");
                    buffer.append("Cpf : " + usuario.getString(12) + "\n\n\n");
                    buffer.append("Senha : " + usuario.getString(13) + "\n\n\n");


                }

                //show data student

                alert_message("List Students", buffer.toString());
                break;

            case R.id.update_btn :
                //converter para int
                String celularString = celular.getText().toString();
                int celularInt = Integer.parseInt(celularString);

                //converter para double
                String cepString = cep.getText().toString();
                double cepD = Double.parseDouble(cepString);

                //converter para Data
                DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
                Date nasc = null;
                try {
                    nasc = datef.parse( nascimento.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean result1 = myDB.updateUsuario(id.getText().toString(),
                        nome.getText().toString(),
                        sobrenome.getText().toString(),
                        email.getText().toString(),
                        cidade.getText().toString(),
                        uf.getText().toString(),
                        sexo.getText().toString(),
                        nasc,
                        celularInt,
                        operadora.getText().toString(),
                        nacionalidade.getText().toString(),
                        cepD,
                        cpf.getText().toString(),
                        senha.getText().toString());

                if (result1) {
                    Toast.makeText(MainActivityUsuario.this, "Success update data User", Toast.LENGTH_LONG).show();
                    nome.setText("");
                    sobrenome.setText("");
                    email.setText("");
                    cidade.setText("");
                    uf.setText("");
                    sexo.setText("");
                    nascimento.setText("");
                    celular.setText("");
                    operadora.setText("");
                    nacionalidade.setText("");
                    cep.setText("");
                    cpf.setText("");
                    senha.setText("");
                    g1.setText("");
                    g2.setText("");
                    g3.setText("");

                }else {
                    Toast.makeText(MainActivityUsuario.this, "Fails update data User", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.delete_btn :
                Integer result2 = myDB.delete_student(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityUsuario.this, "Success delete a User", Toast.LENGTH_LONG).show();
                    nome.setText("");
                    sobrenome.setText("");
                    email.setText("");
                    cidade.setText("");
                    uf.setText("");
                    sexo.setText("");
                    nascimento.setText("");
                    celular.setText("");
                    operadora.setText("");
                    nacionalidade.setText("");
                    cep.setText("");
                    cpf.setText("");
                    senha.setText("");

                }else {
                    Toast.makeText(MainActivityUsuario.this, "Fails delete a User", Toast.LENGTH_LONG).show();

                }
                break;
        }

    }


    public void alert_message(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
