package com.if1001exemplo1.tccvaseguro.basicas;

import java.util.Date;

/**
 * Created by Alessandro on 28/11/17.
 */

public class Rota {

    private int id;
    private String nomeDestino;
    private String cidade;
    private String uf;
    private double cep;
    //private List<Guardioes> guardioes;
    private String emailRelatorio;
    private int metrosDestino; //quantos metros do destino até avisar que chegou
    private int metrosDaRota; //quantos metros da rota até avisar que saiu da rota
    private String modo;
    private User user;
    private Date dataCadastro;

    public Rota(int id, String nomeDestino, String cidade, String uf,double cep/*, List<Guardioes> guardioes*/,
                String emailRelatorio, int metrosDestino, int metrosDaRota, String modo, User user,
                Date dataCadastro){
        this.id = id;
        this.nomeDestino = nomeDestino;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        //this.guardioes = guardioes;
        this.emailRelatorio = emailRelatorio;
        this.metrosDestino = metrosDestino;
        this.metrosDaRota = metrosDaRota;
        this.modo = modo;
        this.user = user;
        this.dataCadastro = dataCadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    /*public List<Guardioes> getGuardioes() {
        return guardioes;
    }

    public void setGuardioes(List<Guardioes> guardioes) {
        this.guardioes = guardioes;
    }*/

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
