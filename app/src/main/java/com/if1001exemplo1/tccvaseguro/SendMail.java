package com.if1001exemplo1.tccvaseguro;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SendMail {

    public SendMail() {}

    //Método que envia email
    public static void sendMail(final Context context, final Activity activity, LatLng location, MensagemEmail mensagemEmail) {

        Context mContext = context;
        String fromEmail = mensagemEmail.getFrom_email();
        String fromPassword = mensagemEmail.getPassword();
        String emailSubject = mensagemEmail.getAssunto();

        String emailBody =  mensagemEmail.getTexto() + "/n Minha localização é: " + mensagemEmail.getLocation().latitude +
                " , "+ mensagemEmail.getLocation().longitude + "!"+
                "/n Origem da rota: " + mensagemEmail.getOrigem() + " Destino da rota: " + mensagemEmail.getDestino();

        List<String> toEmailList = new ArrayList<String>();

        //destino do email
        String emailTo = mensagemEmail.getEmail_destino();
        toEmailList.add(emailTo);

        //chama a AsyncTask responsável pelo envio
        new SendMailTask(activity).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, emailBody);
        try {
            sleep(1000);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    //Método que envia email
    public static void sendRelatorio(final Context context, final Activity activity, LatLng location, Relatorio relatorio) {

        Context mContext = context;
        String fromEmail = "vaseguroapp@gmail.com";
        String fromPassword = "tccalex123";
        String emailSubject = "Relatório";

        String emailBody =
                "Origem: " + relatorio.getOrigem() +
                "/n Destino: " + relatorio.getDestino() +
                "/n Tempo de Saída: " + relatorio.getTempo_saida() +
                "/n Tempo de chegada: " + relatorio.getTempo_chegada() +
                "/n Emitiu alerta: " + relatorio.getEmitir_alerta() +
                "/n Locatização do alerta: "+ relatorio.getLocalizacao_alerta().getLat() + "," + relatorio.getLocalizacao_alerta().getLng()+
                "/n Horário do alerta: " + relatorio.getHorario_do_alerta() +
                "/n Pessoas avisadas: " + relatorio.getPessoas_receberam_alerta() +
                "/n Data: " + relatorio.getData_do_alerta();

        List<String> toEmailList = new ArrayList<String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("emailToPreference", "alessandrorodrigues270@gmail.com");
        String emailTo = email;
        toEmailList.add(emailTo);

        new SendMailTask(activity).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, emailBody);
        try {
            sleep(1000);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
