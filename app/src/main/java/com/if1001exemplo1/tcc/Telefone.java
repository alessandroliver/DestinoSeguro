package com.if1001exemplo1.tcc;

/**
 * Created by Alessandro on 08/05/2017.
 */

public class Telefone {
    private String operadora;
    private int numero;
    private int ddd;

    public Telefone(String operadora, int numero, int ddd){
        this.operadora = operadora;
        this.numero = numero;
        this.ddd = ddd;
    }

    public String getOperadora(){
        return operadora;
    }
    public void setOperadora(String operadora){
        this.operadora = operadora;
    }

    public int getNumero(){
        return numero;
    }
    public void setNumero(int numero){
        this.numero = numero;
    }

    public int getDdd(){
        return ddd;
    }
    public void setDdd(int ddd){
        this.ddd = ddd;
    }
}
