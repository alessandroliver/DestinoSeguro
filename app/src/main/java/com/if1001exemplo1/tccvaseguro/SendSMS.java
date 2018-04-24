package com.if1001exemplo1.tccvaseguro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SendSMS {

    public SendSMS() {}


    public static void sendSms(Context context, String location, String type, MensagemSms mensagemSms) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);

        SmsManager smsManager = SmsManager.getDefault();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Guardiao> contacts = getSelectedContacts(context);
        String currentDateandTime = sdf.format(new Date());
        String typeMessage = "";
        if(type.equals("ok")){
            typeMessage = sharedPreferences.getString("msgOkPreference", "Estou bem");
        } else if(type.equals("sos")){
            typeMessage = "oi";
            //seta mensagem que vai enviar (mensagem de socorro)
            String guardioesTexto = "";
            for(int i = 0 ; i< mensagemSms.getGuardioes().size(); i++){
                guardioesTexto += mensagemSms.getGuardioes().get(i).getNome() + " ";
            }
            typeMessage = mensagemSms.getTexto() + " Minha localização é: " + mensagemSms.getLocation().latitude +
                    " , "+ mensagemSms.getLocation().longitude + "!"+
                    "/ Origem da rota: " + mensagemSms.getOrigem() + " Destino da rota: " + mensagemSms.getDestino();

        }
        typeMessage = mensagemSms.getTexto();
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
        if(type.equals("ok")){

            //reinicia
            Intent i = context.getPackageManager()
                    .getLaunchIntentForPackage( context.getPackageName() );
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