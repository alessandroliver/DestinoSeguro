package com.if1001exemplo1.tccvaseguro;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;


public class MensagemSms extends Mensagem {
    private String remetente;
    List<Guardiao> guardioes;

    public MensagemSms (String texto, double tempo_para_enviar_primeiro_alerta, double intervalo_de_tempo_mensagem,
                        String tipo, Date data, String horario, LatLng location, String origem, String destino, String remetente,
                        List<Guardiao> guardioes){
        super(texto, tempo_para_enviar_primeiro_alerta, intervalo_de_tempo_mensagem, tipo, data, horario, location, origem, destino);
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
}