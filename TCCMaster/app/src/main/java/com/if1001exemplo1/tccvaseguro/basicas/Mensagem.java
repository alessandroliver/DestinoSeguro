package com.if1001exemplo1.tccvaseguro.basicas;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

import javax.mail.MessagingException;


public abstract class Mensagem {
    private int id;
    private String texto;
    private double tempo_para_enviar_primeiro_alerta;
    private double intervalo_de_tempo_mensagem;
    private String tipo;
    private Date data;
    private String horario;
    private double latitude;
    private double longitude;
    private String origem;
    private String destino;

    public abstract void enviarMensagem(Context context, double latitude, double longitude, String text,
                                        String origem, String destino) throws UnsupportedEncodingException, MessagingException;

    public Mensagem(int id, String texto, double tempo_para_enviar_primeiro_alerta, double intervalo_de_tempo_mensagem,
                    String tipo, Date data, String horario, double latitude, double longitude, String origem,
                    String destino) {
        this.id = id;
        this.texto = texto;
        this.tempo_para_enviar_primeiro_alerta = tempo_para_enviar_primeiro_alerta;
        this.intervalo_de_tempo_mensagem = intervalo_de_tempo_mensagem;
        this.tipo = tipo;
        this.data = data;
        this.horario = horario;
        this.latitude = latitude;
        this.longitude = longitude;
        this.destino = destino;
        this.origem = origem;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public double getTempo_para_enviar_primeiro_alerta() {
        return tempo_para_enviar_primeiro_alerta;
    }

    public void setTempo_para_enviar_primeiro_alerta(double tempo_para_enviar_primeiro_alerta) {
        this.tempo_para_enviar_primeiro_alerta = tempo_para_enviar_primeiro_alerta;
    }

    public double getIntervalo_de_tempo_mensagem() {
        return intervalo_de_tempo_mensagem;
    }

    public void setIntervalo_de_tempo_mensagem(double intervalo_de_tempo_mensagem) {
        this.intervalo_de_tempo_mensagem = intervalo_de_tempo_mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}
