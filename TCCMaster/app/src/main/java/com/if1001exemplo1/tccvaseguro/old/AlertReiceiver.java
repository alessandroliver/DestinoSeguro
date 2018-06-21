package com.if1001exemplo1.tccvaseguro.old;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import static com.if1001exemplo1.tccvaseguro.old.SendSMS.getSelectedContacts;



//quando se é emitido um alerta
public class AlertReiceiver extends BroadcastReceiver {
    private Context mContext;
    private AlarmManager alarmManager = null;
    private PendingIntent pendingIntent;



    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Toast.makeText(context, "Enviando SMS", Toast.LENGTH_SHORT).show();
        //envia SMS

        //instanciando a mensagem para o caso de ser mensagem de socorro

        //pega a mensagem de SOS que o user colocou
        SharedPreferences sharedP = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        String texto = sharedP.getString("msgSOSPreference", "Estou correndo perigo");

        //pega o tempo q o user colocou
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES
                , Context.MODE_PRIVATE);
        int tempoEnvio = sharedPref.getInt("timeSendPreference", 40);

        String tipo = "sos";

        Date data = new Date(System.currentTimeMillis());

        //hora
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime();
        String dataFormatada = sd.format(hora);

        LatLng location = new LatLng(Maps.latOri,Maps.longOri);

        String origem = Maps.addressOrigen;

        String destino = Maps.destAdress;

        //ArrayList<Guardiao> contacts = getSelectedContacts(context);

        //remetente
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        String remetente = sharedPreferences.getString("emailToPreference", "alessandrorodrigues270@gmail.com");
//HEREEEEEEEE
       // MensagemSms mensagemSms = new MensagemSms(texto,tempoEnvio,tempoEnvio,tipo,data,dataFormatada,location,origem,destino,remetente,contacts);

//        SendSMS.sendSms(context, Maps.latOri + " " + Maps.longOri, "sos" ,mensagemSms);
    }

}




