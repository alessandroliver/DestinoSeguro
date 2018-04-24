package com.if1001exemplo1.tcc;

/**
 * Created by Alessandro on 08/05/2017.
 */

public class Endereco {
    private String rua_av;
    private String bairro;
    private int num;
    private String cidade;
    private String cep;
    private String estado;
    private String complemento;

    public Endereco(String rua_av, String bairro, int num, String cidade, String cep, String estado, String complemento){
        this.rua_av = rua_av;
        this.bairro = bairro;
        this.num = num;
        this.cidade = cidade;
        this.cep = cep;
        this.estado = estado;
        this.complemento = complemento;
    }

    public String getRua_av(){
        return rua_av;
    }
    public void setRua_av(String rua_av){
        this.rua_av = rua_av;
    }

    public String getBairro(){
        return bairro;
    }
    public void setBairro(String bairro){
        this.bairro = bairro;
    }

    public int getNum(){
        return num;
    }
    public void setNum(int num){
        this.num = num;
    }

    public String getCidade(){
        return cidade;
    }
    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String getCep(){
        return cep;
    }
    public void setCep(String cep){
        this.cep = cep;
    }

    public String getEstado(){
        return estado;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }

    public String getComplemento(){
        return complemento;
    }
    public void setComplemento(String Complemento){
        this.complemento = complemento;
    }
}
