package com.if1001exemplo1.tccvaseguro;

import java.util.Date;

/**
 * Created by thayonara on 28/11/17.
 */

public class BO {
    private String tipoOcorrencia;
    private String rua;
    private double cep;
    private String Bairro;
    private String uf;
    private Date dataOcorrencia;
    private String descricaoAcontecido;
    private String descricaoPessoasEnvolvidas;
    private int quantidadeCoisasRoubadas;
    private Usuario usuario;

    public BO (String tipoOcorrencia, String rua, double cep, String bairro, String uf, Date dataOcorrencia, String descricaoAcontecido
                , String descricaoPessoasEnvolvidas, int quantidadeCoisasRoubadas, Usuario usuario){
        this.tipoOcorrencia = tipoOcorrencia;
        this.rua = rua;
        this.cep = cep;
        this.Bairro = bairro;
        this.uf = uf;
        this.dataOcorrencia = dataOcorrencia;
        this.descricaoAcontecido = descricaoAcontecido;
        this.descricaoPessoasEnvolvidas = descricaoPessoasEnvolvidas;
        this.quantidadeCoisasRoubadas = quantidadeCoisasRoubadas;
        this.usuario = usuario;
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
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
