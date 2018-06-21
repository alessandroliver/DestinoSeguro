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

public class BO {

    //Atributos
    private int id;
    private String tipoOcorrencia;
    private String rua;
    private double cep;
    private String bairro;
    private String uf;
    private Date dataOcorrencia;
    private String descricaoAcontecido;
    private String descricaoPessoasEnvolvidas;
    private int quantidadeCoisasRoubadas;
    private User usuario;

    //construtor
    public BO (int id, String tipoOcorrencia, String rua, double cep, String bairro, String uf, Date dataOcorrencia,
               String descricaoAcontecido, String descricaoPessoasEnvolvidas, int quantidadeCoisasRoubadas,
               User usuario){
        this.id = id;
        this.tipoOcorrencia = tipoOcorrencia;
        this.rua = rua;
        this.cep = cep;
        this.bairro = bairro;
        this.uf = uf;
        this.dataOcorrencia = dataOcorrencia;
        this.descricaoAcontecido = descricaoAcontecido;
        this.descricaoPessoasEnvolvidas = descricaoPessoasEnvolvidas;
        this.quantidadeCoisasRoubadas = quantidadeCoisasRoubadas;
        this.usuario = usuario;
    }

    //gets e sets
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(String tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setCep(double cep) {
        this.cep = cep;
    }

    public double getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getDescricaoAcontecido() {
        return descricaoAcontecido;
    }

    public void setDescricaoAcontecido(String descricaoAcontecido) {
        this.descricaoAcontecido = descricaoAcontecido;
    }

    public String getDescricaoPessoasEnvolvidas() {
        return descricaoPessoasEnvolvidas;
    }

    public void setDescricaoPessoasEnvolvidas(String descricaoPessoasEnvolvidas) {
        this.descricaoPessoasEnvolvidas = descricaoPessoasEnvolvidas;
    }

    public int getQuantidadeCoisasRoubadas() {
        return quantidadeCoisasRoubadas;
    }

    public void setQuantidadeCoisasRoubadas(int quantidadeCoisasRoubadas) {
        this.quantidadeCoisasRoubadas = quantidadeCoisasRoubadas;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }


    //método para enviar o BO
    public void enviarBO(Context context) throws UnsupportedEncodingException, MessagingException {
        final String emailPort = "587";// gmail's smtp port
        final String smtpAuth = "true";
        final String starttls = "true";
        final String emailHost = "smtp.gmail.com";

        String fromEmail = "vaseguroapp@gmail.com";
        String fromPassword = "tccalex123";
        String toEmail = "thayonaradepontes@gmail.com";
        String emailSubject = "Relatório" + " APP Vá Seguro";
        String emailBody= "Tipo de ocorrência: " + getTipoOcorrencia() + " Rua do ocorrido: " + getRua()+
                           " Cep do ocorrido: "+ getCep() + " Bairro do ocorrido: " + getBairro() +
                            " UF do ocorrido: " + getUf() + " Data do ocorrido: " + getDataOcorrencia() +
                            " Descrição do ocorrido: " + getDescricaoAcontecido() +
                            " Descrição das pessoas: " + getDescricaoPessoasEnvolvidas() +
                            " Quantidade das coisas roubadas" + getQuantidadeCoisasRoubadas()+
                            " Usuário: " + getUsuario();
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
