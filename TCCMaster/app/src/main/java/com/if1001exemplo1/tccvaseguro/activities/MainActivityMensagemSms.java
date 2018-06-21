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
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperMensagemSms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityMensagemSms extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelperMensagemSms myDB;
    EditText id, texto, tempo_para_enviar_primeiro_alerta, intervalo_de_tempo_mensagem, tipo, data,
            horario,  latitude,  longitude,  origem, destino, remetente;
    Button add, save_btn, list_btn, update_btn, delete_btn, enviarSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mensagem_sms);
        myDB = new DatabaseHelperMensagemSms(this);
        id = (EditText)findViewById(R.id.et_mensagemSms_nome_id);
        texto = (EditText)findViewById(R.id.et_mensagemSms_texto);
        tempo_para_enviar_primeiro_alerta = (EditText)findViewById(R.id.et_mensagemSms_tempo_para_enviar_primeiro_alerta);
        intervalo_de_tempo_mensagem = (EditText)findViewById(R.id.et_mensagemSms_intervalo_tempo_mensagem);
        tipo = (EditText)findViewById(R.id.et_mensagemSms_tipo);
        data = (EditText)findViewById(R.id.et_mensagemSms_data);
        horario = (EditText)findViewById(R.id.et_mensagemSms_horario);
        latitude = (EditText)findViewById(R.id.et_mensagemSms_latitude);
        longitude = (EditText)findViewById(R.id.et_mensagemSms_longitude);
        origem = (EditText)findViewById(R.id.et_mensagemSms_origem);
        destino = (EditText)findViewById(R.id.et_mensagemSms_destino);
        remetente = (EditText)findViewById(R.id.et_mensagemSms_remetente);


        add= (Button) findViewById(R.id.send_btn_sms);
        save_btn = (Button)findViewById(R.id.send_btn_sms);
        list_btn = (Button)findViewById(R.id.sms_btn);
        update_btn = (Button)findViewById(R.id.update_btn_sms);
        delete_btn = (Button)findViewById(R.id.delete_btn_sms);
        enviarSms = (Button)findViewById(R.id.enviarSms_btn_sms);
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
        enviarSms.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn_sms :
                //converter para double
                String text = tempo_para_enviar_primeiro_alerta.getText().toString();
                double tempoAlertaDouble = Double.parseDouble(text);
                //converter para double
                String text1 = intervalo_de_tempo_mensagem.getText().toString();
                double intervaloMensagemDouble = Double.parseDouble(text1);

                //converter para double
                String text2 = latitude.getText().toString();
                double latitudeDouble = Double.parseDouble(text2);
                //converter para double
                String text3 = longitude.getText().toString();
                double longitudeDouble = Double.parseDouble(text3);


                //converter para Data
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                try {
                    datei = df.parse( data.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean result = myDB.salvaMensagemSms(
                        texto.getText().toString(),
                        tempoAlertaDouble,
                        intervaloMensagemDouble,
                        tipo.getText().toString(),
                        datei,
                        horario.getText().toString(),
                        latitudeDouble,
                        longitudeDouble,
                        origem.getText().toString(),
                        destino.getText().toString(),
                        remetente.getText().toString());
                if (result) {
                    Toast.makeText(MainActivityMensagemSms.this, "Success Add Mensagem SMS", Toast.LENGTH_LONG).show();
                    texto.setText("");
                    tempo_para_enviar_primeiro_alerta.setText("");
                    intervalo_de_tempo_mensagem.setText("");
                    tipo.setText("");
                    data.setText("");
                    horario.setText("");
                    latitude.setText("");
                    longitude.setText("");
                    origem.setText("");
                    destino.setText("");
                    remetente.setText("");
                }else {
                    Toast.makeText(MainActivityMensagemSms.this, "Fails Add Mensagem SMS", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.sms_btn :
                Cursor rota = myDB.listMensagemSms();
                if (rota.getCount() == 0) {
                    alert_message("Message", "No data mensagem found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();
                while (rota.moveToNext()) {
                    buffer.append("Id : " + rota.getString(0) + "\n");
                    buffer.append("Texto : " + rota.getString(1) + "\n");
                    buffer.append("Tempo para enviar primeiro alerta : " + rota.getString(2) + "\n");
                    buffer.append("Intervalo de tempo para mensagem : " + rota.getString(3) + "\n\n\n");
                    buffer.append("Tipo : " + rota.getString(4) + "\n\n\n");
                    buffer.append("Data : " + rota.getString(5) + "\n\n\n");
                    buffer.append("Horário : " + rota.getString(6) + "\n\n\n");
                    buffer.append("Latitude : " + rota.getString(7) + "\n\n\n");
                    buffer.append("Longitude : " + rota.getString(8) + "\n\n\n");
                    buffer.append("Origem : " + rota.getString(9) + "\n\n\n");
                    buffer.append("Destino : " + rota.getString(10) + "\n\n\n");
                    buffer.append("Remetente : " + rota.getString(11) + "\n\n\n");

                }
                //show data student
                alert_message("List Rota", buffer.toString());
                break;
            case R.id.update_btn_sms :
                //converter para double
                String text4 = tempo_para_enviar_primeiro_alerta.getText().toString();
                double tempoAlertaDouble1 = Double.parseDouble(text4);
                //converter para double
                String text5 = intervalo_de_tempo_mensagem.getText().toString();
                double intervaloMensagemDouble1 = Double.parseDouble(text5);

                //converter para double
                String text6 = latitude.getText().toString();
                double latitudeDouble1 = Double.parseDouble(text6);
                //converter para double
                String text7 = longitude.getText().toString();
                double longitudeDouble1 = Double.parseDouble(text7);


                //converter para Data
                DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
                Date datei1 = null;
                try {
                    datei1 = df1.parse( data.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean result1 = myDB.updateMensagemSms(id.getText().toString(),
                        texto.getText().toString(),
                        tempoAlertaDouble1,
                        intervaloMensagemDouble1,
                        tipo.getText().toString(),
                        datei1,
                        horario.getText().toString(),
                        latitudeDouble1,
                        longitudeDouble1,
                        origem.getText().toString(),
                        destino.getText().toString(),
                        remetente.getText().toString());
                if (result1) {
                    Toast.makeText(MainActivityMensagemSms.this, "Success update Mensagem SMS", Toast.LENGTH_LONG).show();
                    texto.setText("");
                    tempo_para_enviar_primeiro_alerta.setText("");
                    intervalo_de_tempo_mensagem.setText("");
                    tipo.setText("");
                    data.setText("");
                    horario.setText("");
                    latitude.setText("");
                    longitude.setText("");
                    origem.setText("");
                    destino.setText("");
                    remetente.setText("");
                }else {
                    Toast.makeText(MainActivityMensagemSms.this, "Fails update data SMS", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_sms :
                Integer result2 = myDB.delete_mensagemSms(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityMensagemSms.this, "Success delete a SMS", Toast.LENGTH_LONG).show();
                    texto.setText("");
                    tempo_para_enviar_primeiro_alerta.setText("");
                    intervalo_de_tempo_mensagem.setText("");
                    tipo.setText("");
                    data.setText("");
                    horario.setText("");
                    latitude.setText("");
                    longitude.setText("");
                    origem.setText("");
                    destino.setText("");
                    remetente.setText("");
                }else {
                    Toast.makeText(MainActivityMensagemSms.this, "Fails delete Mensagem", Toast.LENGTH_LONG).show();
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
