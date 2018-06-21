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
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperRota;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityRota extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelperRota myDB;
    EditText id, nomeDestino, cidade, uf, cep, emailRelatorio, metrosDestino, metrosDaRota, modo, cpfUsu,
            dataCadastro;
    Button add, save_btn, list_btn, update_btn, delete_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rota);
        myDB = new DatabaseHelperRota(this);
        id = (EditText)findViewById(R.id.et_rota_id);
        nomeDestino = (EditText)findViewById(R.id.et_rota_nomeDestino);
        cidade = (EditText)findViewById(R.id.et_rota_cidade);
        uf = (EditText)findViewById(R.id.et_rota_uf);
        cep = (EditText)findViewById(R.id.et_rota_cep);
        emailRelatorio = (EditText)findViewById(R.id.et_rota_emailRelatorio);
        metrosDestino = (EditText)findViewById(R.id.et_rota_metrosDestino);
        metrosDaRota = (EditText)findViewById(R.id.et_rota_metrosDaRota);
        modo = (EditText)findViewById(R.id.et_rota_modo);
        cpfUsu = (EditText)findViewById(R.id.et_rota_cpfUsu);
        dataCadastro = (EditText)findViewById(R.id.et_rota_dataCadastro);


        add = (Button) findViewById(R.id.send_btn_rota);
        save_btn = (Button)findViewById(R.id.send_btn_rota);
        list_btn = (Button)findViewById(R.id.rota_btn);
        update_btn = (Button)findViewById(R.id.update_btn_rota);
        delete_btn = (Button)findViewById(R.id.delete_btn_rota);
      /*  addGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityRota.this, RotaActivity.class);
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
            case R.id.send_btn_rota :
                //converter para double
                String text = cep.getText().toString();
                double cepDouble = Double.parseDouble(text);
                //converter para int
                String myString = metrosDestino.getText().toString();
                int cll = Integer.parseInt(myString);
                String myStrin = metrosDaRota.getText().toString();
                int clll = Integer.parseInt(myStrin);
                //converter para Data
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                try {
                    datei = df.parse( dataCadastro.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean result = myDB.salvaRota(nomeDestino.getText().toString(),
                        cidade.getText().toString(),
                        uf.getText().toString(),
                        cepDouble,
                        emailRelatorio.getText().toString(),
                        cll,
                        clll,
                        modo.getText().toString(),
                        cpfUsu.getText().toString(),
                        datei
                );
                if (result) {
                    Toast.makeText(MainActivityRota.this, "Success Add Rota", Toast.LENGTH_LONG).show();
                    nomeDestino.setText("");
                    cidade.setText("");
                    uf.setText("");
                    cep.setText("");
                    emailRelatorio.setText("");
                    metrosDestino.setText("");
                    metrosDaRota.setText("");
                    modo.setText("");
                    cpfUsu.setText("");
                    dataCadastro.setText("");
                }else {
                    Toast.makeText(MainActivityRota.this, "Fails Add Rota", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.rota_btn :
                Cursor rota = myDB.listRota();
                if (rota.getCount() == 0) {
                    alert_message("Message", "No data user found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();
                while (rota.moveToNext()) {
                    buffer.append("Id : " + rota.getString(0) + "\n");
                    buffer.append("Nome Destino : " + rota.getString(1) + "\n");
                    buffer.append("Cidade : " + rota.getString(2) + "\n");
                    buffer.append("UF : " + rota.getString(3) + "\n\n\n");
                    buffer.append("CEP : " + rota.getString(4) + "\n\n\n");
                    buffer.append("Email Relatorio : " + rota.getString(5) + "\n\n\n");
                    buffer.append("Metros Destino : " + rota.getString(6) + "\n\n\n");
                    buffer.append("Metros da Rota : " + rota.getString(7) + "\n\n\n");
                    buffer.append("Modo : " + rota.getString(8) + "\n\n\n");
                    buffer.append("CPF User : " + rota.getString(9) + "\n\n\n");
                    buffer.append("Data Cadastro : " + rota.getString(10) + "\n\n\n");
                }
                //show data student
                alert_message("List Rota", buffer.toString());
                break;
            case R.id.update_btn_rota :
                //converter para double
                String cepString = cep.getText().toString();
                double cepD = Double.parseDouble(cepString);
                //converter para int
                String metrosDestinoString = metrosDestino.getText().toString();
                int metrosDestinoInt = Integer.parseInt(metrosDestinoString);

                String metrosDaRotaString = metrosDaRota.getText().toString();
                int metrosDaRotaInt = Integer.parseInt(metrosDaRotaString);
                //converter para Data
                DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
                Date dataC = null;
                try {
                    dataC = datef.parse( dataCadastro.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean result1 = myDB.updateRota(id.getText().toString(),
                        nomeDestino.getText().toString(),
                        cidade.getText().toString(),
                        uf.getText().toString(),
                        cepD,
                        emailRelatorio.getText().toString(),
                        metrosDestinoInt,
                        metrosDaRotaInt,
                        modo.getText().toString(),
                        cpfUsu.getText().toString(),
                        dataC);
                if (result1) {
                    Toast.makeText(MainActivityRota.this, "Success update data User", Toast.LENGTH_LONG).show();
                    nomeDestino.setText("");
                    cidade.setText("");
                    uf.setText("");
                    cep.setText("");
                    emailRelatorio.setText("");
                    metrosDestino.setText("");
                    metrosDaRota.setText("");
                    modo.setText("");
                    cpfUsu.setText("");
                    dataCadastro.setText("");
                }else {
                    Toast.makeText(MainActivityRota.this, "Fails update data Rota", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_rota :
                Integer result2 = myDB.delete_rota(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityRota.this, "Success delete a Rota", Toast.LENGTH_LONG).show();
                    nomeDestino.setText("");
                    cidade.setText("");
                    uf.setText("");
                    cep.setText("");
                    emailRelatorio.setText("");
                    metrosDestino.setText("");
                    metrosDaRota.setText("");
                    modo.setText("");
                    cpfUsu.setText("");
                    dataCadastro.setText("");
                }else {
                    Toast.makeText(MainActivityRota.this, "Fails delete a Rota", Toast.LENGTH_LONG).show();
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
