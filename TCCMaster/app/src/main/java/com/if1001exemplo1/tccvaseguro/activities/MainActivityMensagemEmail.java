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
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperMensagemEmail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityMensagemEmail extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelperMensagemEmail myDB;
    EditText id, texto, tempo_para_enviar_primeiro_alerta, intervalo_de_tempo_mensagem, tipo, data,
            horario,  latitude,  longitude,  origem, destino,  email_destino,  from_email,  password,  assunto,
     email_host,  email_port;
    Button add, save_btn, list_btn, update_btn, delete_btn, enviarEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mensagem_email);
        myDB = new DatabaseHelperMensagemEmail(this);
        id = (EditText)findViewById(R.id.et_mensagemEmail_nome_id);
        texto = (EditText)findViewById(R.id.et_mensagemEmail_texto);
        tempo_para_enviar_primeiro_alerta = (EditText)findViewById(R.id.et_mensagemEmail_tempo_para_enviar_primeiro_alerta);
        intervalo_de_tempo_mensagem = (EditText)findViewById(R.id.et_mensagemEmail_intervalo_de_tempo_mensagem);
        tipo = (EditText)findViewById(R.id.et_mensagemEmail_tipo);
        data = (EditText)findViewById(R.id.et_mensagemEmail_data);
        horario = (EditText)findViewById(R.id.et_mensagemEmail_horario);
        latitude = (EditText)findViewById(R.id.et_mensagemEmail_latitude);
        longitude = (EditText)findViewById(R.id.et_mensagemEmail_numero_longitude);
        origem = (EditText)findViewById(R.id.et_mensagemEmail_origem);
        destino = (EditText)findViewById(R.id.et_mensagemEmail_destino);
        email_destino = (EditText)findViewById(R.id.et_mensagemEmail_email_destino);
        from_email = (EditText)findViewById(R.id.et_mensagemEmail_from_email);
        password = (EditText)findViewById(R.id.et_mensagemEmail_password);
        assunto = (EditText)findViewById(R.id.et_mensagemEmail_assunto);
        email_host = (EditText)findViewById(R.id.et_mensagemEmail_email_host);
        email_port = (EditText)findViewById(R.id.et_mensagemEmail_email_port);



        add= (Button) findViewById(R.id.send_btn_email);
        save_btn = (Button)findViewById(R.id.send_btn_email);
        list_btn = (Button)findViewById(R.id.email_btn);
        update_btn = (Button)findViewById(R.id.update_btn_email);
        delete_btn = (Button)findViewById(R.id.delete_btn_email);
        enviarEmail = (Button)findViewById(R.id.enviarEmail_btn_email);
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
        enviarEmail.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn_email :
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
                boolean result = myDB.salvaMensagemEmail(
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
                        email_destino.getText().toString(),
                        from_email.getText().toString(),
                        password.getText().toString(),
                        assunto.getText().toString(),
                        email_host.getText().toString(),
                        email_port.getText().toString());
                if (result) {
                    Toast.makeText(MainActivityMensagemEmail.this, "Success Add Mensagem Email", Toast.LENGTH_LONG).show();
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
                    email_destino.setText("");
                    from_email.setText("");
                    password.setText("");
                    assunto.setText("");
                    email_host.setText("");
                    email_port.setText("");
                }else {
                    Toast.makeText(MainActivityMensagemEmail.this, "Fails Add Mensagem Email", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.email_btn :
                Cursor email = myDB.listMensagemEmail();
                if (email.getCount() == 0) {
                    alert_message("Message", "No data mensagem found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();
                while (email.moveToNext()) {
                    buffer.append("Id : " + email.getString(0) + "\n");
                    buffer.append("Texto : " + email.getString(1) + "\n");
                    buffer.append("Tempo para enviar primeiro alerta : " + email.getString(2) + "\n");
                    buffer.append("Intervalo de tempo para mensagem : " + email.getString(3) + "\n\n\n");
                    buffer.append("Tipo : " + email.getString(4) + "\n\n\n");
                    buffer.append("Data : " + email.getString(5) + "\n\n\n");
                    buffer.append("Horário : " + email.getString(6) + "\n\n\n");
                    buffer.append("Latitude : " + email.getString(7) + "\n\n\n");
                    buffer.append("Longitude : " + email.getString(8) + "\n\n\n");
                    buffer.append("Origem : " + email.getString(9) + "\n\n\n");
                    buffer.append("Destino : " + email.getString(10) + "\n\n\n");
                    buffer.append("Email Destino : " + email.getString(11) + "\n\n\n");
                    buffer.append("From email : " + email.getString(12) + "\n\n\n");
                    buffer.append("Password : " + email.getString(13) + "\n\n\n");
                    buffer.append("Assunto : " + email.getString(14) + "\n\n\n");
                    buffer.append("Email Host : " + email.getString(15) + "\n\n\n");
                    buffer.append("Email Port : " + email.getString(16) + "\n\n\n");

                }
                //show data student
                alert_message("List Email", buffer.toString());
                break;
            case R.id.update_btn_email :
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
                boolean result1 = myDB.updateMensagemEmail(id.getText().toString(),
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
                        email_destino.getText().toString(),
                        from_email.getText().toString(),
                        password.getText().toString(),
                        assunto.getText().toString(),
                        email_host.getText().toString(),
                        email_port.getText().toString());
                if (result1) {
                    Toast.makeText(MainActivityMensagemEmail.this, "Success update Mensagem User", Toast.LENGTH_LONG).show();
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
                    email_destino.setText("");
                    from_email.setText("");
                    password.setText("");
                    assunto.setText("");
                    email_host.setText("");
                    email_port.setText("");
                }else {
                    Toast.makeText(MainActivityMensagemEmail.this, "Fails update data Email", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_email :
                Integer result2 = myDB.delete_mensagemEmail(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityMensagemEmail.this, "Success delete a Email", Toast.LENGTH_LONG).show();
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
                    email_destino.setText("");
                    from_email.setText("");
                    password.setText("");
                    assunto.setText("");
                    email_host.setText("");
                    email_port.setText("");
                }else {
                    Toast.makeText(MainActivityMensagemEmail.this, "Fails delete Mensagem", Toast.LENGTH_LONG).show();
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
