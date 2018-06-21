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

/**
 * Created by Alessandro on 28/11/17.
 */

public class Feedback {
    private int id;
    private String origem;
    private String destino;
    private String modo;
    private int quantidadeAlerta;
    private double tempoPercurso;
    private Date dateFeedback;
    private double latDangerousLocation;
    private double lngDangerousLocation;
    private String horarioAlerta;
    private String descricaoAlerta;
    private User usuario;


    public Feedback( int id, String origem, String destino, String modo, int quantidadeAlerta, double tempoPercurso,
                     Date dateFeedback, double latDangerousLocation, double lngDangerousLocation, String horarioAlerta,
                     String descricaoAlerta, User usuario){
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.modo = modo;
        this.quantidadeAlerta = quantidadeAlerta;
        this.tempoPercurso = tempoPercurso;
        this.dateFeedback = dateFeedback;
        this.latDangerousLocation = latDangerousLocation;
        this.lngDangerousLocation = lngDangerousLocation;
        this.horarioAlerta = horarioAlerta;
        this.descricaoAlerta = descricaoAlerta;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadeAlerta() {
        return quantidadeAlerta;
    }

    public void setQuantidadeAlerta(int quantidadeAlerta) {
        this.quantidadeAlerta = quantidadeAlerta;
    }

    public double getTempoPercurso() {
        return tempoPercurso;
    }

    public void setTempoPercurso(double tempoPercurso) {
        this.tempoPercurso = tempoPercurso;
    }

    public Date getDateFeedback() {
        return dateFeedback;
    }

    public void setDateFeedback(Date dateFeedback) {
        this.dateFeedback = dateFeedback;
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

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getModo() {
        return modo;
    }

    public String getHorarioAlerta() {
        return horarioAlerta;
    }

    public void setHorarioAlerta(String horarioAlerta) {
        this.horarioAlerta = horarioAlerta;
    }

    public String getDescricaoAlerta() {
        return descricaoAlerta;
    }

    public void setDescricaoAlerta(String descricaoAlerta) {
        this.descricaoAlerta = descricaoAlerta;
    }


    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public User getUsuario() {
        return usuario;
    }

    public void enviarFeedback(Context context) throws UnsupportedEncodingException, MessagingException {
        final String emailPort = "587";// gmail's smtp port
        final String smtpAuth = "true";
        final String starttls = "true";
        final String emailHost = "smtp.gmail.com";

        String fromEmail = "vaseguroapp@gmail.com";
        String fromPassword = "tccalex123";
        String toEmail = "alessandrorodrigues270@gmail.com";
        String emailSubject = "Relatório" + " APP Vá Seguro";
        String emailBody= "Quantidade de alerta: " + getQuantidadeAlerta() + " Tempo do Percurso: " + getTempoPercurso()+
                " Data Feedback: "+ getDateFeedback() + " Latitude de algum ocorrido: " + getLatDangerousLocation() +
                " Longitude de algum ocorrido: " + getLngDangerousLocation()
                + " Origem percurso: " + getOrigem() +
                " Destino Percurso: " + getDestino() +
                " Modo do percurso: " + getModo() +
                " Horário alerta" + getHorarioAlerta()
                + " Descrição do ocorrido:" + getDescricaoAlerta();
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
