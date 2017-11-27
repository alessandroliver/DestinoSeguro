package com.if1001exemplo1.tccvaseguro;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class Relatorio {
    private String origem;
    private String destino;
    private String tempo_saida;
    private String tempo_chegada;
    private boolean emitir_alerta;
    private DangerousLocations localizacao_alerta;
    private String horario_do_alerta;
    private String pessoas_receberam_alerta;
    private Date data_do_alerta;

    public Relatorio (String origem, String destino, String tempo_saida, String tempo_chegada, boolean emitir_alerta,
                      DangerousLocations localizacao_alerta, String horario_do_alerta, String pessoas_receberam_alerta,
                      Date data_do_alerta) {
        this.origem = origem;
        this.destino = destino;
        this.tempo_saida = tempo_saida;
        this.tempo_chegada = tempo_chegada;
        this.emitir_alerta = emitir_alerta;
        this.localizacao_alerta = localizacao_alerta;
        this.horario_do_alerta = horario_do_alerta;
        this.pessoas_receberam_alerta = pessoas_receberam_alerta;
        this.data_do_alerta = data_do_alerta;
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

    public boolean getEmitir_alerta() {
        return emitir_alerta;
    }

    public void setEmitir_alerta(boolean emitir_alerta) {
        this.emitir_alerta = emitir_alerta;
    }

    public DangerousLocations getLocalizacao_alerta() {
        return localizacao_alerta;
    }

    public void setLocalizacao_alerta(DangerousLocations localizacao_alerta) {
        this.localizacao_alerta = localizacao_alerta;
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
}
