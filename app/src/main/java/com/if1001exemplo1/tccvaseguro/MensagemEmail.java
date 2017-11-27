package com.if1001exemplo1.tccvaseguro;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class MensagemEmail extends Mensagem {
    private String email_destino;
    private String from_email;
    private String password;
    private String assunto;
    private String email_host;
    private String email_port;

    public MensagemEmail(String texto, double tempo_para_enviar_primeiro_alerta, double intervalo_de_tempo_mensagem,
                         String tipo, Date data, String horario, LatLng location, String origem, String destino, String email_destino,
                         String from_email, String password, String assunto, String email_host, String email_port){
        super(texto, tempo_para_enviar_primeiro_alerta, intervalo_de_tempo_mensagem, tipo, data, horario, location,origem,destino);
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
}

