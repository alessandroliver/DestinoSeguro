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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Alessandro on 05/06/2018.
 */
public class MainActivityGuardioes extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelperGuardioes myDB;
    EditText id, nome, sobrenome, email, cidade, uf, sexo, nascimento, celular, operadora, nacionalidade, cep, parentesco;
    Button addGuardian, save_btn_guardian, list_btn_guardian, update_btn_guardian, delete_btn_guardian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guardioes);
        myDB = new DatabaseHelperGuardioes(this);
        id = (EditText)findViewById(R.id.et_guardioes_id);
        nome = (EditText)findViewById(R.id.et_guardioes_nome);
        sobrenome = (EditText)findViewById(R.id.et_guardioes_sobrenome);
        email = (EditText)findViewById(R.id.et_guardioes_email);
        cidade = (EditText)findViewById(R.id.et_guardioes_cidade);
        uf = (EditText)findViewById(R.id.et_guardioes_uf);
        sexo = (EditText)findViewById(R.id.et_guardioes_sexo);
        nascimento = (EditText)findViewById(R.id.et_guardioes_nascimento);
        celular = (EditText)findViewById(R.id.et_guardioes_numero_celular);
        operadora = (EditText)findViewById(R.id.et_guardioes_operadora);
        nacionalidade = (EditText)findViewById(R.id.et_guardioes_nacionalidade);
        cep = (EditText)findViewById(R.id.et_guardioes_cep);
        parentesco = (EditText)findViewById(R.id.et_guardioes_parentesco);


        addGuardian = (Button) findViewById(R.id.send_btn_guardian);
        save_btn_guardian = (Button)findViewById(R.id.send_btn_guardian);
        list_btn_guardian = (Button)findViewById(R.id.guardian_btn);
        update_btn_guardian = (Button)findViewById(R.id.update_btn_guardian);
        delete_btn_guardian = (Button)findViewById(R.id.delete_btn_guardian);
      /*  addGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityGuardioes.this, GuardioesActivity.class);
                startActivity(intent);
            }
        });*/
        list_btn_guardian.setOnClickListener( this);
        update_btn_guardian.setOnClickListener(this);
        delete_btn_guardian.setOnClickListener(this);
        save_btn_guardian.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn_guardian :
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
                boolean result = myDB.salvaGuardioes(nome.getText().toString(),
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
                        parentesco.getText().toString()
                );
                if (result) {
                    Toast.makeText(MainActivityGuardioes.this, "Success Add guardian", Toast.LENGTH_LONG).show();
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
                    parentesco.setText("");
                }else {
                    Toast.makeText(MainActivityGuardioes.this, "Fails Add guardian", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.guardian_btn :
                Cursor guardioes = myDB.listGuardioes();
                if (guardioes.getCount() == 0) {
                    alert_message("Message", "No data guardian found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();
                while (guardioes.moveToNext()) {
                    List<String> user =  myDB.listGuardianUser( guardioes.getString(1));
                    for (int i = 0; i < user.size(); i++) {
                        buffer.append("User "+ i + ": " + user.get(i)+ "\n\n\n");

                    }
                    buffer.append("Id : " + guardioes.getString(0) + "\n");
                    buffer.append("Nome : " + guardioes.getString(1) + "\n");
                    buffer.append("Sobrenome : " + guardioes.getString(2) + "\n");
                    buffer.append("Email : " + guardioes.getString(3) + "\n\n\n");
                    buffer.append("Cidade : " + guardioes.getString(4) + "\n\n\n");
                    buffer.append("UF : " + guardioes.getString(5) + "\n\n\n");
                    buffer.append("Sexo : " + guardioes.getString(6) + "\n\n\n");
                    buffer.append("Nascimento : " + guardioes.getString(7) + "\n\n\n");
                    buffer.append("Celular : " + guardioes.getString(8) + "\n\n\n");
                    buffer.append("Operadora : " + guardioes.getString(9) + "\n\n\n");
                    buffer.append("Nacionalidade : " + guardioes.getString(10) + "\n\n\n");
                    buffer.append("Cep : " + guardioes.getString(11) + "\n\n\n");
                    buffer.append("Parentesco : " + guardioes.getString(12) + "\n\n\n");
                }
                //show data student
                alert_message("List Guardioes", buffer.toString());
                break;
            case R.id.update_btn_guardian :
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
                boolean result1 = myDB.updateGuardioes(id.getText().toString(),
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
                        parentesco.getText().toString());
                if (result1) {
                    Toast.makeText(MainActivityGuardioes.this, "Success update data User", Toast.LENGTH_LONG).show();
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
                    parentesco.setText("");
                }else {
                    Toast.makeText(MainActivityGuardioes.this, "Fails update data Guar", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_guardian :
                Integer result2 = myDB.delete_guardioes(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityGuardioes.this, "Success delete a Guar", Toast.LENGTH_LONG).show();
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
                    parentesco.setText("");
                }else {
                    Toast.makeText(MainActivityGuardioes.this, "Fails delete a Guar", Toast.LENGTH_LONG).show();
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

