package com.if1001exemplo1.tccvaseguro.basicas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.if1001exemplo1.tccvaseguro.old.Constants;
import com.if1001exemplo1.tccvaseguro.old.DatabaseOpenHelper;
import com.if1001exemplo1.tccvaseguro.old.Guardiao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.if1001exemplo1.tccvaseguro.*;


public class MensagemSms extends Mensagem {
    private String remetente;
    List<Guardiao> guardioes;

    public MensagemSms (int id, String texto, double tempo_para_enviar_primeiro_alerta, double intervalo_de_tempo_mensagem,
                        String tipo, Date data, String horario, double latitude, double longitude, String origem,
                        String destino, String remetente, List<Guardiao> guardioes){
        super(id, texto, tempo_para_enviar_primeiro_alerta, intervalo_de_tempo_mensagem, tipo, data, horario, latitude,
                longitude, origem, destino);
        this.remetente = remetente;
        this.guardioes = guardioes;

    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public List<Guardiao> getGuardioes() {
        return guardioes;
    }

    public void setGuardioes(List<Guardiao> guardioes) {
        this.guardioes = guardioes;
    }

    @Override
    public void enviarMensagem(Context context, double latitude, double longitude, String text,
                                String origem, String destino) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        SmsManager smsManager = SmsManager.getDefault();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Guardiao> contacts = getSelectedContacts(context);
        String currentDateandTime = sdf.format(new Date());
        String typeMessage = "";
        if (getTipo().equals("ok")) {
            typeMessage = sharedPreferences.getString("msgOkPreference", "Estou bem");
        } else if (getTipo().equals("sos")) {
            typeMessage = "oi";
            //seta mensagem que vai enviar (mensagem de socorro)
            String guardioesTexto = "";
            for(int i = 0 ; i< getGuardioes().size(); i++){
                guardioesTexto += getGuardioes().get(i).getNome() + " ";
            }
            typeMessage = text + " Minha localização é: " + latitude +
                    " , "+longitude + "!"+
                    "/ Origem da rota: " + origem + " Destino da rota: " + destino;

        }
            typeMessage = text;
        if (contacts.size() > 0) {
            //seleciona os contatos
            for (Guardiao contact : contacts) {
                currentDateandTime = sdf.format(new Date());
                //envia
                smsManager.sendTextMessage(contact.getTelefone(), null, typeMessage, null, null);
            }
        } else {
            Toast.makeText(context, "Nenhum contado habilitado para receber alerta!", Toast.LENGTH_SHORT).show();
        }
            if (getTipo().equals("ok")) {

                //reinicia
                Intent i = context.getPackageManager()
                        .getLaunchIntentForPackage(context.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }


        }


    //Get only contacts whose notify boolean is true
    public static ArrayList<Guardiao> getSelectedContacts(Context context) {
        ArrayList<Guardiao> a = new ArrayList<Guardiao>();
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(context);
        Cursor c = dbHelper.getReadableDatabase().query(DatabaseOpenHelper.TABLE_NAME,
                DatabaseOpenHelper.columns, null, new String[]{}, null, null,
                null);
        if(c.moveToFirst()){
            do{
                String name = c.getString(c.getColumnIndex(DatabaseOpenHelper.CONTACT_NAME));
                String phone = c.getString(c.getColumnIndex(DatabaseOpenHelper.CONTACT_NUMBER));
                String avisar = c.getString(c.getColumnIndex(DatabaseOpenHelper.CONTACT_ALERT));
                if(avisar.equals("true")){
                    Guardiao p = new Guardiao();
                    p.setNome(name);
                    p.setTelefone(phone);
                    p.setAvisar((avisar.equals("true")) ? true : false);
                    a.add(p);
                }
            }while(c.moveToNext());
            c.close();
        }
        return a;
    }


}