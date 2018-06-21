package com.if1001exemplo1.tccvaseguro.old;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Alert {

    private Context context;
    boolean send = true;
    public Alert(Context context){
        this.context = context;
    }

    //pra fazer nova rota
    public void  enableAlertRoute(int timer){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Você está em perigo?");
        builder.setMessage("O Alerta será enviado em: ");

        builder.setNegativeButton("Fazer nova rota", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Gerando nova rota...", Toast.LENGTH_SHORT).show();
                Maps.mMap.clear();
                new Maps().newRoute(Maps.destAdress, Maps.mMap, context);
                send = false;
                dialogInterface.cancel();
            }
        });

        final AlertDialog alert = builder.create();
        alert.show();
        new CountDownTimer(timer * 1000, 1000) {
            @Override
            public void onTick(long l) {
                alert.setMessage("Notamos que você não está em sua rota. Aperte em \"Fazer nova rota\" ou emitiremos um alerta em: " + l / 1000 + "s");
            }

            @Override
            public void onFinish() {
                if(send){
                Intent intentBR = new Intent().setAction("com.if1001exemplo1.tcc.SOSSolicited");
                context.sendBroadcast(intentBR);
                    Toast.makeText(context, "Enviando alerta aos seus contatos...", Toast.LENGTH_SHORT).show();

                }

                alert.cancel();
            }
        }.start();
    }




    public void enableAlert(int timer) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Você está em perigo?");
        builder.setMessage("O Alerta será enviado em: ");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "O alerta não será enviado!", Toast.LENGTH_SHORT).show();
                send = false;
                MainActivity.setVisibilityAlarm();
                dialogInterface.cancel();
            }
        });

        final AlertDialog alert = builder.create();
        alert.show();
        new CountDownTimer(timer * 1000, 1000) {
            @Override
            public void onTick(long l) {
                alert.setMessage("Você solicitou ajuda. Emitiremos o alerta em: " + l / 1000 + "s");
            }

            @Override
            public void onFinish() {
                if(send) {
                    Intent intentBR = new Intent().setAction("com.if1001exemplo1.tcc.SOSSolicited");
                    context.sendBroadcast(intentBR);
                    Toast.makeText(context, "Enviando alerta aos seus contatos...", Toast.LENGTH_SHORT).show();

                }

                alert.cancel();
            }
        }.start();
    }

    public void enableAlertPreference(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Apagar Histórico?");
        builder.setMessage("Clicando em Ok você perderá as informações dos locais em que você emitiu um alarme.");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new Maps().removeDangerousLocations(context, Maps.mMap);
                new LocationDatasController(context).clearAllDangerousLocations();
                dialogInterface.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    public void destinationHasBeenReachedAlert(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Destino Alcançado");
        builder.setMessage("Você chegou ao seu destino em seguraça. Avisaremos aos seus contatos que você está bem.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //instanciando a mensagem para o caso de ser mensagem de socorro

                //pega a mensagem de SOS que o user colocou
                SharedPreferences sharedP = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
                String texto = sharedP.getString("msgOkPreference", "Estou bem!");

                //pega o tempo q o user colocou
                SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCES
                        , Context.MODE_PRIVATE);
                int tempoEnvio = sharedPref.getInt("timeSendPreference", 40);

                String tipo = "ok";

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

//HEREEEEEEEE
                //SendSMS.sendSms(context, Maps.latOri + " " + Maps.longOri, "ok", mensagemSms);
                dialogInterface.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();

    }


}
