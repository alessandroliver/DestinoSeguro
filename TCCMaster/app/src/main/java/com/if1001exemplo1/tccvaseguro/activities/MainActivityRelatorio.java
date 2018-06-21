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
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperRelatorio;
import com.if1001exemplo1.tccvaseguro.basicas.BO;
import com.if1001exemplo1.tccvaseguro.basicas.Relatorio;
import com.if1001exemplo1.tccvaseguro.basicas.User;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

public class MainActivityRelatorio extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelperRelatorio myDB;
    EditText id, origem, destino, tempo_saida, tempo_chegada, latDangerousLocation, lngDangerousLocation,
            horario_do_alerta, pessoas_receberam_alerta, data_do_alerta;
    Button add, save_btn, list_btn, update_btn, delete_btn, enviarRElatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relatorio);
        myDB = new DatabaseHelperRelatorio(this);
        id = (EditText)findViewById(R.id.et_relatorio_id);
        origem = (EditText)findViewById(R.id.et_relatorio_origem);
        destino = (EditText)findViewById(R.id.et_relatorio_destino);
        tempo_saida = (EditText)findViewById(R.id.et_relatorio_tempo_saida);
        tempo_chegada = (EditText)findViewById(R.id.et_relatorio_tempo_chegada);
        latDangerousLocation = (EditText)findViewById(R.id.et_relatorio_latDangerousLocation);
        lngDangerousLocation = (EditText)findViewById(R.id.et_relatorio_lngDangerousLocation);
        horario_do_alerta = (EditText)findViewById(R.id.et_relatorio_horario_do_alerta);
        pessoas_receberam_alerta = (EditText)findViewById(R.id.et_relatorio_pessoas_receberam_alerta);
        data_do_alerta = (EditText)findViewById(R.id.et_relatorio_data_do_alerta);
        add= (Button) findViewById(R.id.send_btn_relatorio);
        save_btn = (Button)findViewById(R.id.send_btn_relatorio);
        list_btn = (Button)findViewById(R.id.relatorio_btn);
        update_btn = (Button)findViewById(R.id.update_btn_relatorio);
        delete_btn = (Button)findViewById(R.id.delete_btn_relatorio);
        enviarRElatorio = (Button)findViewById(R.id.enviarRelatorio_btn_relatorio);
      /*  addGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityRelatorio.this, RelatorioActivity.class);
                startActivity(intent);
            }
        });*/
        save_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        enviarRElatorio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn_relatorio :
                //converter para double
                String tex = latDangerousLocation.getText().toString();
                double latDangerousLocationDouble = Double.parseDouble(tex);

                String txt = lngDangerousLocation.getText().toString();
                double lngDangerousLocationDouble = Double.parseDouble(txt);

                //converter para Data
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                try {
                    datei = df.parse( data_do_alerta.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean result = myDB.salvaRelatorio(origem.getText().toString(),
                        destino.getText().toString(),
                        tempo_saida.getText().toString(),
                        tempo_chegada.getText().toString(),
                        latDangerousLocationDouble,
                        lngDangerousLocationDouble,
                        horario_do_alerta.getText().toString(),
                        pessoas_receberam_alerta.getText().toString(),
                        datei
                );

                if (result) {
                    Toast.makeText(MainActivityRelatorio.this, "Success Add Relatorio", Toast.LENGTH_LONG).show();
                    origem.setText("");
                    destino.setText("");
                    tempo_saida.setText("");
                    tempo_chegada.setText("");
                    latDangerousLocation.setText("");
                    lngDangerousLocation.setText("");
                    horario_do_alerta.setText("");
                    pessoas_receberam_alerta.setText("");
                    data_do_alerta.setText("");
                }else {
                    Toast.makeText(MainActivityRelatorio.this, "Fails Add Relatorio", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.relatorio_btn :
                Cursor relatorio = myDB.listRelatorio();
                if (relatorio.getCount() == 0) {
                    alert_message("Message", "No data user found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();

                while (relatorio.moveToNext()) {
                    buffer.append("Id : " + relatorio.getString(0) + "\n");
                    buffer.append("Origem : " + relatorio.getString(1) + "\n");
                    buffer.append("Destino : " + relatorio.getString(2) + "\n");
                    buffer.append("Tempo Saida : " + relatorio.getString(3) + "\n\n\n");
                    buffer.append("Tempo Chegada : " + relatorio.getString(4) + "\n\n\n");
                    buffer.append("Latitude Dangerous Location : " + relatorio.getString(5) + "\n\n\n");
                    buffer.append("Longitude Dangerous Location : " + relatorio.getString(6) + "\n\n\n");
                    buffer.append("Horario do Alerta : " + relatorio.getString(7) + "\n\n\n");
                    buffer.append("Pessoas Receberam Alerta : " + relatorio.getString(8) + "\n\n\n");
                    buffer.append("Data do Alerta : " + relatorio.getString(9) + "\n\n\n");
                }
                //show data student
                alert_message("List Relatorio", buffer.toString());
                break;
            case R.id.update_btn_relatorio :

                //converter para double
                String tex1 = latDangerousLocation.getText().toString();
                double latDangerousLocationDouble1 = Double.parseDouble(tex1);

                String txt1 = lngDangerousLocation.getText().toString();
                double lngDangerousLocationDouble1 = Double.parseDouble(txt1);

                //converter para Data
                DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
                Date datei1 = null;
                try {
                    datei1 = df1.parse( data_do_alerta.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean result1 = myDB.updateRelatorio(id.getText().toString(),
                        origem.getText().toString(),
                        destino.getText().toString(),
                        tempo_saida.getText().toString(),
                        tempo_chegada.getText().toString(),
                        latDangerousLocationDouble1,
                        lngDangerousLocationDouble1,
                        horario_do_alerta.getText().toString(),
                        pessoas_receberam_alerta.getText().toString(),
                        datei1);
                if (result1) {
                    Toast.makeText(MainActivityRelatorio.this, "Success update data Relatorio", Toast.LENGTH_LONG).show();
                    origem.setText("");
                    destino.setText("");
                    tempo_saida.setText("");
                    tempo_chegada.setText("");
                    latDangerousLocation.setText("");
                    lngDangerousLocation.setText("");
                    horario_do_alerta.setText("");
                    pessoas_receberam_alerta.setText("");
                    data_do_alerta.setText("");
                }else {
                    Toast.makeText(MainActivityRelatorio.this, "Fails update data Relatorio", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_relatorio :
                Integer result2 = myDB.delete_relatorio(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityRelatorio.this, "Success delete a Relatorio", Toast.LENGTH_LONG).show();
                    origem.setText("");
                    destino.setText("");
                    tempo_saida.setText("");
                    tempo_chegada.setText("");
                    latDangerousLocation.setText("");
                    lngDangerousLocation.setText("");
                    horario_do_alerta.setText("");
                    pessoas_receberam_alerta.setText("");
                    data_do_alerta.setText("");
                }else {
                    Toast.makeText(MainActivityRelatorio.this, "Fails delete a Relatorio", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.enviarRelatorio_btn_relatorio:
                Cursor rela = myDB.listRelatorio();
                while (rela.moveToNext()) {
                    if(rela.getString(0).equals(id.getText().toString())){
                        //converter para int
                        String idString = rela.getString(0);
                        int idInt = Integer.parseInt(idString);
                        //converter para double
                        String latS = rela.getString(5);
                        double latD = Double.parseDouble(latS);
                        //converter para double
                        String lngS = rela.getString(6);
                        double lngDo = Double.parseDouble(lngS);
                        //converter para Data
                        DateFormat dateform = new SimpleDateFormat("MM/dd/yyyy");
                        Date dataD = null;
                        try {
                            dataD = dateform.parse(rela.getString(6));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Relatorio newRel = new Relatorio(idInt, rela.getString(1),rela.getString(2),rela.getString(3),rela.getString(4)
                                ,latD, lngDo,rela.getString(7),rela.getString(8), dataD);

                        try {
                            newRel.enviarRelatorio(this);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }

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
