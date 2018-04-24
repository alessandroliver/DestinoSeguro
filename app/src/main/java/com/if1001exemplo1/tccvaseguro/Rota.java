package com.if1001exemplo1.tccvaseguro;

import java.util.Date;
import java.util.List;

/**
 * Created by thayonara on 28/11/17.
 */

public class Rota {

    private String nomeDestino;
    private String cidade;
    private String uf;
    private double cep;
    private List<Guardioes> guardioes;
    private String emailRelatorio;
    private int metrosDestino; //quantos metros do destino até avisar que chegou
    private int metrosDaRota; //quantos metros da rota até avisar que saiu da rota
    private String modo;
    private Usuario usuario;
    private Date dataCadastro;

    public Rota(String nomeDestino, String cidade, String uf,double cep, List<Guardioes> guardioes, String emailRelatorio
    , int metrosDestino, int metrosDaRota, String modo, Usuario usuario, Date dataCadastro){
        this.nomeDestino = nomeDestino;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.guardioes = guardioes;
        this.emailRelatorio = emailRelatorio;
        this.metrosDestino = metrosDestino;
        this.metrosDaRota = metrosDaRota;
        this.modo = modo;
        this.usuario = usuario;
        this.dataCadastro = dataCadastro;
    }


    public String getNomeDestino() {
        return nomeDestino;
    }

    public void setNomeDestino(String nomeDestino) {
        this.nomeDestino = nomeDestino;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public double getCep() {
        return cep;
    }

    public void setCep(double cep) {
        this.cep = cep;
    }

    public List<Guardioes> getGuardioes() {
        return guardioes;
    }

    public void setGuardioes(List<Guardioes> guardioes) {
        this.guardioes = guardioes;
    }

    public String getEmailRelatorio() {
        return emailRelatorio;
    }

    public void setEmailRelatorio(String emailRelatorio) {
        this.emailRelatorio = emailRelatorio;
    }

    public int getMetrosDaRota() {
        return metrosDaRota;
    }

    public void setMetrosDaRota(int metrosDaRota) {
        this.metrosDaRota = metrosDaRota;
    }

    public int getMetrosDestino() {
        return metrosDestino;
    }

    public void setMetrosDestino(int metrosDestino) {
        this.metrosDestino = metrosDestino;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
