package com.if1001exemplo1.tccvaseguro.old;

import com.if1001exemplo1.tccvaseguro.basicas.Guardioes;
import com.if1001exemplo1.tccvaseguro.basicas.User;

import java.util.Date;
import java.util.List;

/**
 * Created by thayonara on 28/11/17.
 */

public abstract class MensagemSMSEnviar {
    private String remetente;
    private double tempoEnvio;
    private Date dataCadastroMensagem;
    private int quantidadeMensagemMax;
    private int quantidadeMensagemPorPessoa;
    private String textoAssunto;
    private String textoCorpo;
    private User usuario;
    private List<Guardioes> guardioes;


    public MensagemSMSEnviar(String remetente, double tempoEnvio, Date dataCadastroMensagem
                                , int quantidadeMensagemMax, int quantidadeMensagemPorPessoa, String textoAssunto, String textoCorpo, User usuario, List<Guardioes> guardioes){
        this.remetente = remetente;
        this.tempoEnvio = tempoEnvio;
        this.dataCadastroMensagem = dataCadastroMensagem;
        this.quantidadeMensagemPorPessoa = quantidadeMensagemPorPessoa;
        this.quantidadeMensagemMax = quantidadeMensagemMax;
        this.textoAssunto = textoAssunto;
        this.textoCorpo = textoCorpo;
        this.guardioes = guardioes;
        this.usuario = usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setGuardioes(List<Guardioes> guardioes) {
        this.guardioes = guardioes;
    }

    public List<Guardioes> getGuardioes() {
        return guardioes;
    }

    public String getTextoCorpo() {
        return textoCorpo;
    }

    public void setTextoCorpo(String textoCorpo) {
        this.textoCorpo = textoCorpo;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public double getTempoEnvio() {
        return tempoEnvio;
    }

    public void setTempoEnvio(double tempoEnvio) {
        this.tempoEnvio = tempoEnvio;
    }

    public Date getDataCadastroMensagem() {
        return dataCadastroMensagem;
    }

    public void setDataCadastroMensagem(Date dataCadastroMensagem) {
        this.dataCadastroMensagem = dataCadastroMensagem;
    }

    public int getQuantidadeMensagemMax() {
        return quantidadeMensagemMax;
    }

    public void setQuantidadeMensagemMax(int quantidadeMensagemMax) {
        this.quantidadeMensagemMax = quantidadeMensagemMax;
    }

    public int getQuantidadeMensagemPorPessoa() {
        return quantidadeMensagemPorPessoa;
    }

    public void setQuantidadeMensagemPorPessoa(int quantidadeMensagemPorPessoa) {
        this.quantidadeMensagemPorPessoa = quantidadeMensagemPorPessoa;
    }

    public String getTextoAssunto() {
        return textoAssunto;
    }

    public void setTextoAssunto(String textoAssunto) {
        this.textoAssunto = textoAssunto;
    }
}
