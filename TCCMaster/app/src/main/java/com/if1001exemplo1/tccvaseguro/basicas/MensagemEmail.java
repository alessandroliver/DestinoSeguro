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


public class MensagemEmail extends Mensagem {
    private String email_destino;
    private String from_email;
    private String password;
    private String assunto;
    private String email_host;
    private String email_port;

    public MensagemEmail(int id, String texto, double tempo_para_enviar_primeiro_alerta, double intervalo_de_tempo_mensagem,
                         String tipo, Date data, String horario, double latitude, double longitude, String origem,
                         String destino, String email_destino, String from_email, String password, String assunto,
                         String email_host, String email_port){
        super(id, texto, tempo_para_enviar_primeiro_alerta, intervalo_de_tempo_mensagem, tipo, data, horario, latitude,
                longitude, origem, destino);
        this.email_destino = email_destino;
        this.from_email = from_email;
        this.password = password;
        this.assunto = assunto;
        this.email_host = email_host;
        this.email_port = email_port;

    }

    public String getEmail_destino() {
        return email_destino;
    }

    public void setEmail_destino(String email_destino) {
        this.email_destino = email_destino;
    }

    public String getFrom_email() {
        return from_email;
    }

    public void setFrom_email(String from_email) {
        this.from_email = from_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getEmail_host() {
        return email_host;
    }

    public void setEmail_host(String email_host) {
        this.email_host = email_host;
    }

    public String getEmail_port() {
        return email_port;
    }

    public void setEmail_port(String email_port) {
        this.email_port = email_port;
    }

    @Override
    public void enviarMensagem(Context context, double latitude, double longitude, String text,
                               String origem, String destino) throws UnsupportedEncodingException, MessagingException {
        String emailPort = getEmail_port();// gmail's smtp port
        String smtpAuth = "true";
        String starttls = "true";
        String emailHost = getEmail_host();

        String fromEmail = "vaseguroapp@gmail.com";
        String fromPassword = "tccalex123";
        String toEmail = getEmail_destino();
        String emailSubject = getTipo() + " APP Vá Seguro";
        String emailBody= text;
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
