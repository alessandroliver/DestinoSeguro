package com.if1001exemplo1.tccvaseguro.basicas;

import android.content.Context;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Relatorio {
    private int id;
    private String origem;
    private String destino;
    private String tempo_saida;
    private String tempo_chegada;
    private double latDangerousLocation;
    private double lngDangerousLocation;
    private String horario_do_alerta;
    private String pessoas_receberam_alerta;
    private Date data_do_alerta;

    public Relatorio (int id, String origem, String destino, String tempo_saida, String tempo_chegada,
                      double latDangerousLocation, double lngDangerousLocation, String horario_do_alerta,
                      String pessoas_receberam_alerta, Date data_do_alerta) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.tempo_saida = tempo_saida;
        this.tempo_chegada = tempo_chegada;
        this.latDangerousLocation = latDangerousLocation;
        this.lngDangerousLocation = lngDangerousLocation;
        this.horario_do_alerta = horario_do_alerta;
        this.pessoas_receberam_alerta = pessoas_receberam_alerta;
        this.data_do_alerta = data_do_alerta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTempo_saida() {
        return tempo_saida;
    }

    public void setTempo_saida(String tempo_saida) {
        this.tempo_saida = tempo_saida;
    }

    public String getTempo_chegada() {
        return tempo_chegada;
    }

    public void setTempo_chegada(String tempo_chegada) {
        this.tempo_chegada = tempo_chegada;
    }

    public double getLatDangerousLocation() {
        return latDangerousLocation;
    }

    public void setLatDangerousLocation(double latDangerousLocation) {
        this.latDangerousLocation = latDangerousLocation;
    }

    public double getLngDangerousLocation() {
        return lngDangerousLocation;
    }

    public void setLngDangerousLocation(double lngDangerousLocation) {
        this.lngDangerousLocation = lngDangerousLocation;
    }

    public String getHorario_do_alerta() {
        return horario_do_alerta;
    }

    public void setHorario_do_alerta(String horario_do_alerta) {
        this.horario_do_alerta = horario_do_alerta;
    }

    public String getPessoas_receberam_alerta() {
        return pessoas_receberam_alerta;
    }

    public void setPessoas_receberam_alerta(String pessoas_receberam_alerta) {
        this.pessoas_receberam_alerta = pessoas_receberam_alerta;
    }

    public Date getData_do_alerta() {
        return data_do_alerta;
    }

    public void setData_do_alerta(Date data_do_alerta) {
        this.data_do_alerta = data_do_alerta;
    }

    public void enviarRelatorio(Context context) throws UnsupportedEncodingException, MessagingException {
        final String emailPort = "587";// gmail's smtp port
        final String smtpAuth = "true";
        final String starttls = "true";
        final String emailHost = "smtp.gmail.com";

        String fromEmail = "vaseguroapp@gmail.com";
        String fromPassword = "tccalex123";
        String toEmail = "alessandrorodrigues270@gmail.com";
        String emailSubject = "Relatório" + " APP Vá Seguro";
        String emailBody= "Minha origem: " + getOrigem() + " Meu destino: " + getDestino()+
                " Tempo da saída: "+ getTempo_saida() + " Tempo chegada: " + getTempo_chegada() +
                " Latitude do lugar perigoso: " + getLatDangerousLocation()
                    + " Longitude do lugar perigoso: " + getLngDangerousLocation() +
                " Horário do ocorrido: " + getHorario_do_alerta() +
                " Pessoas que receberam o alerta: " + getPessoas_receberam_alerta() +
                " Data do alerta" + getHorario_do_alerta();
        Properties emailProperties= System.getProperties();

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);


        Session mailSession = Session.getDefaultInstance(emailProperties, null);
        MimeMessage emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        emailMessage.addRecipient(Message.RecipientType.TO,
                new InternetAddress(toEmail));


        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");// for a html email
        // emailMessage.setText(emailBody);// for a text email

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        Log.i("GMail", "allrecipients: " + emailMessage.getAllRecipients());
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i("GMail", "Email sent successfully.");

    }
}
