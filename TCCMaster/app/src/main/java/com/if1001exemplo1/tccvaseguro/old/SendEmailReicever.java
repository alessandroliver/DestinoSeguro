package com.if1001exemplo1.tccvaseguro.old;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class SendEmailReicever extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Enviando E-mail", Toast.LENGTH_SHORT).show();
        SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy"); //você pode usar outras máscaras
        Date dataUsuario= null;
        try {
             dataUsuario=sdf1.parse("12/12/1998");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //instanciando o objeto do tipo Mensagem

        //pega o email que o usuário digitou nas preferências
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        String emailDestino = sharedPreferences.getString("emailToPreference", "alessandrorodrigues270@gmail.com");

        String emailOrigem = "vaseguroapp@gmail.com";
        String senhaDoEmailOrigem = "tccalex123";

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES
                , Context.MODE_PRIVATE);
        int tempoEnvio = sharedPref.getInt("timeSendPreference", 40);

        String tipo = "sos";

        Date data = new Date(System.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime();
        String dataFormatada = sdf.format(hora);

        LatLng location = new LatLng(Maps.latOri,Maps.longOri);

        String origem = Maps.addressOrigen;

        String destino = Maps.destAdress;

        String assunto = "ALERTA!";

        String emailHost = "smtp.gmail.com";
        String emailPort = "587";

        SharedPreferences sharedP = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        String texto = sharedP.getString("msgSOSPreference", "Estou correndo perigo");


        //MensagemEmail mensagemEmail = new MensagemEmail(texto,tempoEnvio, tempoEnvio, tipo, data,dataFormatada, location,origem,destino, emailDestino, emailOrigem, senhaDoEmailOrigem,assunto, emailHost, emailHost);
        //SendMail.sendMail(context, new MainActivity(), new LatLng(Maps.latOri, Maps.longOri), mensagemEmail);

    }
}
